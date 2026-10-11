package com.bugra.imposter

/** [hint] is a loosely related word given to the impostor: not too obvious, not too far off. */
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
        "Döner:Dürüm, Lahmacun:Limon, Pide:Kaşarlı, Kebap:Mangal, Mantı:Yoğurt, Karnıyarık:Patlıcan, Menemen:Yumurta, Simit:Susam, Börek:Yufka, Gözleme:Sac, Köfte:Kıyma, Pilav:Pirinç, Mercimek Çorbası:Kaşık, Dolma:Yaprak, Pizza:İtalya, Hamburger:Ketçap, Sushi:Japonya, Makarna:Sos, Çiğ Köfte:Marul, Kokoreç:Gece, Midye Dolma:Limon, Islak Hamburger:Taksim, Tost:Kaşar, Cacık:Salatalık, Kuru Fasulye:Pilav, İskender:Tereyağı, Balık Ekmek:Eminönü, Patates Kızartması:Yağ, Omlet:Tava"),
    c("Tatlı & İçecek", "🍰",
        "Baklava:Fıstık, Künefe:Peynir, Sütlaç:Fırın, Kazandibi:Karamel, Lokum:Şeker, Dondurma:Külah, Çay:Demlik, Türk Kahvesi:Fal, Ayran:Yoğurt, Salep:Tarçın, Limonata:Ekşi, Boza:Kış, Şalgam:Adana, Kola:Gazlı, Milkshake:Pipet, Cheesecake:Bisküvi, Tiramisu:İtalya, Profiterol:Çikolata, Revani:Şerbet, Aşure:Muharrem, Maraş Dondurması:Kahramanmaraş, Su:Bidon, Meyve Suyu:Karton, Soğuk Kahve:Buz, Gazoz:Kapak, Helva:Tahin, Kek:Dilim, Kurabiye:Tepsi"),
    c("Eşyalar", "🪑",
        "Makas:Kâğıt, Çekiç:Çivi, Şemsiye:Yağmur, Anahtar:Kilit, Cüzdan:Kart, Gözlük:Cam, Kol Saati:Bilek, Telefon:Ekran, Şarj Aleti:Priz, Buzdolabı:Mutfak, Ütü:Gömlek, Süpürge:Toz, Saç Fırçası:Banyo, Havlu:Duş, Yastık:Uyku, Battaniye:Soğuk, Bardak:Su, Çaydanlık:Semaver, Tost Makinesi:Ekmek, Kulaklık:Kablo, Powerbank:Pil, Pil:Kutup, Ayna:Yansıma, Tarak:Diş, Diş Fırçası:Macun, Terlik:Ev, Tabure:Oturmak, Fener:Karanlık, Çakmak:Ateş, Valiz:Yolculuk"),
    c("Mekanlar", "📍",
        "Hastane:Randevu, Okul:Zil, Berber:Ustura, Lunapark:Dönme Dolap, Havalimanı:Bavul, Sinema:Mısır, Kütüphane:Sessizlik, Market:Sepet, Cami:Minare, Plaj:Şezlong, Orman:Ağaç, Müze:Bilet, Otogar:Peron, Hamam:Buhar, Pastane:Vitrin, Kamp Alanı:Çadır, Karakol:Nöbet, Banka:Kasa, Düğün Salonu:Gelinlik, Asansör:Kat, Çatı Katı:Merdiven, Bodrum:Rutubet, Otopark:Araba, Çamaşırhane:Deterjan, Eczane:İlaç, Benzinlik:Pompa, Pazar Yeri:Tezgah, Tren İstasyonu:Vagon, Hayvanat Bahçesi:Kafes"),
    c("Meslekler", "👷",
        "Doktor:Stetoskop, Öğretmen:Tahta, Pilot:Kokpit, Aşçı:Önlük, Marangoz:Testere, Tesisatçı:Boru, Astronot:Uzay, Dedektif:İz, Barista:Espresso, Hakem:Düdük, Avukat:Dava, Mimar:Proje, İtfaiyeci:Hortum, Kasap:Et, Fotoğrafçı:Objektif, Şoför:Direksiyon, Kuaför:Makas, Garson:Tepsi, Terzi:Mezura, Gazeteci:Mikrofon, Çiftçi:Tarla, Elektrikçi:Kablo, Polis:Kelepçe, Hemşire:Serum, Yazılımcı:Kod, Kaptan:Dümen, Veteriner:Aşı, Diş Hekimi:Dolgu, Postacı:Zarf, Balıkçı:Olta"),
    c("Ünlüler", "⭐",
        "Tarkan:Şımarık, Sezen Aksu:Serçe, Cem Yılmaz:Stand-up, Kemal Sunal:Tosun, Atatürk:Cumhuriyet, Fatih Sultan Mehmet:İstanbul, Barış Manço:Bıyık, Ajda Pekkan:Süperstar, Lionel Messi:Arjantin, Cristiano Ronaldo:Portekiz, Arda Turan:Barcelona, Hadise:Düm Tek Tek, Albert Einstein:Görelilik, Leonardo da Vinci:Mona Lisa, Elon Musk:Roket, Mozart:Piyano, Picasso:Tablo, Shakira:Kolombiya, Michael Jackson:Moonwalk, Kenan Doğulu:Shake It Up, Müslüm Gürses:Arabesk, Orhan Gencebay:Bağlama, Acun Ilıcalı:Survivor, Ata Demirer:Eyvah Eyvah, Şahan Gökbakar:Komedi, Kıvanç Tatlıtuğ:Dizi, Haluk Levent:Konser, Barack Obama:Amerika, Charlie Chaplin:Sessiz Film, Nasreddin Hoca:Fıkra"),
    c("Dizi, Film & Çizgi", "🎬",
        "Kurtlar Vadisi:Polat Alemdar, Leyla ile Mecnun:Mecnun, Recep İvedik:Şahan, Hababam Sınıfı:Okul, Harry Potter:Büyücü, Titanic:Buzdağı, Yüzüklerin Efendisi:Yüzük, Star Wars:Uzay, Matrix:Hap, Batman:Gotham, Örümcek Adam:Ağ, Shrek:Bataklık, Aslan Kral:Simba, Karayip Korsanları:Gemi, Squid Game:Yeşil Eşofman, Breaking Bad:Kimya, Game of Thrones:Ejderha, Friends:Kahve, Avatar:Mavi, Inception:Rüya, Eşkıya:Baran, Çalgı Çengi:Müzisyen, Gora:Uzaylı, Babam ve Oğlum:Dram, Tom ve Jerry:Fare, Sünger Bob:Ananas, Aşk-ı Memnu:Bihter, Muhteşem Yüzyıl:Saray, Aile Arasında:Mahalle, Yedi Numara:Komedi"),
    c("Türkiye & Şehirler", "🇹🇷",
        "İstanbul:Boğaz, Ankara:Başkent, İzmir:Kordon, Kapadokya:Balon, Pamukkale:Beyaz, Efes Antik Kenti:Antik, Ayasofya:Kubbe, Galata Kulesi:Manzara, Boğaz Köprüsü:Asya, Anıtkabir:Nöbet, Nemrut Dağı:Heykel, Sümela Manastırı:Uçurum, Antalya:Turizm, Bodrum:Marina, Trabzon:Hamsi, Gaziantep:Fıstık, Mardin:Taş, Van Gölü:Kedi, Uludağ:Kayak, Çanakkale:Zafer, Kız Kulesi:Efsane, Safranbolu:Konak, Konya:Mevlana, Bursa:Kestane, Eskişehir:Porsuk, Rize:Çay, Ağrı Dağı:Nuh, Hatay:Künefe, Edirne:Selimiye, Sinop:Kuzey"),
    c("Spor", "⚽",
        "Futbol:Gol, Basketbol:Pota, Voleybol:Servis, Tenis:Kort, Yüzme:Havuz, Boks:Eldiven, Güreş:Yağ, Okçuluk:Ok, Eskrim:Kılıç, Golf:Çim, Satranç:Şah, Dart:Hedef, Bilardo:Isteka, Kayak:Kar, Sörf:Dalga, Maraton:Koşu, Korner:Köşe, Penaltı:Kaleci, Ofsayt:Bayrak, Smaç:Potaya, File:Ağ, Düdük:Hakem, Forma:Numara, Krampon:Ayakkabı, Raket:Tel, Halter:Kaldırmak, Jimnastik:Barfiks, Badminton:Tüy, Masa Tenisi:Top, Yamaç Paraşütü:Yükseklik"),
    c("Hayvanlar", "🦁",
        "Aslan:Yele, Kaplan:Çizgi, Fil:Hortum, Zürafa:Boyun, Penguen:Buz, Köpekbalığı:Yüzgeç, Yunus:Zeki, Kedi:Miyav, Köpek:Havlamak, Papağan:Konuşan, Kartal:Pençe, Yılan:Zehir, Timsah:Nehir, Kaplumbağa:Kabuk, Tavşan:Havuç, Ayı:Bal, Kurt:Uluma, Tilki:Kurnaz, Kanguru:Avustralya, Panda:Bambu, Deve:Hörgüç, At:Nal, Karınca:Koloni, Arı:Petek, Ahtapot:Kol, Flamingo:Pembe, Baykuş:Gece, Maymun:Muz, Koala:Okaliptüs, Kirpi:Diken"),
    c("Doğa & Bitkiler", "🌳",
        "Gül:Diken, Papatya:Fal, Lale:Hollanda, Çam Ağacı:Kozalak, Kaktüs:Susuz, Mantar:Yağmur, Şelale:Akıntı, Volkan:Lav, Çöl:Kum, Okyanus:Derin, Nehir:Yatak, Mağara:Karanlık, Gökkuşağı:Renk, Yıldırım:Bulut, Kar Tanesi:Kış, Sis:Görüş, Deprem:Fay, Fırtına:Rüzgar, Ay Tutulması:Gölge, Orman Yangını:Duman, Çimen:Yeşil, Bambu:Panda, Zeytin Ağacı:Yağ, Göl:Sakin, Dağ:Zirve, Sahil:Dalga, Çiğ:Sabah, Buzul:Kutup, Ay:Gece, Güneş:Sıcak"),
    c("Teknoloji & Oyun", "🎮",
        "WhatsApp:Mesaj, Instagram:Hikaye, TikTok:Dans, YouTube:Abone, Netflix:Dizi, Minecraft:Küp, Fortnite:Savaş, PlayStation:Kol, Mario:Mantar, Pokémon:Yakalamak, Among Us:Astronot, Google:Arama, ChatGPT:Yapay Zeka, Bluetooth:Eşleşme, Wi-Fi:Şifre, Robot:Metal, Drone:Pervane, Laptop:Taşınabilir, Klavye:Tuş, Mouse:Tık, Tetris:Blok, FIFA:Kupa, GTA:Şehir, Valorant:Ajan, Steam:İndirim, Spotify:Liste, Discord:Sunucu, Akıllı Saat:Adım, Yazıcı:Kağıt, Hoparlör:Ses"),
    c("Günlük Durumlar", "🤡",
        "Otobüste Ayakta Kalmak:Tutunmak, Alarm Çalmak:Erken, Wi-Fi Şifresi:Misafir, Şarjı Bitmek:Yüzde Bir, Sınav Haftası:Ders, Pazartesi Sendromu:Hafta, Kargo Beklemek:Kapı, Zam Gelmek:Fiyat, Kuyrukta Beklemek:Sıra, Toplantıda Uyuklamak:Sunum, Düğün Yemeği:Davetli, Çay Molası:Bardak, Mesaja Cevap Alamamak:Mavi Tik, Grup Sohbeti:Sessize Al, Bayram Ziyareti:Harçlık, Misafir Gelmek:Temizlik, Fatura Ödemek:Son Gün, Telefonu Kaybetmek:Aramak, Yağmurda Islanmak:Şemsiyesiz, Trafikte Kalmak:Korna, İnternetin Kopması:Modem, Kapıda Anahtarı Unutmak:Çilingir, Yanlış Numara:Özür"),
    c("Marka & Ürün", "🏷️",
        "Apple:iPhone, Samsung:Galaxy, Nike:Tik, Adidas:Üç Çizgi, Coca-Cola:Kırmızı, Ülker:Çikolata, Eti:Bisküvi, Arçelik:Beyaz Eşya, Vestel:Televizyon, Migros:Money, Trendyol:Kargo, Hepsiburada:Sipariş, Getir:Kurye, Starbucks:Yeşil, McDonald's:Happy Meal, Burger King:Taç, IKEA:Montaj, Zara:Moda, Tesla:Elektrikli, Mercedes:Yıldız, BMW:Almanya, Ford:Mustang, Toyota:Corolla, Lego:Plastik, Red Bull:Kanat, Pepsi:Mavi, Nutella:Fındık, Turkcell:Hat, Türk Hava Yolları:Uçak, Togg:Yerli"),
    c("Tarih & Mitoloji", "🏛️",
        "Truva Atı:Tahta, Zeus:Şimşek, Poseidon:Deniz, Medusa:Taş, Herakles:Güç, Kleopatra:Mısır, Cengiz Han:Moğol, Napolyon:Fransa, Kristof Kolomb:Amerika, Piramitler:Mumya, Çin Seddi:Uzun, Vikingler:Kuzey, Gladyatör:Arena, Osmanlı:Padişah, Selçuklu:Anadolu, Malazgirt Savaşı:1071, Kurtuluş Savaşı:Sakarya, Rönesans:Sanat, İpek Yolu:Kervan, Roma İmparatorluğu:Sezar, Samuray:Katana, Şövalye:Zırh, Korsan:Hazine, Firavun:Tutankamon, Atlantis:Kayıp, Kral Arthur:Yuvarlak Masa, Ay'a İlk Adım:Apollo, Matbaa:Gutenberg, Tekerlek:Dönmek"),
    c("Parti & Gece", "🎉",
        "Doğum Günü:Mum, Sürpriz Parti:Saklanmak, Konfeti:Patlatmak, DJ:Plak, Ev Partisi:Komşu, Barbekü:Kömür, Şişe Çevirmece:Daire, Doğruluk mu Cesaretlik mi:Soru, Maskeli Balo:Gizem, Kokteyl:Buz, Disko Topu:Işık, Karaoke:Mikrofon, Dans Pisti:Müzik, Kulüp:Giriş, Bar:Tezgah, Havai Fişek:Gökyüzü, Yılbaşı Gecesi:Geri Sayım, Bekarlığa Veda:Arkadaşlar, Piknik:Örtü, Kamp Ateşi:Gitar, Pasta:Krema, Balon:Hava, Müzik Listesi:Karıştır, Kadeh:Cam"),
    c("Aşk & İlişki (+18)", "💘",
        "İlk Buluşma:Heyecan, Eski Sevgili:Geçmiş, Kıskançlık:Güven, Flört:Bakış, Mesaj Atmak:Yazıyor, Evlilik Teklifi:Diz, Yüzük:Parmak, Gizli Aşk:Sır, Romantik Akşam Yemeği:Mum, Gül Buketi:Çiçekçi, Sevgililer Günü:Şubat, Nişan:Aile, Balayı:Tatil, Kör Randevu:Tanışmak, Aşk Mektubu:Kalem, Barışmak:Kırgın, Terk Edilmek:Yalnız, Çiftler Tatili:Otel, Seen Atmak:Cevap, Ayrılık:Veda", adult = true),
)
