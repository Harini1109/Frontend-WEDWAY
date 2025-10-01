package com.example.yourapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CakeorderActivity : AppCompatActivity() {

    private lateinit var spinnerFlavor: Spinner
    private lateinit var spinnerSize: Spinner
    private lateinit var etLayers: EditText
    private lateinit var etMessage: EditText
    private lateinit var etQuantity: EditText
    private lateinit var btnPlaceOrder: Button
    private lateinit var tvPrice: TextView
    private lateinit var backbtn: ImageView
    private lateinit var homebtn: ImageView
    private lateinit var setbtn: ImageView
    private lateinit var probtn: ImageView
    private lateinit var btnSelectDate: Button

    private var selectedDate: String = ""
    private var totalPrice: Int = 0   // ✅ Store price after calculation

    private val flavorPrice = mapOf(
        "Chocolate" to 500,
        "Vanilla" to 450,
        "Red Velvet" to 600,
        "Strawberry" to 550,
        "Butterscotch" to 500,
        "Chocotruffle" to 650,
        "Blackcurrent" to 600
    )
    private val sizeMultiplier = mapOf(
        "Small" to 1.0,
        "Medium" to 1.5,
        "Large" to 2.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cakeorder)

        // Initialize views
        homebtn = findViewById(R.id.navHome)
        probtn = findViewById(R.id.navProfile)
        setbtn = findViewById(R.id.navSettings)
        backbtn = findViewById(R.id.ivBack)
        spinnerFlavor = findViewById(R.id.spinnerFlavor)
        spinnerSize = findViewById(R.id.spinnerSize)
        etLayers = findViewById(R.id.etLayers)
        etMessage = findViewById(R.id.etMessage)
        etQuantity = findViewById(R.id.etQuantity)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
        tvPrice = findViewById(R.id.tvPrice)
        btnSelectDate = findViewById(R.id.btnSelectDate)

        // Initialize spinners
        ArrayAdapter.createFromResource(
            this,
            R.array.cake_flavor_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFlavor.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.cake_size_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSize.adapter = adapter
        }

        // Navigation buttons
        backbtn.setOnClickListener { finish() }
        homebtn.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        probtn.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        setbtn.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }

        // Select Date → also calculate price
        btnSelectDate.setOnClickListener {
            showDatePicker()
        }

        // Place Order → go to Confirmation Page
        btnPlaceOrder.setOnClickListener {
            if (totalPrice == 0 || selectedDate.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please select date and enter details to calculate price first",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val flavor = spinnerFlavor.selectedItem.toString()
                val size = spinnerSize.selectedItem.toString()
                val layers = etLayers.text.toString().toInt()
                val quantity = etQuantity.text.toString().toInt()
                val message = etMessage.text.toString()

                val intent = Intent(this, ConfirmcakeActivity::class.java).apply {
                    putExtra("flavor", flavor)
                    putExtra("size", size)
                    putExtra("layers", layers)
                    putExtra("quantity", quantity)
                    putExtra("message", message)
                    putExtra("date", selectedDate)
                    putExtra("price", totalPrice)
                }
                startActivity(intent)
            }
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                btnSelectDate.text = "Delivery Date: $selectedDate"
                // ✅ Calculate price automatically after selecting date
                calculatePrice()
            },
            year, month, day
        )
        dpd.datePicker.minDate = c.timeInMillis
        dpd.show()
    }

    private fun calculatePrice() {
        val flavor = spinnerFlavor.selectedItem.toString()
        val size = spinnerSize.selectedItem.toString()
        val layersStr = etLayers.text.toString()
        val quantityStr = etQuantity.text.toString()

        if (layersStr.isEmpty() || quantityStr.isEmpty() || selectedDate.isEmpty()) {
            tvPrice.text = ""
            return
        }

        val layers = layersStr.toInt()
        val quantity = quantityStr.toInt()
        val basePrice = flavorPrice[flavor] ?: 0
        val multiplier = sizeMultiplier[size] ?: 1.0
        totalPrice = (basePrice * multiplier * layers * quantity).toInt()
        tvPrice.text = "Total Price: ₹$totalPrice"
    }
}
