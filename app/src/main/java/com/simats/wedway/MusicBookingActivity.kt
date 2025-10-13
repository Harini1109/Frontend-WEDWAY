package com.simats.wedway

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MusicBookingActivity : AppCompatActivity() {

    private lateinit var tvPackageName: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var etUserName: EditText
    private lateinit var etEventDate: EditText
    private lateinit var btnConfirmBooking: Button

    // Store booked dates for packages (in-memory for demo)
    private val bookedDates = mutableMapOf<String, MutableList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_booking)

        // Find views
        tvPackageName = findViewById(R.id.tvPackageName)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        etUserName = findViewById(R.id.etUserName)
        etEventDate = findViewById(R.id.etEventDate)
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking)

        // Get data from previous activity
        val packageName = intent.getStringExtra("PACKAGE_NAME") ?: "N/A"
        val basePrice = intent.getDoubleExtra("BASE_PRICE", 0.0)
        val gstAmount = intent.getDoubleExtra("GST_AMOUNT", 0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)

        // Display package info
        tvPackageName.text = "Package: $packageName"
        tvTotalPrice.text = "Total Price: ₹${String.format("%.2f", totalAmount)}"

        // Setup DatePicker for event date
        etEventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateString = format.format(selectedDate.time)
                    etEventDate.setText(dateString)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Confirm button click
        btnConfirmBooking.setOnClickListener {
            val userName = etUserName.text.toString().trim()
            val eventDate = etEventDate.text.toString().trim()

            if (userName.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (eventDate.isEmpty()) {
                Toast.makeText(this, "Please select an event date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check for duplicate booking
            val datesForPackage = bookedDates.getOrDefault(packageName, mutableListOf())
            if (datesForPackage.contains(eventDate)) {
                Toast.makeText(
                    this,
                    "No vacancy on $eventDate for this package. Please select another date.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Save booking date
            datesForPackage.add(eventDate)
            bookedDates[packageName] = datesForPackage

            // Navigate to ConfirmMusicActivity
            val intent = Intent(this, ConfirmMusicActivity::class.java)
            intent.putExtra("PACKAGE_NAME", packageName)
            intent.putExtra("BASE_PRICE", basePrice)
            intent.putExtra("GST_AMOUNT", gstAmount)
            intent.putExtra("TOTAL_AMOUNT", totalAmount)
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("EVENT_DATE", eventDate)
            startActivity(intent)
        }
    }
}
