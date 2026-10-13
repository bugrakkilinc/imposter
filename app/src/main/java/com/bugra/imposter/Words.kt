package com.bugra.imposter

/**
 * [hint] is the word shown to the impostor. Rule of thumb: it must fit at least
 * 2-3 other words in the same category, so the impostor can blend in without
 * being able to pin down the exact secret word.
 */
data class Entry(val word: String, val hint: String)

data class Category(val name: String, val emoji: String, val words: List<Entry>, val adult: Boolean = false)

/** Format: "Kelime:İpucu, Kelime:İpucu, ..." */
private fun c(name: String, emoji: String, words: String, adult: Boolean = false) =
    Category(
        name, emoji,
        words.split(",").map { it.trim() }.filter { it.contains(":") }
            .map { val (w, h) = it.split(":", limit = 2); Entry(w.trim(), h.trim()) }
            .distinctBy { it.word },
        adult
    )

val ALL_CATEGORIES: List<Category> = listOf(
    c("Yemekler", "🍔",
        "Döner:Et, Lahmacun:Fırın, Pide:Fırın, Kebap:Mangal, Mantı:Hamur, Karnıyarık:Sebze, Menemen:Kahvaltı, " +
        "Simit:Kahvaltı, Börek:Hamur, Gözleme:Köy, Köfte:Mangal, Pilav:Tencere, Mercimek Çorbası:Kaşık, " +
        "Yaprak Sarma:Zeytinyağı, Pizza:Fırın, Hamburger:Fast food, Sushi:Pirinç, Makarna:Haşlama, Çiğ Köfte:Acı, " +
        "Kokoreç:Gece, Midye Dolma:Sahil, Islak Hamburger:Gece, Tost:Peynir, Cacık:Yoğurt, Kuru Fasulye:Tencere, " +
        "İskender:Yoğurt, Balık Ekmek:Sahil, Patates Kızartması:Yağ, Omlet:Tava, Mücver:Sebze"),
    c("Tatlı & İçecek", "🍰",
        "Baklava:Bayram, Künefe:Sıcak, Sütlaç:Süt, Kazandibi:Süt, Lokum:Misafir, Dondurma:Yaz, Çay:Sabah, " +
        "Türk Kahvesi:Köpük, Ayran:Serin, Salep:Kış, Limonata:Yaz, Boza:Kış, Şalgam:Ekşi, Kola:Gazlı, Milkshake:Pipet, " +
        "Cheesecake:Kafe, Tiramisu:Kafe, Profiterol:Çikolata, Revani:Şerbet, Aşure:Komşu, Su:Şişe, Meyve Suyu:Kutu, " +
        "Soğuk Kahve:Buz, Gazoz:Gazlı, Helva:Kaşık, Kek:Fırın, Kurabiye:Fırın, Sıcak Çikolata:Kış, Kadayıf:Şerbet"),
    c("Eşyalar", "🪑",
        "Makas:Kesmek, Çekiç:Alet, Şemsiye:Katlanır, Anahtar:Cep, Cüzdan:Cep, Gözlük:Yüz, Kol Saati:Pil, " +
        "Telefon:Şarj, Şarj Aleti:Priz, Buzdolabı:Mutfak, Ütü:Sıcak, Süpürge:Temizlik, Saç Fırçası:Banyo, " +
        "Havlu:Banyo, Yastık:Yatak, Battaniye:Yatak, Bardak:Mutfak, Çaydanlık:Mutfak, Tost Makinesi:Priz, " +
        "Kulaklık:Kablo, Powerbank:Şarj, Pil:Enerji, Ayna:Banyo, Tarak:Cep, Diş Fırçası:Banyo, Terlik:Ev, " +
        "Tabure:Katlanır, Fener:Kamp, Çakmak:Cep, Valiz:Tatil"),
    c("Mekanlar", "📍",
        "Hastane:Bekleme, Okul:Teneffüs, Berber:Ayna, Lunapark:Bilet, Havalimanı:Güvenlik, Sinema:Bilet, " +
        "Kütüphane:Sessizlik, Market:Kasa, Cami:Ayakkabı, Plaj:Güneş, Orman:Yürüyüş, Müze:Bilet, Stadyum:Kalabalık, " +
        "Otogar:Yolcu, Hamam:Havlu, Pastane:Vitrin, Kamp Alanı:Ateş, Karakol:Güvenlik, Banka:Sıra, Düğün Salonu:Dans, " +
        "Asansör:Kat, Çatı Katı:Merdiven, Bodrum:Karanlık, Otopark:Araba, Çamaşırhane:Makine, Eczane:Reçete, " +
        "Benzinlik:Yol, Pazar Yeri:Pazarlık, Tren İstasyonu:Yolcu, Hayvanat Bahçesi:Çocuk"),
    c("Meslekler", "👷",
        "Doktor:Önlük, Öğretmen:Sınıf, Pilot:Üniforma, Aşçı:Önlük, Marangoz:Atölye, Tesisatçı:Tamir, " +
        "Astronot:Yükseklik, Dedektif:Gizli, Barista:Tezgah, Hakem:Maç, Avukat:Takım elbise, Mimar:Çizim, " +
        "İtfaiyeci:Kahraman, Kasap:Bıçak, Fotoğrafçı:Düğün, Şoför:Yol, Kuaför:Ayna, Garson:Bahşiş, Terzi:Ölçü, " +
        "Gazeteci:Haber, Çiftçi:Toprak, Elektrikçi:Tamir, Polis:Üniforma, Hemşire:Nöbet, Yazılımcı:Bilgisayar, " +
        "Kaptan:Üniforma, Veteriner:Hayvan, Diş Hekimi:Koltuk, Postacı:Kapı, Balıkçı:Sabah"),
    c("Ünlüler", "⭐",
        "Tarkan:Pop, Sezen Aksu:Pop, Cem Yılmaz:Komedi, Kemal Sunal:Yeşilçam, Atatürk:Lider, " +
        "Fatih Sultan Mehmet:Tarih, Barış Manço:Efsane, Ajda Pekkan:Diva, Lionel Messi:Futbol, " +
        "Cristiano Ronaldo:Futbol, Arda Turan:Futbol, Hadise:Sahne, Albert Einstein:Bilim, Leonardo da Vinci:Sanat, " +
        "Elon Musk:Zengin, Mozart:Klasik, Picasso:Sanat, Shakira:Dans, Michael Jackson:Dans, Kenan Doğulu:Pop, " +
        "Müslüm Gürses:Duygusal, Orhan Gencebay:Usta, Acun Ilıcalı:Televizyon, Ata Demirer:Komedi, " +
        "Şahan Gökbakar:Komedi, Kıvanç Tatlıtuğ:Dizi, Haluk Levent:Yardım, Barack Obama:Politika, " +
        "Charlie Chaplin:Siyah beyaz, Nasreddin Hoca:Hikaye"),
    c("Dizi, Film & Çizgi", "🎬",
        "Kurtlar Vadisi:Mafya, Leyla ile Mecnun:Absürt, Recep İvedik:Komedi, Hababam Sınıfı:Okul, Harry Potter:Büyü, " +
        "Titanic:Aşk, Yüzüklerin Efendisi:Macera, Star Wars:Uzay, Matrix:Gelecek, Batman:Kahraman, " +
        "Örümcek Adam:Kahraman, Shrek:Masal, Aslan Kral:Disney, Karayip Korsanları:Deniz, Squid Game:Kore, " +
        "Breaking Bad:Suç, Game of Thrones:Savaş, Friends:Sitcom, Avatar:Gelecek, Inception:Zihin, Eşkıya:İntikam, " +
        "Çalgı Çengi:Müzik, Gora:Uzay, Babam ve Oğlum:Ağlatan, Tom ve Jerry:Kovalamaca, Sünger Bob:Deniz, " +
        "Aşk-ı Memnu:Yalı, Muhteşem Yüzyıl:Tarih, Aile Arasında:Düğün, Yedi Numara:Mahalle"),
    c("Türkiye & Şehirler", "🇹🇷",
        "İstanbul:Kalabalık, Ankara:Memur, İzmir:Ege, Kapadokya:Turist, Pamukkale:Termal, Efes Antik Kenti:Antik, " +
        "Ayasofya:Tarihi, Galata Kulesi:Manzara, Boğaz Köprüsü:Trafik, Anıtkabir:Ziyaret, Nemrut Dağı:Gün doğumu, " +
        "Sümela Manastırı:Karadeniz, Antalya:Tatil, Bodrum:Tatil, Trabzon:Karadeniz, Gaziantep:Yemek, Mardin:Taş, " +
        "Van Gölü:Doğu, Uludağ:Kar, Çanakkale:Tarih, Kız Kulesi:Manzara, Safranbolu:Tarihi, Konya:İç Anadolu, " +
        "Bursa:Yeşil, Eskişehir:Öğrenci, Rize:Karadeniz, Ağrı Dağı:Zirve, Hatay:Yemek, Edirne:Sınır, Sinop:Sahil"),
    c("Spor", "⚽",
        "Futbol:Top, Basketbol:Top, Voleybol:Takım, Tenis:Raket, Yüzme:Su, Boks:Ring, Güreş:Minder, Okçuluk:Hedef, " +
        "Eskrim:Maske, Golf:Sopa, Satranç:Strateji, Dart:Hedef, Bilardo:Masa, Kayak:Kış, Sörf:Dalga, " +
        "Maraton:Dayanıklılık, Korner:Futbol, Penaltı:Kaleci, Ofsayt:Hakem, Smaç:Zıplamak, Forma:Takım, " +
        "Krampon:Çim, Halter:Ağırlık, Jimnastik:Esneklik, Badminton:Raket, Masa Tenisi:Raket, " +
        "Yamaç Paraşütü:Rüzgar, Bisiklet:Kask, Buz Pateni:Kış, Kaleci:Eldiven"),
    c("Hayvanlar", "🦁",
        "Aslan:Vahşi, Kaplan:Vahşi, Fil:Büyük, Zürafa:Afrika, Penguen:Soğuk, Köpekbalığı:Deniz, Yunus:Deniz, " +
        "Kedi:Evcil, Köpek:Evcil, Papağan:Renkli, Kartal:Kanat, Yılan:Sürüngen, Timsah:Sürüngen, Kaplumbağa:Yavaş, " +
        "Tavşan:Hızlı, Ayı:Orman, Kurt:Sürü, Tilki:Orman, Kanguru:Zıplamak, Panda:Sevimli, Deve:Çöl, At:Çiftlik, " +
        "Karınca:Küçük, Arı:Çiçek, Ahtapot:Deniz, Flamingo:Renkli, Baykuş:Gece, Maymun:Ağaç, Koala:Ağaç, Kirpi:Gece"),
    c("Doğa & Bitkiler", "🌳",
        "Gül:Çiçek, Papatya:Çiçek, Lale:Bahar, Çam Ağacı:Orman, Kaktüs:Saksı, Mantar:Orman, Şelale:Su, Volkan:Dağ, " +
        "Çöl:Sıcak, Okyanus:Mavi, Nehir:Su, Mağara:Karanlık, Gökkuşağı:Yağmur, Yıldırım:Yağmur, Kar Tanesi:Kış, " +
        "Sis:Sabah, Deprem:Doğal afet, Fırtına:Rüzgar, Ay Tutulması:Gökyüzü, Orman Yangını:Doğal afet, Çimen:Yeşil, " +
        "Bambu:Yeşil, Zeytin Ağacı:Ege, Göl:Su, Dağ:Yüksek, Sahil:Kum, Buzul:Soğuk, Ay:Gökyüzü, Güneş:Gökyüzü, " +
        "Ayçiçeği:Sarı"),
    c("Teknoloji & Oyun", "🎮",
        "WhatsApp:Uygulama, Instagram:Uygulama, TikTok:Video, YouTube:Video, Netflix:Abonelik, Minecraft:Yapı, " +
        "Fortnite:Savaş, PlayStation:Kol, Mario:Klasik, Pokémon:Karakter, Among Us:Arkadaş, Google:İnternet, " +
        "ChatGPT:İnternet, Bluetooth:Kablosuz, Wi-Fi:Kablosuz, Robot:Gelecek, Drone:Kumanda, Laptop:Ekran, " +
        "Klavye:Masa, Mouse:Masa, Tetris:Klasik, FIFA:Maç, GTA:Araba, Valorant:Takım, Steam:Bilgisayar, " +
        "Spotify:Müzik, Discord:Sohbet, Akıllı Saat:Bilek, Yazıcı:Ofis, Hoparlör:Ses"),
    c("Günlük Durumlar", "🤡",
        "Otobüste Ayakta Kalmak:Kalabalık, Alarm Çalmak:Sabah, Wi-Fi Şifresi:Misafir, Şarjı Bitmek:Telefon, " +
        "Sınav Haftası:Stres, Pazartesi Sendromu:Sabah, Kargo Beklemek:Sabır, Zam Gelmek:Para, Kuyrukta Beklemek:Sabır, " +
        "Toplantıda Uyuklamak:İş, Düğün Yemeği:Kalabalık, Çay Molası:İş, Mesaja Cevap Alamamak:Telefon, " +
        "Grup Sohbeti:Bildirim, Bayram Ziyareti:Aile, Misafir Gelmek:Temizlik, Fatura Ödemek:Para, " +
        "Telefonu Kaybetmek:Panik, Yağmurda Islanmak:Hava, Trafikte Kalmak:Sabır, İnternetin Kopması:Sinir, " +
        "Anahtarı Unutmak:Panik, Yanlış Numara:Telefon, Ev Taşımak:Yorgunluk, Diyete Başlamak:Pazartesi, " +
        "Uyuyakalmak:Geç, Bulaşık Sırası:Kavga"),
    c("Marka & Ürün", "🏷️",
        "Apple:Telefon, Samsung:Telefon, Nike:Spor, Adidas:Spor, Coca-Cola:İçecek, Pepsi:İçecek, Red Bull:İçecek, " +
        "Ülker:Atıştırmalık, Eti:Atıştırmalık, Nutella:Kahvaltı, Arçelik:Beyaz eşya, Vestel:Elektronik, " +
        "Migros:Market, Trendyol:Kargo, Hepsiburada:Kargo, Getir:Hızlı, Starbucks:Kahve, McDonald's:Fast food, " +
        "Burger King:Fast food, IKEA:Mobilya, Zara:Giyim, Tesla:Araba, Mercedes:Araba, BMW:Araba, Togg:Araba, " +
        "Toyota:Araba, Lego:Oyuncak, Turkcell:Hat, Türk Hava Yolları:Seyahat, Netflix:Dizi"),
    c("Tarih & Mitoloji", "🏛️",
        "Truva Atı:Hile, Zeus:Tanrı, Poseidon:Tanrı, Medusa:Canavar, Herakles:Kahraman, Kleopatra:Kraliçe, " +
        "Cengiz Han:Fetih, Napolyon:İmparator, Kristof Kolomb:Keşif, Piramitler:Antik, Çin Seddi:Asya, " +
        "Vikingler:Savaşçı, Gladyatör:Savaşçı, Osmanlı:İmparatorluk, Selçuklu:Devlet, Malazgirt Savaşı:Zafer, " +
        "Kurtuluş Savaşı:Zafer, Rönesans:Avrupa, İpek Yolu:Ticaret, Roma İmparatorluğu:İmparatorluk, " +
        "Samuray:Savaşçı, Şövalye:Savaşçı, Korsan:Deniz, Firavun:Mısır, Atlantis:Efsane, Kral Arthur:Efsane, " +
        "Ay'a İlk Adım:Uzay, Matbaa:İcat, Tekerlek:İcat"),
    c("Parti & Gece", "🎉",
        "Doğum Günü:Hediye, Sürpriz Parti:Gizli, Konfeti:Renkli, DJ:Müzik, Ev Partisi:Kalabalık, Barbekü:Bahçe, " +
        "Şişe Çevirmece:Oyun, Doğruluk mu Cesaretlik mi:Oyun, Maskeli Balo:Kostüm, Kokteyl:Bardak, Disko Topu:Işık, " +
        "Karaoke:Şarkı, Dans Pisti:Kalabalık, Kulüp:Gece, Havai Fişek:Gökyüzü, Yılbaşı Gecesi:Kutlama, " +
        "Bekarlığa Veda:Kutlama, Piknik:Açık hava, Kamp Ateşi:Açık hava, Pasta:Mum, Balon:Renkli, " +
        "Çalma Listesi:Müzik, Kostüm Partisi:Kostüm, Mezuniyet:Kutlama"),
    c("Aşk & İlişki (+18)", "💘",
        "İlk Buluşma:Heyecan, Eski Sevgili:Geçmiş, Kıskançlık:Duygu, Flört:Heyecan, Evlilik Teklifi:Sürpriz, " +
        "Yüzük:Hediye, Gizli Aşk:Sır, Romantik Akşam Yemeği:Mum, Gül Buketi:Hediye, Sevgililer Günü:Hediye, " +
        "Nişan:Aile, Balayı:Tatil, Kör Randevu:Tanışmak, Aşk Mektubu:Duygu, Barışmak:Kavga, Ayrılık:Gözyaşı, " +
        "Çiftler Tatili:Tatil, Seen Atmak:Telefon, Terk Edilmek:Gözyaşı, İlk Öpücük:Heyecan, Aldatılmak:Sır, " +
        "Kaçamak:Sır", adult = true),
)
