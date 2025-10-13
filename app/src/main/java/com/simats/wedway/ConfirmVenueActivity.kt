package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmVenueActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_venue)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Bottom navigation click listeners
        navHome.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        backbtn.setOnClickListener { startActivity(Intent(this, VenueBookingActivity::class.java)) }

        val tvSummary = findViewById<TextView>(R.id.tvSummary)
        val btnDecor = findViewById<Button>(R.id.btnDecor)

        // Get booking data from Intent
        val userName = intent.getStringExtra("userName") ?: "N/A"
        val hallName = intent.getStringExtra("hallName") ?: "N/A"
        val guestCount = intent.getIntExtra("guestCount", 0)
        val totalAmount = intent.getDoubleExtra("totalAmount", 0.0)

        // Populate summary
        val summaryText = """
            Venue Booking Confirmed!
            Name: $userName
            Hall: $hallName
            Number of Guests: $guestCount
            Total Bill (with GST): ₹${"%.2f".format(totalAmount)}
        """.trimIndent()
        tvSummary.text = summaryText

        // ✅ Save Venue booking as confirmed
        saveConfirmedBooking(hallName, totalAmount)

        // Navigate to DecorActivity
        btnDecor.setOnClickListener {
            val intent = Intent(this, DecorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveConfirmedBooking(hallName: String, totalAmount: Double) {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val bookings = sharedPref.getStringSet("ConfirmedBookings", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        bookings.add("Venue Confirmed: $hallName, Total: ₹${"%.2f".format(totalAmount)}")
        sharedPref.edit().putStringSet("ConfirmedBookings", bookings).apply()
    }
}
