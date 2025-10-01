package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmDecorActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_decor)

        // Bottom navigation
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        navHome.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        backbtn.setOnClickListener { startActivity(Intent(this, DecorBookingActivity::class.java)) }

        // TextViews
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvUserPhone = findViewById<TextView>(R.id.tvUserPhone)
        val tvPackageName = findViewById<TextView>(R.id.tvPackageName)
        val tvBasePrice = findViewById<TextView>(R.id.tvBasePrice)
        val tvGST = findViewById<TextView>(R.id.tvGST)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvThankYou = findViewById<TextView>(R.id.tvThankYou)

        // Button: Choose Music
        val btnChooseMusic = findViewById<Button>(R.id.btnMusic)

        // Get booking data from Intent
        val userName = intent.getStringExtra("USER_NAME") ?: "N/A"
        val userPhone = intent.getStringExtra("USER_PHONE") ?: "N/A"
        val packageName = intent.getStringExtra("PACKAGE_NAME") ?: "N/A"
        val basePrice = intent.getDoubleExtra("BASE_PRICE", 0.0)
        val gstAmount = intent.getDoubleExtra("GST_AMOUNT", 0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)

        // Populate views
        tvUserName.text = "Name: $userName"
        tvUserPhone.text = "Phone: $userPhone"
        tvPackageName.text = "Package: $packageName"
        tvBasePrice.text = "Base Price: ₹${"%.2f".format(basePrice)}"
        tvGST.text = "GST (18%): ₹${"%.2f".format(gstAmount)}"
        tvTotal.text = "Total Amount: ₹${"%.2f".format(totalAmount)}"
        tvThankYou.text = "Thank you for booking with us!"

        // ✅ Save Decor booking as confirmed
        saveConfirmedBooking(packageName, totalAmount)

        // Navigate to Music Selection Activity
        btnChooseMusic.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java) // replace MusicActivity with your actual activity
            startActivity(intent)
        }
    }

    private fun saveConfirmedBooking(packageName: String, totalAmount: Double) {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val bookings = sharedPref.getStringSet("ConfirmedBookings", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        bookings.add("Decor Confirmed: $packageName, Total: ₹${"%.2f".format(totalAmount)}")
        sharedPref.edit().putStringSet("ConfirmedBookings", bookings).apply()
    }
}
