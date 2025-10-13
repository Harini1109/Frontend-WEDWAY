package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class LiveMusicActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    private val gstRate = 0.18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_music_selection)

        // Debug log to check activity launch
        Log.d("LiveMusicActivity", "Activity started")

        // Bottom navigation setup
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

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
            startActivity(Intent(this, MusicActivity::class.java))
        }

        // Music package buttons
        val btnSmallBand = findViewById<Button>(R.id.btnSmallBand)
        val btnFullBand = findViewById<Button>(R.id.btnFullBand)

        btnSmallBand.setOnClickListener {
            navigateToBooking("Small Band", 15000.0)
        }

        btnFullBand.setOnClickListener {
            navigateToBooking("Full Band", 35000.0)
        }
    }

    // Navigate to BookingActivity first to get user name
    private fun navigateToBooking(packageName: String, basePrice: Double) {
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
