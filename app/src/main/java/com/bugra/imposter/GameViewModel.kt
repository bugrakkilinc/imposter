package com.bugra.imposter

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

enum class Screen { LOBBY, REVEAL, START, RESULT }

/** NORMAL: classic round. ALL_IMPOSTER / DIFFERENT: troll rounds. */
enum class RoundType { NORMAL, ALL_IMPOSTER, DIFFERENT }

class GameViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = app.getSharedPreferences("imposter", Context.MODE_PRIVATE)

    val players = mutableStateListOf<String>()
    val selectedCategories = mutableStateListOf<String>()
    var impostorCount by mutableStateOf(1)
    var showCategory by mutableStateOf(true)
    var showHint by mutableStateOf(true)
    var trollMode by mutableStateOf(false)
    var screen by mutableStateOf(Screen.LOBBY)

    // Round state
    var roundType by mutableStateOf(RoundType.NORMAL)
    var word by mutableStateOf("")
    var hintWord by mutableStateOf("")
    var category by mutableStateOf<Category?>(null)
    var impostors by mutableStateOf<Set<Int>>(emptySet())
    var assigned by mutableStateOf<List<Pair<Category, Entry>>>(emptyList()) // DIFFERENT rounds only
    var revealIndex by mutableStateOf(0)
    var starter by mutableStateOf(0)
    private val usedWords = mutableSetOf<String>()

    init {
        players.addAll(prefs.getString("players", "")!!.split("\n").filter { it.isNotBlank() })
        val known = ALL_CATEGORIES.map { it.name }.toSet()
        val saved = prefs.getString("cats", null)?.split("\n")?.filter { it in known }.orEmpty()
        if (saved.isEmpty()) selectedCategories.addAll(ALL_CATEGORIES.filter { !it.adult }.map { it.name })
        else selectedCategories.addAll(saved)
        impostorCount = prefs.getInt("impostors", 1)
        showCategory = prefs.getBoolean("showCategory", true)
        showHint = prefs.getBoolean("showHint", true)
        trollMode = prefs.getBoolean("troll", false)
    }

    private fun save() {
        prefs.edit()
            .putString("players", players.joinToString("\n"))
            .putString("cats", selectedCategories.joinToString("\n"))
            .putInt("impostors", impostorCount)
            .putBoolean("showCategory", showCategory)
            .putBoolean("showHint", showHint)
            .putBoolean("troll", trollMode)
            .apply()
    }

    fun addPlayer(name: String) {
        val n = name.trim()
        if (n.isNotEmpty() && players.none { it.equals(n, true) }) { players.add(n); fixImpostorCount(); save() }
    }

    fun removePlayer(i: Int) { if (i in players.indices) { players.removeAt(i); fixImpostorCount(); save() } }

    fun movePlayer(from: Int, to: Int) {
        if (from !in players.indices || to !in players.indices || from == to) return
        players.add(to, players.removeAt(from))
        save()
    }

    private fun fixImpostorCount() {
        impostorCount = impostorCount.coerceIn(1, maxImpostors())
    }

    fun maxImpostors() = ((players.size - 1) / 2).coerceAtLeast(1)

    fun changeImpostors(delta: Int) { impostorCount = (impostorCount + delta).coerceIn(1, maxImpostors()); save() }
    fun toggleShowCategory(v: Boolean) { showCategory = v; save() }
    fun toggleShowHint(v: Boolean) { showHint = v; save() }
    fun toggleTroll(v: Boolean) { trollMode = v; save() }

    fun toggleCategory(name: String) {
        if (name in selectedCategories) selectedCategories.remove(name) else selectedCategories.add(name)
        save()
    }

    fun selectAll(all: Boolean) {
        selectedCategories.clear()
        if (all) selectedCategories.addAll(ALL_CATEGORIES.map { it.name })
        save()
    }

    fun canStart() = players.size >= 3 && selectedCategories.isNotEmpty()

    fun startRound() {
        val pool = ALL_CATEGORIES.filter { it.name in selectedCategories }
        if (pool.isEmpty() || players.size < 3) return

        val allEntries = pool.flatMap { c -> c.words.map { c to it } }
        var available = allEntries.filter { it.second.word !in usedWords }

        // If all words in the selected categories have been played, recycle the deck!
        if (available.isEmpty()) {
            val wordsInPool = allEntries.map { it.second.word }.toSet()
            usedWords.removeAll(wordsInPool)
            // Exclude the most recent word so it doesn't appear back-to-back across resets
            available = if (allEntries.size > 1 && word.isNotEmpty()) {
                allEntries.filter { it.second.word != word }
            } else {
                allEntries
            }
        }

        val isTrollRoll = trollMode && roundType == RoundType.NORMAL && (0..99).random() < 9
        roundType = if (isTrollRoll) {
            listOf(RoundType.ALL_IMPOSTER, RoundType.DIFFERENT).random()
        } else {
            RoundType.NORMAL
        }

        if (roundType == RoundType.DIFFERENT) {
            val diffPool = allEntries.shuffled().distinctBy { it.second.word }
            assigned = List(players.size) { diffPool[it % diffPool.size] }
            val chosen = assigned.first()
            category = chosen.first
            word = chosen.second.word
            hintWord = chosen.second.hint
            impostors = emptySet()
        } else {
            assigned = emptyList()
            val chosen = available.random()
            category = chosen.first
            val entry = chosen.second
            word = entry.word
            hintWord = entry.hint
            usedWords.add(word)

            impostors = when (roundType) {
                RoundType.NORMAL -> players.indices.shuffled().take(impostorCount).toSet()
                RoundType.ALL_IMPOSTER -> players.indices.toSet()
                else -> emptySet()
            }
        }

        starter = players.indices.random()
        revealIndex = 0
        screen = Screen.REVEAL
    }

    fun wordFor(i: Int) = if (roundType == RoundType.DIFFERENT) assigned[i].second.word else word
    fun categoryNameFor(i: Int) =
        if (roundType == RoundType.DIFFERENT) assigned[i].first.name else category?.name.orEmpty()

    fun nextReveal() {
        if (revealIndex + 1 >= players.size) screen = Screen.START else revealIndex++
    }

    /** Text shown to an impostor, depending on the toggles. Null = nothing extra. */
    fun impostorHint(): String? {
        val lines = buildList {
            if (showCategory) add("Kategori: ${category?.name}")
            if (showHint) add("İpucu: $hintWord")
        }
        return lines.takeIf { it.isNotEmpty() }?.joinToString("\n")
    }

    fun showResult() { screen = Screen.RESULT }
    fun backToLobby() { screen = Screen.LOBBY }
}
