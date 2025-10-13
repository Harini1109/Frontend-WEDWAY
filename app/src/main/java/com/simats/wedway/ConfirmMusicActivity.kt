package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmMusicActivity : AppCompatActivity() {

    private lateinit var tvPackageName: TextView
    private lateinit var tvBasePrice: TextView
    private lateinit var tvGST: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvThankYou: TextView
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var btnGenerateInvitation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_music)

        // Find views
        tvPackageName = findViewById(R.id.tvPackageName)
        tvBasePrice = findViewById(R.id.tvBasePrice)
        tvGST = findViewById(R.id.tvGST)
        tvTotal = findViewById(R.id.tvTotal)
        tvThankYou = findViewById(R.id.tvThankYou)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        btnGenerateInvitation = findViewById(R.id.btnGenerateInvitation)

        // Bottom navigation click listeners
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeUserActivity::class.java))
        }
        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        navSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Get data from previous activity (MusicBookingActivity)
        val packageName = intent.getStringExtra("PACKAGE_NAME") ?: "N/A"
        val basePrice = intent.getDoubleExtra("BASE_PRICE", 0.0)
        val gstAmount = intent.getDoubleExtra("GST_AMOUNT", 0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val userName = intent.getStringExtra("USER_NAME") ?: ""

        // Display the details
        tvPackageName.text = "Package: $packageName"
        tvBasePrice.text = "Base Price: ₹${String.format("%.2f", basePrice)}"
        tvGST.text = "GST (18%): ₹${String.format("%.2f", gstAmount)}"
        tvTotal.text = "Total Amount: ₹${String.format("%.2f", totalAmount)}"

        // Personalize thank you message
        tvThankYou.text = if (userName.isNotEmpty()) {
            "Thank you, $userName, for booking with us!"
        } else {
            "Thank you for booking with us!"
        }

        // Save booking automatically
        saveConfirmedBooking(packageName, totalAmount)

        // Generate Invitation button navigation
        btnGenerateInvitation.setOnClickListener {
            val intent = Intent(this, InvitationGenerationActivity::class.java)
            intent.putExtra("PACKAGE_NAME", packageName)
            intent.putExtra("TOTAL_AMOUNT", totalAmount)
            intent.putExtra("USER_NAME", userName)
            startActivity(intent)
        }
    }

    // Function to save confirmed booking in SharedPreferences
    private fun saveConfirmedBooking(hallName: String, totalAmount: Double) {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val bookings = sharedPref.getStringSet("ConfirmedBookings", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        bookings.add("Music Confirmed: $hallName, Total: ₹${"%.2f".format(totalAmount)}")
        sharedPref.edit().putStringSet("ConfirmedBookings", bookings).apply()
    }
}
