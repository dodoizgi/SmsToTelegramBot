package com.example.smstotelegrambot

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters

class StartServiceWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val intent = Intent(applicationContext, SmsForegroundService::class.java)

        applicationContext.startForegroundService(intent)

        return Result.success()
    }
}
