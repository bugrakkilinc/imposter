package com.bugra.imposter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Flat, low-contrast palette (easy on the eyes)
private val Bg = Color(0xFF16181D)
private val Surface1 = Color(0xFF1F2229)
private val Surface2 = Color(0xFF292D36)
private val Accent = Color(0xFF8E9BD6)
private val OnAccent = Color(0xFF14161C)
private val TextMain = Color(0xFFE4E6EC)
private val TextSub = Color(0xFF9399A8)
private val Red = Color(0xFFC9757C)
private val Green = Color(0xFF7DB596)
private val Amber = Color(0xFFD1A86B)

private val Scheme = darkColorScheme(
    primary = Accent, onPrimary = OnAccent,
    background = Bg, onBackground = TextMain,
    surface = Surface1, onSurface = TextMain,
    surfaceVariant = Surface2, onSurfaceVariant = TextSub
)

class MainActivity : ComponentActivity() {
    private val vm: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = Scheme) {
                Box(Modifier.fillMaxSize().background(Bg).systemBarsPadding()) {
                    CompositionLocalProvider(LocalContentColor provides TextMain) { App(vm) }
                }
            }
        }
    }
}

@Composable
fun App(vm: GameViewModel) {
    when (vm.screen) {
        Screen.LOBBY -> LobbyScreen(vm)
        Screen.REVEAL -> RevealScreen(vm)
        Screen.START -> StartScreen(vm)
        Screen.RESULT -> ResultScreen(vm)
    }
}

@Composable
fun PrimaryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick, enabled = enabled,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Accent, contentColor = OnAccent,
            disabledContainerColor = Surface2, disabledContentColor = TextSub
        )
    ) { Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Surface2, contentColor = TextMain)
    ) { Text(text, fontSize = 15.sp, fontWeight = FontWeight.Medium) }
}

/** Compact row: label on the left, value on the right, opens a popup when clicked. */
@Composable
fun MenuRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Surface1)
            .clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Text(value, fontSize = 15.sp, color = TextSub)
        Text("  ›", fontSize = 18.sp, color = TextSub)
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable { onChange(!checked) }.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Switch(
            checked = checked, onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Accent, checkedThumbColor = OnAccent,
                uncheckedTrackColor = Surface2, uncheckedThumbColor = TextSub, uncheckedBorderColor = Surface2
            )
        )
    }
}

@Composable
fun LobbyScreen(vm: GameViewModel) {
    var showPlayers by remember { mutableStateOf(false) }
    var showCategories by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.padding(top = 20.dp, bottom = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.ic_launcher), null, Modifier.size(52.dp).clip(RoundedCornerShape(14.dp)))
                Spacer(Modifier.width(14.dp))
                Text("Imposter", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }

            MenuRow("Oyuncular", "${vm.players.size} kişi") { showPlayers = true }
            MenuRow("Kategoriler", "${vm.selectedCategories.size} seçili") { showCategories = true }

            // Imposter count – compact stepper
            Row(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Surface1)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Imposter sayısı", fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                StepButton("−") { vm.changeImpostors(-1) }
                Text("${vm.impostorCount}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
                StepButton("+") { vm.changeImpostors(1) }
            }

            // Impostor options
            Column(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Surface1)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                ToggleRow("Kategori herkese gösterilsin", vm.showCategory) { vm.toggleShowCategory(it) }
                ToggleRow("Imposter ipucu alsın", vm.showHint) { vm.toggleShowHint(it) }
                ToggleRow("Troll modu", vm.trollMode) { vm.toggleTroll(it) }
            }
            if (vm.trollMode) Text(
                "Her tur rastgele: ya herkes imposter olur ya da herkese farklı kelime verilir.",
                color = Amber, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp)
            )

            if (vm.players.size < 3) Text("Başlamak için en az 3 oyuncu ekle", color = Red, fontSize = 13.sp,
                modifier = Modifier.padding(start = 4.dp))
        }

        Box(Modifier.padding(top = 10.dp, bottom = 20.dp)) {
            PrimaryButton("Oyunu Başlat", enabled = vm.canStart()) { vm.startRound() }
        }
    }

    if (showPlayers) PlayersDialog(vm) { showPlayers = false }
    if (showCategories) CategoriesDialog(vm) { showCategories = false }
}

