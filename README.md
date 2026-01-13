<div align="center">

# SmsToTelegramBot TR/ EN

Android cihazınıza gelen **SMS** mesajlarını otomatik olarak Telegram'a ileten hafif bir uygulama.

[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-orange?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![API](https://img.shields.io/badge/API%20level-26--34-blue)](https://developer.android.com/guide/topics/manifest/uses-sdk-element)

</div>

## Özellikler

- Gelen tüm SMS'leri anında Telegram sohbetine/grubuna iletir  
- Cihaz yeniden başlatıldığında otomatik başlar (Boot Receiver)  
- Android 14 dahil modern kısıtlamalara uyumlu **Foreground Service** kullanımı  
- Pil optimizasyonundan etkilenmemek için foreground servisi bildirimi  
- Basit yapılandırma → sadece Bot Token ve Chat ID girmeniz yeterli  
- Orta seviye güvenlik: mesajlar cihazınızdan doğrudan Telegram sunucularına gider (aracı sunucu yok)

## Kullanım Alanları

- Banka SMS onay kodlarını uzaktan takip  
- Sunucu/uyarı SMS'lerini Telegram'a toplama  
- Fiziksel olarak erişemediğiniz telefondaki SMS'leri okuma  
- İkinci hat / yedek telefon SMS izleme

## Gereksinimler

- Android 8.0 (Oreo) ve üzeri  
- Telegram hesabı  
- @BotFather ile oluşturulmuş bir bot

## Kurulum (5 Dakika)

1. **Telegram Bot Oluşturma**
   - Telegram'da `@BotFather` ile konuşun  
   - `/newbot` komutuyla bot oluşturun  
   - Aldığınız **BOT_TOKEN**'ı not edin

2. **Chat ID Bulma**
   - Botunuzu kendinize veya istediğiniz gruba ekleyin  
   - https://t.me/userinfobot veya @getidsbot ile chat id'nizi öğrenin  
   - (Grup için negatif ID döner → -1001234567890 gibi)

3. **Kodda Değişiklik Yapma**
   `app/src/main/java/.../Constants.kt` (veya ilgili dosya) içinde:

   ```kotlin
   const val TELEGRAM_BOT_TOKEN = "1234567890:AAF1b2C3d4e5f6g7h8i9j0kLmNoPqRsTuVwX"
   const val TELEGRAM_CHAT_ID   = "-1001234567890"   // veya "123456789" (kişisel)

   <div align="center">

# SmsToTelegramBot

A lightweight Android application that automatically forwards incoming **SMS** messages to Telegram.

[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-orange?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![API](https://img.shields.io/badge/API%20level-26--34-blue)](https://developer.android.com/guide/topics/manifest/uses-sdk-element)

</div>

## Features

- Forwards all incoming SMS instantly to a Telegram chat or group  
- Auto-starts on device boot (Boot Receiver)  
- Uses **Foreground Service** compatible with modern restrictions (including Android 14)  
- Persistent foreground notification to avoid battery optimization killing  
- Very simple setup → only Bot Token + Chat ID required  
- Reasonable privacy: messages go directly from your device to Telegram servers (no middleman server)

## Use Cases

- Remote monitoring of bank OTP / verification SMS  
- Collecting server alerts or notification SMS in Telegram  
- Reading SMS from a phone you cannot physically access  
- Monitoring secondary SIM / backup phone messages

## Requirements

- Android 8.0 (Oreo) or higher  
- Telegram account  
- A bot created via @BotFather

## Setup (≈5 minutes)

1. **Create a Telegram Bot**
   - Talk to `@BotFather` in Telegram  
   - Use `/newbot` command to create your bot  
   - Copy the **BOT_TOKEN** you receive

2. **Find your Chat ID**
   - Add your bot to yourself or to the desired group  
   - Use https://t.me/userinfobot or @getidsbot to get your chat ID  
   - (Group IDs are negative, e.g. -1001234567890)

3. **Update the code**
   In `app/src/main/java/.../Constants.kt` (or the relevant constants file):

   ```kotlin
   const val TELEGRAM_BOT_TOKEN = "1234567890:AAF1b2C3d4e5f6g7h8i9j0kLmNoPqRsTuVwX"
   const val TELEGRAM_CHAT_ID   = "-1001234567890"   // or "123456789" for personal chat
