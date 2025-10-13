package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CateringConfirmActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn: ImageView
    private lateinit var cakebtn: Button   // <-- Button, not ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catering_confirm)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn = findViewById(R.id.ivBack)
        cakebtn = findViewById(R.id.btnCakeOrders)

        // ✅ Bottom navigation click listeners
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeUserActivity::class.java))
        }
        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        navSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // ✅ Back button (finish instead of going to random activity)
        backbtn.setOnClickListener { finish() }

        // ✅ Go to Cake Orders Activity (NOT this same activity again)
        cakebtn.setOnClickListener {
            startActivity(Intent(this, CakeorderActivity::class.java))
        }

        // Set activity title
        supportActionBar?.title = "Booking Confirmation"

        val confirmationText = findViewById<TextView>(R.id.confirmationText)

        // Get booking details from Intent
        val packageName = intent.getStringExtra("packageName") ?: "N/A"
        val pricePerPlate = intent.getIntExtra("pricePerPlate", 0)
        val guestCount = intent.getIntExtra("guestCount", 0)
        val totalBill = intent.getIntExtra("totalBill", 0)
        val dishes = intent.getStringArrayListExtra("dishes") ?: arrayListOf()

        val subtotal = guestCount * pricePerPlate
        val gstAmount = totalBill - subtotal

        // Build confirmation message
        val confirmationMessage = """
            Booking Confirmation
            -----------------------
            Package Name: $packageName
            Guest Count: $guestCount
            Price per Plate: ₹$pricePerPlate
            Subtotal: ₹$subtotal
            GST (18%): ₹$gstAmount
            Total Bill: ₹$totalBill
            Dishes Included:
            • ${dishes.joinToString("\n• ")}
        """.trimIndent()

        confirmationText.text = confirmationMessage

        // ✅ Save confirmed booking to SharedPreferences
        saveConfirmedBooking(packageName, guestCount)
    }

    private fun saveConfirmedBooking(packageName: String, guestCount: Int) {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val bookings = sharedPref.getStringSet("ConfirmedBookings", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        bookings.add("Catering Confirmed: $packageName, Guests: $guestCount")
        sharedPref.edit().putStringSet("ConfirmedBookings", bookings).apply()
    }
}
