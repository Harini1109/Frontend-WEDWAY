package com.simats.wedway

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class VenueBookingActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var etEventDate: EditText
    private lateinit var backbtn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_booking)

        // Bottom navigation
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
            startActivity(Intent(this, VenueActivity::class.java))
        }

        // Views
        val tvHallName = findViewById<TextView>(R.id.tvHallName)
        val etUserName = findViewById<EditText>(R.id.etUserName)
        val etGuestCount = findViewById<EditText>(R.id.etGuestCount)
        val tvTotalPrice = findViewById<TextView>(R.id.tvTotalPrice)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        etEventDate = findViewById(R.id.etEventDate)

        // Get hall details from Intent
        val hallName = intent.getStringExtra("hallName") ?: "Unknown Hall"
        val hallPrice = intent.getDoubleExtra("hallPrice", 0.0)
        tvHallName.text = "Booking: $hallName"

        var totalWithGST = 0.0

        // Date Picker
        etEventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val date = "$d/${m + 1}/$y"
                etEventDate.setText(date)
            }, year, month, day)

            datePicker.show()
        }

        // Calculate total with GST
        btnCalculate.setOnClickListener {
            val guestCount = etGuestCount.text.toString().toIntOrNull()
            if (guestCount == null || guestCount <= 0) {
                Toast.makeText(this, "Enter a valid number of guests", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gstRate = 0.18
            val totalAmount = hallPrice
            val gstAmount = totalAmount * gstRate
            totalWithGST = totalAmount + gstAmount

            tvTotalPrice.text =
                "Total Amount (including 18% GST): ₹${"%.2f".format(totalWithGST)}"
        }

        // Confirm booking
        btnConfirm.setOnClickListener {
            val userName = etUserName.text.toString().trim()
            val guestCount = etGuestCount.text.toString().toIntOrNull()
            val eventDate = etEventDate.text.toString().trim()

            if (userName.isEmpty() || guestCount == null || guestCount <= 0 || eventDate.isEmpty()) {
                Toast.makeText(this, "Please enter all details including date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (totalWithGST == 0.0) {
                Toast.makeText(this, "Please calculate the total amount first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check for duplicate booking
            val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
            val bookings = sharedPref.getStringSet("VenueBookings", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            val bookingKey = "$hallName-$eventDate"

            if (bookings.any { it.startsWith(bookingKey) }) {
                Toast.makeText(this, "No vacancy! Already booked on $eventDate", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Save booking
            bookings.add("$bookingKey: Total ₹${"%.2f".format(totalWithGST)}")
            sharedPref.edit().putStringSet("VenueBookings", bookings).apply()

            // Navigate to ConfirmVenueActivity
            val intent = Intent(this, ConfirmVenueActivity::class.java)
            intent.putExtra("userName", userName)
            intent.putExtra("hallName", hallName)
            intent.putExtra("guestCount", guestCount)
            intent.putExtra("eventDate", eventDate)
            intent.putExtra("totalAmount", totalWithGST)
            startActivity(intent)
        }
    }
}
