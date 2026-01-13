package com.example.smstotelegrambot

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val permissions = mutableListOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (hasAllPermissions()) {
            try {
                startServiceAndExit()
                Log.d(TAG, "onCreate: Foreground service başlatıldı")
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: Foreground service başlatılamadı", e)
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                100
            )
        }
    }

    private fun hasAllPermissions(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startServiceAndExit() {
        val intent = Intent(this, SmsForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(
                TAG,
                "startForegroundService: Android O+ için startForegroundService kullanılıyor"
            )
            startForegroundService(intent)
        } else {
            Log.d(TAG, "startForegroundService: Eski Android için startService kullanılıyor")
            startService(intent)
        }
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            startServiceAndExit()
        } else {
            Toast.makeText(
                this,
                "Çalışması için izinler gerekli",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}



