package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DjselectionActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_djselection)

        // Initialize views
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn = findViewById(R.id.ivBack)   // ✅ fixed initialization

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

        // Back button click
        backbtn.setOnClickListener {
            startActivity(Intent(this, MusicActivity::class.java))
            // Or if you just want to go back:
            // finish()
        }

        // Find buttons
        val btnStandardDJ = findViewById<Button>(R.id.btnStandardDJ)
        val btnPremiumDJ = findViewById<Button>(R.id.btnPremiumDJ)

        // GST percentage
        val gstRate = 0.18

        // Standard DJ click listener
        btnStandardDJ.setOnClickListener {
            val packageName = "Standard DJ"
            val basePrice = 10000.0
            navigateToBooking(packageName, basePrice, gstRate)
        }

        // Premium DJ click listener
        btnPremiumDJ.setOnClickListener {
            val packageName = "Premium DJ"
            val basePrice = 25000.0
            navigateToBooking(packageName, basePrice, gstRate)
        }
    }

    // Navigate to BookingActivity
    private fun navigateToBooking(packageName: String, basePrice: Double, gstRate: Double) {
        val gstAmount = basePrice * gstRate
        val totalAmount = basePrice + gstAmount

        val intent = Intent(this, MusicBookingActivity::class.java)
        intent.putExtra("PACKAGE_NAME", packageName)
        intent.putExtra("BASE_PRICE", basePrice)
        intent.putExtra("GST_AMOUNT", gstAmount)
        intent.putExtra("TOTAL_AMOUNT", totalAmount)
        startActivity(intent)
    }
}
