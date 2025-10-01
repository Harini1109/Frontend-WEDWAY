package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeUserActivity : AppCompatActivity() {

    private lateinit var welcomeTitle: TextView
    private lateinit var brideNameTv: TextView
    private lateinit var groomNameTv: TextView
    private lateinit var weddingDateTv: TextView
    private lateinit var weddingTimeTv: TextView
    private lateinit var daysLeftTv: TextView
    private lateinit var pendingTasksContainer: LinearLayout
    private lateinit var bookingsContainer: LinearLayout
    private var countdownTimer: CountDownTimer? = null
    private lateinit var nextButton: Button

    // Bottom navigation
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView

    // Budget Tracker
    private lateinit var budgetProgress: ProgressBar
    private lateinit var budgetPercent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)

        // Initialize views
        welcomeTitle = findViewById(R.id.welcomeTitle)
        brideNameTv = findViewById(R.id.brideName)
        groomNameTv = findViewById(R.id.groomName)
        weddingDateTv = findViewById(R.id.weddingDate)
        weddingTimeTv = findViewById(R.id.weddingTime)
        daysLeftTv = findViewById(R.id.daysLeft)
        pendingTasksContainer = findViewById(R.id.pendingTasksContainer)
        bookingsContainer = findViewById(R.id.bookingsContainer)
        nextButton = findViewById(R.id.vendorButton)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)

        // Budget tracker views
        budgetProgress = findViewById(R.id.budgetProgress)
        budgetPercent = findViewById(R.id.budgetPercent)

        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val brideName = sharedPref.getString("brideName", "") ?: ""
        val groomName = sharedPref.getString("groomName", "") ?: ""
        val weddingDate = sharedPref.getString("weddingDate", "") ?: ""
        val weddingTime = sharedPref.getString("weddingTime", "") ?: ""

        // Set user details
        welcomeTitle.text = "Welcome, $brideName"
        brideNameTv.text = "Bride’s name: $brideName"
        groomNameTv.text = "Groom’s name: $groomName"
        weddingDateTv.text = "Wedding date: $weddingDate"
        weddingTimeTv.text = "Wedding time: $weddingTime"

        // Start features
        startCountdown(weddingDate, weddingTime)
        displayPendingTasks()
        displayConfirmedBookings()
        updateBudgetTracker()

        // Navigation
        nextButton.setOnClickListener {
            startActivity(Intent(this, WeddingtypeActivity::class.java))
        }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        // Refresh budget tracker when activity resumes
        updateBudgetTracker()
    }

    private fun startCountdown(weddingDate: String, weddingTime: String) {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val weddingDateTime = sdf.parse("$weddingDate $weddingTime") ?: return
            val currentTime = Calendar.getInstance().time
            val diff = weddingDateTime.time - currentTime.time
            if (diff <= 0) {
                daysLeftTv.text = "Wedding day is here! 🎉"
                return
            }

            countdownTimer?.cancel()
            countdownTimer = object : CountDownTimer(diff, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000 % 60
                    val minutes = millisUntilFinished / (1000 * 60) % 60
                    val hours = millisUntilFinished / (1000 * 60 * 60) % 24
                    val days = millisUntilFinished / (1000 * 60 * 60 * 24)
                    daysLeftTv.text = "${days}d ${hours}h ${minutes}m ${seconds}s left"
                }

                override fun onFinish() {
                    daysLeftTv.text = "Wedding day is here! 🎉"
                }
            }.start()
        } catch (e: Exception) {
            daysLeftTv.text = "Invalid date/time"
        }
    }

    private fun displayPendingTasks() {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val pendingTasks = sharedPref.getStringSet("PendingTasks", emptySet()) ?: emptySet()
        pendingTasksContainer.removeAllViews()

        if (pendingTasks.isEmpty()) {
            val tv = TextView(this)
            tv.text = "No tasks completed yet"
            tv.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            tv.textSize = 16f
            pendingTasksContainer.addView(tv)
        } else {
            pendingTasks.forEach { task ->
                val tv = TextView(this)
                tv.text = "• $task"
                tv.textSize = 16f
                tv.setTextColor(resources.getColor(android.R.color.black))
                tv.setPadding(0, 4, 0, 4)
                pendingTasksContainer.addView(tv)
            }
        }
    }

    private fun displayConfirmedBookings() {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        bookingsContainer.removeAllViews()
        val bookings = sharedPref.getStringSet("ConfirmedBookings", emptySet()) ?: emptySet()
        if (bookings.isEmpty()) {
            val tv = TextView(this)
            tv.text = "No bookings confirmed yet."
            tv.textSize = 16f
            tv.setTextColor(resources.getColor(android.R.color.black))
            bookingsContainer.addView(tv)
        } else {
            bookings.forEach { booking ->
                val tv = TextView(this)
                tv.text = "• $booking"
                tv.textSize = 16f
                tv.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                tv.setPadding(0, 4, 0, 4)
                bookingsContainer.addView(tv)
            }
        }
    }

    private fun updateBudgetTracker() {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val total = sharedPref.getFloat("TotalBudget", 0f).toDouble()
        val venue = sharedPref.getFloat("VenueBudget", 0f).toDouble()
        val catering = sharedPref.getFloat("CateringBudget", 0f).toDouble()
        val decoration = sharedPref.getFloat("DecorationBudget", 0f).toDouble()
        val other = sharedPref.getFloat("OtherBudget", 0f).toDouble()

        if (total > 0) {
            val used = venue + catering + decoration + other
            val percentUsed = ((used / total) * 100).toInt().coerceIn(0, 100)
            budgetProgress.progress = percentUsed
            budgetPercent.text = "$percentUsed%"

            val drawable = budgetProgress.progressDrawable.mutate()
            val color = when {
                percentUsed <= 60 -> android.graphics.Color.parseColor("#4CAF50")
                percentUsed <= 90 -> android.graphics.Color.parseColor("#FFC107")
                else -> android.graphics.Color.parseColor("#F44336")
            }
            drawable.setTint(color)
            budgetProgress.progressDrawable = drawable
        } else {
            budgetProgress.progress = 0
            budgetPercent.text = "0%"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }
}
