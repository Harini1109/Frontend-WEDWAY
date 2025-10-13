package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmgiftActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmgift)

        // Bottom navigation
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)

        navHome.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }

        // Display order details
        val tvOrderDetails: TextView = findViewById(R.id.tvOrderDetails)

        val giftName = intent.getStringExtra("giftName") ?: "Unknown Gift"
        val quantity = intent.getIntExtra("quantity", 1)
        val price = intent.getStringExtra("price") ?: "₹0"
        val deliveryDate = intent.getStringExtra("deliveryDate") ?: "Not specified"

        tvOrderDetails.text = "Gift: $giftName\nQuantity: $quantity\nPrice: $price\nDelivery: $deliveryDate"

        // Save order to SharedPreferences for Home page display
        saveOrderToHome(giftName, quantity, price, deliveryDate)
    }

    private fun saveOrderToHome(giftName: String, quantity: Int, price: String, deliveryDate: String) {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val orders = sharedPref.getStringSet("ConfirmedGiftOrders", mutableSetOf())?.toMutableSet()
            ?: mutableSetOf()
        orders.add("Gift: $giftName, Qty: $quantity, Price: $price, Delivery: $deliveryDate")
        sharedPref.edit().putStringSet("ConfirmedGiftOrders", orders).apply()
    }
}
