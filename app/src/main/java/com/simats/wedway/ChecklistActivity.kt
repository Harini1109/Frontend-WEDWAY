package com.simats.wedway

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build

class ChecklistActivity : AppCompatActivity() {

    private lateinit var checklistContainer: LinearLayout
    private lateinit var btnUpdate: Button
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    private val checklistItems = listOf(
        "Book venue 🏰",
        "Finalize catering 🍽️",
        "Choose decoration 🎀",
        "Arrange music/entertainment 🎶",
        "Send invitations 💌",
        "Select wedding attire 👗💍",
        "Hire photographer/videographer 📸🎥",
        "Arrange transportation 🚌",
        "Confirm vendors & payments 💳",
        "Prepare wedding day schedule 🕒",
        "Buy rings 💍",
        "Arrange accommodation for guests 🏨",
        "Plan rehearsal dinner 🍽️",
        "Prepare emergency kit 🎁"
    )

    private val checkboxes = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Set navigation click listeners
        navHome.setOnClickListener {
            val intent = Intent(this, HomeUserActivity::class.java)
            startActivity(intent)
        }

        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        navSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        backbtn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }



        checklistContainer = findViewById(R.id.checklistContainer)
        btnUpdate = findViewById(R.id.btnUpdate)

        // Add checklist items dynamically
        checklistItems.forEach { item ->
            val cb = CheckBox(this)
            cb.text = item
            cb.textSize = 16f
            checklistContainer.addView(cb)
            checkboxes.add(cb)
        }

        btnUpdate.setOnClickListener {
            savePendingTasks()
            sendChecklistNotification()
            Toast.makeText(this, "Checklist updated!", Toast.LENGTH_SHORT).show()
        }

        createNotificationChannel()
    }

    private fun savePendingTasks() {
        val pendingItems = checkboxes.filter { !it.isChecked }.map { it.text.toString() }
        val sharedPref = getSharedPreferences("WeddingAppPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().putStringSet("PendingTasks", pendingItems.toSet()).apply()
    }

    private fun sendChecklistNotification() {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", Context.MODE_PRIVATE)
        val pendingItems = sharedPref.getStringSet("PendingTasks", emptySet()) ?: emptySet()
        val message = if (pendingItems.isEmpty()) {
            "🎉 All tasks completed! Your wedding checklist is done."
        } else {
            "⚠️ Pending tasks: ${pendingItems.joinToString(", ")}"
        }

        val builder = NotificationCompat.Builder(this, "checklistChannel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Wedding Checklist Reminder")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(this).notify(101, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Checklist Reminder"
            val descriptionText = "Notifications for pending checklist items"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("checklistChannel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
