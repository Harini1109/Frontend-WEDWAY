package com.simats.wedway

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TaskNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("task_title") ?: "Task Reminder"
        val date = intent.getStringExtra("task_date") ?: ""

        // Check POST_NOTIFICATIONS permission safely
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, do not post notification
            return
        }

        // Build notification
        val builder = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(R.drawable.ic_notification) // Ensure this icon exists
            .setContentTitle("Task Today")
            .setContentText("$title is scheduled for today: $date")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Post notification using NotificationManagerCompat
        NotificationManagerCompat.from(context).notify(
            System.currentTimeMillis().toInt(), // unique ID
            builder.build()
        )
    }
}
