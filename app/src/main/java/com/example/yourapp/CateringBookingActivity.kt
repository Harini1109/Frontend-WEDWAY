package com.example.yourapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CateringBookingActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catering_booking)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)

        // Navigation clicks
        navHome.setOnClickListener {
            val intent = Intent(this, HomeUserActivity::class.java)
            startActivity(intent)
        }
        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        navSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Set activity title
        supportActionBar?.title = "Catering Booking"

        val packageNameText = findViewById<TextView>(R.id.packageNameText)
        val pricePerPlateText = findViewById<TextView>(R.id.pricePerPlateText)
        val dishesText = findViewById<TextView>(R.id.dishesText)
        val guestCountInput = findViewById<EditText>(R.id.guestCountInput)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val totalBillText = findViewById<TextView>(R.id.totalBillText)
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val datePickerButton = findViewById<Button>(R.id.datePickerButton)
        val selectedDateText = findViewById<TextView>(R.id.selectedDateText)

        // Get package info from Intent
        val packageName = intent.getStringExtra("packageName") ?: "Catering Package"
        val pricePerPlate = intent.getIntExtra("pricePerPlate", 250)
        val dishes = intent.getStringArrayListExtra("dishes") ?: arrayListOf()

        packageNameText.text = packageName
        pricePerPlateText.text = "Price per plate: ₹$pricePerPlate"
        dishesText.text = dishes.joinToString("\n• ", prefix = "• ")

        var totalBill = 0
        val gstRate = 18 // 18% GST
        var selectedDate: String? = null

        // Date picker dialog
        datePickerButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, selYear, selMonth, selDay ->
                selectedDate = "$selDay/${selMonth + 1}/$selYear"
                selectedDateText.text = "Selected Date: $selectedDate"
            }, year, month, day)

            dpd.datePicker.minDate = System.currentTimeMillis() // Prevent past dates
            dpd.show()
        }

        // Calculate total bill
        calculateButton.setOnClickListener {
            val guestCountStr = guestCountInput.text.toString()

            if (guestCountStr.isEmpty()) {
                Toast.makeText(this, "Enter guest count", Toast.LENGTH_SHORT).show()
            } else if (selectedDate == null) {
                Toast.makeText(this, "Please select an event date", Toast.LENGTH_SHORT).show()
            } else {
                val guestCount = guestCountStr.toInt()
                val subtotal = guestCount * pricePerPlate
                val gstAmount = subtotal * gstRate / 100
                totalBill = subtotal + gstAmount

                totalBillText.text = "Event Date: $selectedDate\n" +
                        "Subtotal: ₹$subtotal\n" +
                        "GST (${gstRate}%): ₹$gstAmount\n" +
                        "Total Bill: ₹$totalBill"
            }
        }

        // Confirm booking
        confirmButton.setOnClickListener {
            val guestCountStr = guestCountInput.text.toString()
            if (guestCountStr.isEmpty()) {
                Toast.makeText(this, "Enter guest count to confirm", Toast.LENGTH_SHORT).show()
            } else if (selectedDate == null) {
                Toast.makeText(this, "Please select an event date", Toast.LENGTH_SHORT).show()
            } else {
                val guestCount = guestCountStr.toInt()
                val subtotal = guestCount * pricePerPlate
                val gstAmount = subtotal * gstRate / 100
                totalBill = subtotal + gstAmount

                // 🔹 Check booking availability
                val bookingKey = "$packageName-$selectedDate"
                val prefs = getSharedPreferences("Bookings", Context.MODE_PRIVATE)
                val isAlreadyBooked = prefs.getBoolean(bookingKey, false)

                if (isAlreadyBooked) {
                    Toast.makeText(
                        this,
                        "No vacancy on this date for $packageName. Please choose another date.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Save booking as confirmed
                    prefs.edit().putBoolean(bookingKey, true).apply()

                    // Start confirmation page
                    val intent = Intent(this, CateringConfirmActivity::class.java)
                    intent.putExtra("packageName", packageName)
                    intent.putExtra("pricePerPlate", pricePerPlate)
                    intent.putExtra("guestCount", guestCount)
                    intent.putExtra("totalBill", totalBill)
                    intent.putExtra("eventDate", selectedDate) // Pass selected date
                    intent.putStringArrayListExtra("dishes", ArrayList(dishes))
                    startActivity(intent)
                }
            }
        }
    }
}
