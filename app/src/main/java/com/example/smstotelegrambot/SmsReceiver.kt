package com.example.smstotelegrambot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

private const val TARGET_NUMBER = "target-number"

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "SmsReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: BroadcastReceiver tetiklendi")
        Log.d(TAG, "onReceive: Intent action: ${intent.action}")

        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Log.w(TAG, "onReceive: SMS_RECEIVED_ACTION değil, çıkılıyor")
            return
        }

        Log.d(TAG, "onReceive: SMS mesajları alınıyor...")
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        Log.d(TAG, "onReceive: ${messages.size} adet SMS mesajı alındı")

        for ((index, sms) in messages.withIndex()) {
            val sender = sms.originatingAddress
            val message = sms.messageBody

            Log.d(TAG, "onReceive: SMS #$index - Gönderici: $sender")
            Log.d(TAG, "onReceive: SMS #$index - Mesaj: $message")

            if (sender == null) {
                Log.w(TAG, "onReceive: SMS #$index - Gönderici null, atlanıyor")
                continue
            }

            if (message == null) {
                Log.w(TAG, "onReceive: SMS #$index - Mesaj null, atlanıyor")
                continue
            }

            if (sender == TARGET_NUMBER) {
                Log.i(TAG, "onReceive: SMS #$index - Hedef numaradan geldi ($TARGET_NUMBER)")
                Log.d(TAG, "onReceive: SMS #$index - Telegram'a gönderiliyor...")
                try {
                    SmsForegroundService.sendToTelegram(context, sender, message)
                    Log.i(TAG, "onReceive: SMS #$index - Telegram'a başarıyla gönderildi")
                } catch (e: Exception) {
                    Log.e(TAG, "onReceive: SMS #$index - Telegram'a gönderilemedi", e)
                }
            } else {
                Log.d(
                    TAG,
                    "onReceive: SMS #$index - Hedef numaradan değil ($sender != $TARGET_NUMBER), atlanıyor"
                )
            }
        }

        Log.d(TAG, "onReceive: Tüm SMS'ler işlendi")
    }

}