@Composable
private fun StepButton(label: String, onClick: () -> Unit) {
    Box(
        Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(Surface2).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) { Text(label, fontSize = 20.sp, fontWeight = FontWeight.Medium) }
}

@Composable
fun PlayersDialog(vm: GameViewModel, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface1, titleContentColor = TextMain, textContentColor = TextMain,
        shape = RoundedCornerShape(20.dp),
        title = { Text("Oyuncular (${vm.players.size})", fontSize = 18.sp, fontWeight = FontWeight.SemiBold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name, onValueChange = { name = it }, singleLine = true,
                        placeholder = { Text("İsim", color = TextSub) },
                        modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextMain, unfocusedTextColor = TextMain,
                            focusedBorderColor = Accent, unfocusedBorderColor = Surface2, cursorColor = Accent
                        )
                    )
                    TextButton(onClick = { vm.addPlayer(name); name = "" }) {
                        Text("Ekle", color = Accent, fontWeight = FontWeight.SemiBold)
                    }
                }
                val rowPx = with(LocalDensity.current) { 52.dp.toPx() }
                var dragName by remember { mutableStateOf<String?>(null) }
                var dragOffset by remember { mutableStateOf(0f) }
                Column(Modifier.heightIn(max = 280.dp).verticalScroll(rememberScrollState())) {
                    vm.players.toList().forEach { p ->
                        key(p) {
                            val dragging = dragName == p
                            Row(
                                Modifier.fillMaxWidth().height(52.dp)
                                    .zIndex(if (dragging) 1f else 0f)
                                    .offset { IntOffset(0, if (dragging) dragOffset.roundToInt() else 0) }
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (dragging) Surface2 else Color.Transparent)
                                    .pointerInput(p) {
                                        detectDragGesturesAfterLongPress(
                                            onDragStart = { dragName = p; dragOffset = 0f },
                                            onDragEnd = { dragName = null; dragOffset = 0f },
                                            onDragCancel = { dragName = null; dragOffset = 0f },
                                            onDrag = { change, amount ->
                                                change.consume()
                                                dragOffset += amount.y
                                                val idx = vm.players.indexOf(p)
                                                if (dragOffset > rowPx / 2 && idx < vm.players.lastIndex) {
                                                    vm.movePlayer(idx, idx + 1); dragOffset -= rowPx
                                                } else if (dragOffset < -rowPx / 2 && idx > 0) {
                                                    vm.movePlayer(idx, idx - 1); dragOffset += rowPx
                                                }
                                            }
                                        )
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("≡", fontSize = 20.sp, color = TextSub, modifier = Modifier.padding(horizontal = 10.dp))
                                Text(p, fontSize = 16.sp, modifier = Modifier.weight(1f))
                                TextButton(onClick = { vm.removePlayer(vm.players.indexOf(p)) }) { Text("Sil", color = Red) }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Tamam", color = Accent, fontWeight = FontWeight.SemiBold) } }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesDialog(vm: GameViewModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface1, titleContentColor = TextMain, textContentColor = TextMain,
        shape = RoundedCornerShape(20.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Kategoriler", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                TextButton(onClick = { vm.selectAll(true) }) { Text("Hepsi", color = Accent) }
                TextButton(onClick = { vm.selectAll(false) }) { Text("Hiçbiri", color = Accent) }
            }
        },
        text = {
            FlowRow(
                Modifier.verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ALL_CATEGORIES.forEach { c ->
                    val sel = c.name in vm.selectedCategories
                    FilterChip(
                        selected = sel, onClick = { vm.toggleCategory(c.name) },
                        label = { Text("${c.emoji} ${c.name}", fontSize = 14.sp) },
                        shape = RoundedCornerShape(10.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Surface2, labelColor = TextMain,
                            selectedContainerColor = Accent, selectedLabelColor = OnAccent
                        ),
                        border = null
                    )
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Tamam", color = Accent, fontWeight = FontWeight.SemiBold) } }
    )
}

@Composable
fun RevealScreen(vm: GameViewModel) {
    BackHandler { vm.backToLobby() }
    val i = vm.revealIndex
    var holding by remember(i) { mutableStateOf(false) }
    var seen by remember(i) { mutableStateOf(false) }
    val isImpostor = i in vm.impostors
    val last = i == vm.players.size - 1
    val cardColor = Surface2

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Telefonu ver", color = TextSub, fontSize = 15.sp)
            Spacer(Modifier.height(4.dp))
            Text("${i + 1} / ${vm.players.size}", color = TextSub, fontSize = 13.sp)
        }

        Box(
            Modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(24.dp)).background(cardColor)
                .pointerInput(i) {
                    detectTapGestures(onPress = {
                        holding = true; seen = true
                        try { awaitRelease() } finally { holding = false }
                    })
                },
            contentAlignment = Alignment.Center
        ) {
            val onCard = TextMain
            if (!holding) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                    Text(vm.players[i], fontSize = 38.sp, fontWeight = FontWeight.Bold, color = onCard, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(14.dp))
                    Text("Görmek için basılı tut", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = TextSub)
                }
            } else if (isImpostor) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                    Text("Sen imposter'sın", fontSize = 15.sp, color = onCard.copy(alpha = 0.75f))
                    Spacer(Modifier.height(6.dp))
                    Text("IMPOSTER", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = onCard)
                    vm.impostorHint()?.let {
                        Spacer(Modifier.height(16.dp))
                        Text(it, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = onCard,
                            textAlign = TextAlign.Center, lineHeight = 28.sp)
                    }
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                    Text(vm.wordFor(i), fontSize = 38.sp, fontWeight = FontWeight.Bold, color = onCard,
                        textAlign = TextAlign.Center, lineHeight = 44.sp)
                    Spacer(Modifier.height(10.dp))
                    if (vm.showCategory)
                        Text(vm.categoryNameFor(i), fontSize = 16.sp, color = onCard.copy(alpha = 0.75f))
                }
            }
        }

        PrimaryButton(if (last) "Oyunu Başlat" else "Sonraki Oyuncuya Geç", enabled = seen && !holding) { vm.nextReveal() }
    }
}

