package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    private lateinit var btntask:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)
        // Bottom navigation clicks
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeUserActivity::class.java))
        }
        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        navSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        backbtn.setOnClickListener {
            startActivity(Intent(this, HomeUserActivity::class.java))
        }

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Buttons
        val btnChecklist: Button = findViewById(R.id.btnChecklist)
        val btnBudget: Button = findViewById(R.id.btnBudget)
        val btnChatbot: Button = findViewById(R.id.btnChatbot)
        val btnProfile: Button = findViewById(R.id.btnProfile)
        val btnAddGuest: Button = findViewById(R.id.btnAddGuest)
        val btnNotes: Button = findViewById(R.id.btnNotes)
        val btnVendors: Button = findViewById(R.id.btnVendors)  // NEW
        val btnLogout: Button = findViewById(R.id.btnLogout)
        btntask=findViewById(R.id.btnTaskScheduler)

        // Navigate to activities
        btnChecklist.setOnClickListener { startActivity(Intent(this, ChecklistActivity::class.java)) }
        btnBudget.setOnClickListener { startActivity(Intent(this, BudgetActivity::class.java)) }
        btnChatbot.setOnClickListener { startActivity(Intent(this, ChatbotActivity::class.java)) }
        btnProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        btnAddGuest.setOnClickListener { startActivity(Intent(this, GuestActivity::class.java)) }
        btnNotes.setOnClickListener { startActivity(Intent(this, NoteActivity::class.java)) }
        btnVendors.setOnClickListener { startActivity(Intent(this, VendorsActivity::class.java)) }
        btntask.setOnClickListener {
            startActivity(Intent(this, TaskSchedulerActivity::class.java))
        }// NEW

        // Logout with confirmation popup
        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to log out?")
            builder.setPositiveButton("Yes") { _, _ ->
                // Clear saved wedding/user details
                val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
                sharedPref.edit().clear().apply()
                // Navigate back to login screen
                val intent = Intent(this, UserLoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
    }
}
