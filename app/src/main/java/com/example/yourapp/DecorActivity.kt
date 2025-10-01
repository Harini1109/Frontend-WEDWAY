package com.example.yourapp  // Make sure this matches your app's package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DecorActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_decor)

        // Initialize views
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Set navigation click listeners
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
            startActivity(Intent(this, ConfirmVenueActivity::class.java))
        }

        // Handle system window insets for edge-to-edge
        val mainView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // GST percentage
        val gstRate = 0.18

        // Find buttons
        val btnClassic = findViewById<Button>(R.id.btnClassicDecor)
        val btnModern = findViewById<Button>(R.id.btnModernDecor)
        val btnLuxury = findViewById<Button>(R.id.btnLuxuryDecor)

        // Button click listeners
        btnClassic.setOnClickListener {
            navigateToBill("Classic Package", 25000.0, gstRate)
        }

        btnModern.setOnClickListener {
            navigateToBill("Modern Package", 40000.0, gstRate)
        }

        btnLuxury.setOnClickListener {
            navigateToBill("Luxury Package", 70000.0, gstRate)
        }
    }

    private fun navigateToBill(packageName: String, basePrice: Double, gstRate: Double) {
        val gstAmount = basePrice * gstRate
        val totalAmount = basePrice + gstAmount

        val intent = Intent(this, DecorBookingActivity::class.java).apply {
            putExtra("PACKAGE_NAME", packageName)
            putExtra("BASE_PRICE", basePrice)
            putExtra("GST_AMOUNT", gstAmount)
            putExtra("TOTAL_AMOUNT", totalAmount)
        }
        startActivity(intent)
    }
}
