package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmcakeActivity : AppCompatActivity() {

    private lateinit var homebtn: ImageView
    private lateinit var setbtn: ImageView
    private lateinit var probtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmcake)

        // Bottom navigation buttons
        homebtn = findViewById(R.id.navHome)
        probtn = findViewById(R.id.navProfile)
        setbtn = findViewById(R.id.navSettings)

        homebtn.setOnClickListener {
            startActivity(Intent(this, HomeUserActivity::class.java))
        }
        probtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        setbtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Fetch data from intent
        val flavor = intent.getStringExtra("flavor") ?: "N/A"
        val size = intent.getStringExtra("size") ?: "N/A"
        val layers = intent.getIntExtra("layers", 1)
        val quantity = intent.getIntExtra("quantity", 1)
        val message = intent.getStringExtra("message") ?: "No message"
        val date = intent.getStringExtra("date") ?: "N/A"
        val price = intent.getIntExtra("price", 0)

        // Display order details
        val tvOrderDetails: TextView = findViewById(R.id.tvOrderDetails)
        tvOrderDetails.text = """
            Cake: $flavor ($size)
            Layers: $layers
            Quantity: $quantity
            Message: $message
            Delivery Date: $date
            Total Price: ₹$price
        """.trimIndent()

        // Save order to SharedPreferences to show in Home
        saveOrderToHome(flavor, size, layers, quantity, message, date, price)
    }

    private fun saveOrderToHome(
        flavor: String,
        size: String,
        layers: Int,
        quantity: Int,
        message: String,
        date: String,
        price: Int
    ) {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val orders = sharedPref.getStringSet("ConfirmedCakeOrders", mutableSetOf())?.toMutableSet()
            ?: mutableSetOf()
        orders.add("Cake: $flavor ($size), Layers: $layers, Qty: $quantity, Message: $message, Delivery: $date, Price: ₹$price")
        sharedPref.edit().putStringSet("ConfirmedCakeOrders", orders).apply()
    }
}