@Composable
fun StartScreen(vm: GameViewModel) {
    BackHandler { vm.backToLobby() }
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier)
        Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Surface1).padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("İlk söz hakkı", fontSize = 16.sp, color = TextSub)
            Spacer(Modifier.height(10.dp))
            Text(vm.players[vm.starter], fontSize = 44.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(18.dp))
            Text("Sırayla tek kelimelik ipucu verin, sonra oylayın.", textAlign = TextAlign.Center, color = TextSub, fontSize = 15.sp)
        }
        PrimaryButton("Sonucu Göster") { vm.showResult() }
    }
}

@Composable
fun ResultScreen(vm: GameViewModel) {
    BackHandler { vm.backToLobby() }
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            when (vm.roundType) {
                RoundType.NORMAL -> {
                    ResultCard("Imposter", vm.impostors.sorted().joinToString(", ") { vm.players[it] }, Red)
                    ResultCard("Gizli kelime", vm.word, Green)
                    Text("Kategori: ${vm.category?.name}", color = TextSub, fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    if (vm.showHint)
                        Text("Imposter ipucu: ${vm.hintWord}", color = TextSub, fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
                RoundType.ALL_IMPOSTER -> {
                    ResultCard("Troll turu", "Herkes imposterdı!", Amber)
                    Text("Seçilen kelime: ${vm.word} (${vm.category?.name})", color = TextSub, fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
                RoundType.DIFFERENT -> {
                    ResultCard("Troll turu", "Herkese farklı kelime verildi!", Amber)
                    Column(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Surface1).padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        vm.players.forEachIndexed { i, p ->
                            Row {
                                Text(p, fontSize = 16.sp, color = TextSub, modifier = Modifier.weight(1f))
                                Text(vm.wordFor(i), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PrimaryButton("Hemen Yeni Tur") { vm.startRound() }
            SecondaryButton("Ayarları Değiştir") { vm.backToLobby() }
        }
    }
}

@Composable
private fun ResultCard(label: String, value: String, color: Color) {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Surface1).padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, color = TextSub, fontSize = 14.sp)
        Spacer(Modifier.height(6.dp))
        Text(value, fontSize = 34.sp, fontWeight = FontWeight.Bold, color = color,
            textAlign = TextAlign.Center, lineHeight = 40.sp)
    }
}
