package com.example.smstotelegrambot

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.net.HttpURLConnection
import java.net.URL

class SmsForegroundService : Service() {

    companion object {
        private const val TAG = "SmsForegroundService"

        fun sendToTelegram(context: Context, sender: String?, message: String) {

            val botToken = "your-bot-token"
            val chatId = "your-chat-id"

            val text = """
            📩 Yeni SMS
            👤 Gönderen: $sender
            💬 Mesaj: $message
            """.trimIndent()

            Thread {
                try {
                    val url = URL("https://api.telegram.org/bot$botToken/sendMessage")
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/json")
                    conn.doOutput = true

                    val json = """
            {
              "chat_id": "$chatId",
              "text": "$text"
            }
            """.trimIndent()

                    conn.outputStream.use {
                        it.write(json.toByteArray())
                    }

                    conn.inputStream.close()
                } catch (e: Exception) {
                    Log.e("Telegram", "Gönderim hatası", e)
                }
            }.start()
        }

    }

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: SmsForegroundService oluşturuluyor...")
        Log.d(TAG, "onCreate: Android sürümü: ${Build.VERSION.SDK_INT}")

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                Log.d(
                    TAG,
                    "onCreate: Android 14+ için foreground service başlatılıyor (DATA_SYNC type)"
                )
                startForeground(
                    1,
                    createNotification()
                )
            } else {
                Log.d(TAG, "onCreate: Foreground service başlatılıyor")
                startForeground(1, createNotification())
            }
            Log.i(TAG, "onCreate: SmsForegroundService başarıyla ba��latıldı")
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: Foreground service başlatılamadı", e)
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: Çağrıldı (flags=$flags, startId=$startId)")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: Çağrıldı (null döndürülüyor)")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "onDestroy: SmsForegroundService sonlandırılıyor")
    }

    private fun createNotification(): Notification {
        val channelId = "sms_service_channel"
        Log.d(TAG, "createNotification: Notification oluşturuluyor (channelId=$channelId)")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "createNotification: Android O+ için notification channel oluşturuluyor")
            val channel = NotificationChannel(
                channelId,
                "SMS Listener",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
            Log.d(TAG, "createNotification: Notification channel oluşturuldu")
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("SMS Bot Aktif")
            .setContentText("Gelen SMS'ler Telegram'a iletiliyor")
            .setSmallIcon(android.R.drawable.sym_action_chat)
            .build()

        Log.d(TAG, "createNotification: Notification başarıyla oluşturuldu")
        return notification
    }
}

