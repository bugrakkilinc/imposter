# 🕵️‍♂️ Imposter — Android Parti Oyunu (Açık Kaynak)

[![Download APK](https://img.shields.io/badge/İndir-APK%20(v1.1.0)-blue?style=for-the-badge&logo=android)](https://github.com/bugrakkilinc/imposter/releases/latest)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

Arkadaş ortamlarında tek bir telefon üzerinden oynanabilen (**Pass & Play**), tamamen ücretsiz, reklamsız ve **çevrimdışı (offline)** çalışan minimalist bir sosyal çıkarım / blöf oyunudur.

Piyasadaki popüler imposter oyunlarının en iyi kategorileri ücretli abonelik veya rahatsız edici reklamlar arkasına saklamasına tepki olarak geliştirilmiştir. İsteyen herkes indirip oynayabilir, yeni kelimeler ekleyebilir veya kaynak kodunu çatallayarak (fork) geliştirebilir.

---

## 🎮 Oyun Nasıl Oynanır?

1. **Lobi:** Oyuncuları ekleyin (sıralamak için isimlerin üstüne basılı tutup sürükleyebilirsiniz), imposter sayısını ve dilediğiniz kategorileri seçin.
2. **Rol Dağıtımı:** Telefon elden ele dolaşır. Her oyuncu ekrandaki karta **basılı tutarak** gizli kelimesini ve rolünü öğrenir, parmağını çektiği an kart kapanır (arkadan gözetlemeleri engeller).
   - **Masumlar:** Gizli kelimeyi görür *(Örn: "Döner")*.
   - **Imposter:** Seçilen ayarlara göre ya hiçbir şey bilmez, ya sadece kategoriyi görür ya da kendisini ilk turda ele vermeyecek ilişkili bir ipucu kelimesi alır *(Örn: "Dürüm")*.
3. **Konuşma Turu:** Uygulama ilk söz hakkının kimde olduğunu söyler. Herkes sırayla kelimeyi bildiğini belli eden ama açık etmeyen tek kelimelik bir ipucu verir.
4. **Oylama & Sonuç:** Masada yapılan tartışmanın ardından **"Sonucu Göster"** butonuna basılarak Imposter ve kelime ifşa edilir. Tek tıkla hemen yeni tura geçilebilir!

---

## ✨ Özellikler

- **📱 Tek Cihaz (Pass & Play):** Herkesin ayrı ayrı uygulama indirmesine veya internet bağlantısına gerek yoktur.
- **👁️ Göz Yormayan Minimalist Arayüz:** Sade, modern, Flat Design karanlık tema.
- **🏷️ Geniş Kategori & Kelime Havuzu:**
  - 🍔 Yemekler
  - 🍰 Tatlı & İçecek
  - 🪑 Eşyalar
  - 📍 Mekanlar
  - 👷 Meslekler
  - ⭐ Ünlüler
  - 🎬 Dizi, Film & Çizgi
  - 🇹🇷 Türkiye & Şehirler
  - ⚽ Spor
  - 🦁 Hayvanlar
  - 🌳 Doğa & Bitkiler
  - 🎮 Teknoloji & Oyun
  - 🤡 Günlük Durumlar
  - 🏷️ Marka & Ürün
  - 🏛️ Tarih & Mitoloji
  - 🎉 Parti & Gece
  - 💘 Aşk & İlişki (+18)
- **🎯 Akıllı İpucu Sistemi:** Imposter'a verilen ipuçları özenle seçilmiştir; kelimeyi direkt ele vermez, ancak imposter'ın ilk turdan absürt bir şey söyleyip haksızca elenmesini de önler.
- **🎭 Troll Modu:** Açıldığında her tur rastgele iki sürprizden biri gerçekleşir:
  1. *Herkes Imposter:* Kimsenin kelimesi yoktur, herkes blöf yapmaya çalışır!
  2. *Herkese Farklı Kelime:* Her oyuncuya birbiriyle ilişkili farklı kelimeler verilir, masadaki kaos tavan yapar.
- **⚙️ Esnek Ayarlar:**
  - "Kategori herkese gösterilsin" aç/kapat
  - "Imposter ipucu alsın" aç/kapat
  - "Troll modu" aç/kapat
  - Sürükle-bırak ile oyuncu sırası değiştirme
- **💾 Yerel Hafıza:** Eklediğiniz oyuncular ve seçtiğiniz ayarlar telefonunuzda saklanır, uygulamayı kapatıp açtığınızda kaybolmaz.

---

## 🛠️ Teknik Detaylar & Proje Yapısı

- **Platform:** Android (minSdk: 24, targetSdk: 34)
- **Dil:** Kotlin
- **Arayüz (UI):** Jetpack Compose (Material 3)
- **Mimari:** MVVM (Model-View-ViewModel)
- **Durum Yönetimi:** Kotlin StateFlow & Compose MutableState
- **Depolama:** SharedPreferences (Yerel, sıfır ağ bağımlılığı)

---

## 🚀 Projeyi Derleme ve Çalıştırma

### Gereksinimler
- Android Studio Ladybug / Jellyfish veya daha yenisi (veya JDK 17)
- Android SDK (API 34)

### Terminal Üzerinden:
```bash
# Projeyi klonlayın
git clone https://github.com/<KULLANICI_ADINIZ>/imposter.git
cd imposter

# Debug APK derleyin
./gradlew assembleDebug

# Bağlı cihaza doğrudan yükleyin
./gradlew installDebug
```

Çıkan APK dosyası `app/build/outputs/apk/debug/app-debug.apk` konumunda yer alır.

---

## 🤝 Katkıda Bulunma

Yeni kelimeler, yeni kategoriler veya yeni oyun mekanikleri eklemek çok kolaydır!

1. Bu depoyu **Fork**'layın.
2. `app/src/main/java/com/bugra/imposter/Words.kt` dosyasına girip yeni kelimeler veya kategoriler ekleyin (Format: `"Kelime:İpucu"`).
3. Değişikliklerinizi commit edin ve bir **Pull Request (PR)** açın.

---

## 📄 Lisans

Bu proje [MIT Lisansı](LICENSE) altında açık kaynak olarak sunulmaktadır. Dilediğiniz gibi kullanabilir, değiştirebilir ve dağıtabilirsiniz.
