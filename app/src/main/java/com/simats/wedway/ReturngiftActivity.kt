package com.simats.wedway

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class ReturngiftActivity : AppCompatActivity() {

    private lateinit var spinnerGift: Spinner
    private lateinit var etQuantity: EditText
    private lateinit var btnSelectDate: Button
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnPlaceOrder: Button
    private lateinit var backbtn: ImageView

    // Bottom navigation
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView

    // Price per gift
    private val giftPrice = mapOf(
        "Keychain" to 50,
        "Candle Set" to 120,
        "Chocolate Box" to 200,
        "Mini Plant" to 150,
        "Photo Frame" to 180,
        "Handmade Soap" to 100,
        "Notebook" to 80
    )

    private var selectedDate: String = ""
    private var calculatedPrice: Int = 0  // ✅ Store price after calculation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_returngift)

        // Initialize views
        spinnerGift = findViewById(R.id.spinnerGift)
        etQuantity = findViewById(R.id.etQuantity)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
        backbtn = findViewById(R.id.ivBack)

        // Bottom navigation
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)

        navHome.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }

        // Back button
        backbtn.setOnClickListener { finish() }

        // Populate spinner from string-array
        ArrayAdapter.createFromResource(
            this,
            R.array.return_gift_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGift.adapter = adapter
        }

        // Select date → calculate price
        btnSelectDate.setOnClickListener { showDatePicker() }

        // Place order → go to confirmation
        btnPlaceOrder.setOnClickListener {
            if (calculatedPrice == 0) {
                Toast.makeText(this, "Please select date and quantity to calculate price first", Toast.LENGTH_SHORT).show()
            } else {
                val gift = spinnerGift.selectedItem.toString()
                val quantity = etQuantity.text.toString().toInt()
                val intent = Intent(this, ConfirmgiftActivity::class.java).apply {
                    putExtra("giftName", gift)
                    putExtra("quantity", quantity)
                    putExtra("price", "₹$calculatedPrice")
                    putExtra("deliveryDate", selectedDate)
                }
                startActivity(intent)
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                calendar.set(year, month, dayOfMonth)
                selectedDate = sdf.format(calendar.time)
                btnSelectDate.text = "Delivery Date: $selectedDate"

                // Calculate price after selecting date
                calculateTotalPrice()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = calendar.timeInMillis
        datePicker.show()
    }

    private fun calculateTotalPrice() {
        val gift = spinnerGift.selectedItem.toString()
        val quantityStr = etQuantity.text.toString()
        if (quantityStr.isEmpty()) {
            tvTotalPrice.text = ""
            calculatedPrice = 0
            return
        }
        val quantity = quantityStr.toIntOrNull()
        if (quantity == null || quantity <= 0) {
            Toast.makeText(this, "Enter a valid quantity", Toast.LENGTH_SHORT).show()
            tvTotalPrice.text = ""
            calculatedPrice = 0
            return
        }

        val pricePerGift = giftPrice[gift] ?: 0
        calculatedPrice = pricePerGift * quantity
        tvTotalPrice.text = "Total Price: ₹$calculatedPrice"
    }
}
