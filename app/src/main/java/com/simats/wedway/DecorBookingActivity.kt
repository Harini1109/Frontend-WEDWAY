package com.simats.wedway

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DecorBookingActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView

    // In-memory map to store booked dates per package (replace with database for real apps)
    private val bookedDates = mutableMapOf<String, MutableList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decor_booking)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        navHome.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        backbtn.setOnClickListener { startActivity(Intent(this, DecorActivity::class.java)) }

        // Views
        val tvPackageName = findViewById<TextView>(R.id.tvPackageName)
        val tvBasePrice = findViewById<TextView>(R.id.tvBasePrice)
        val tvGST = findViewById<TextView>(R.id.tvGST)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEventDate = findViewById<EditText>(R.id.etEventDate) // New EditText for date
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        // Get package data from intent
        val packageName = intent.getStringExtra("PACKAGE_NAME") ?: "Decor Package"
        val basePrice = intent.getDoubleExtra("BASE_PRICE", 0.0)
        val gstAmount = intent.getDoubleExtra("GST_AMOUNT", 0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)

        // Display package info
        tvPackageName.text = "Package: $packageName"
        tvBasePrice.text = "Base Price: ₹${"%.2f".format(basePrice)}"
        tvGST.text = "GST (18%): ₹${"%.2f".format(gstAmount)}"
        tvTotal.text = "Total Amount: ₹${"%.2f".format(totalAmount)}"

        // Date picker
        etEventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    etEventDate.setText(sdf.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Confirm booking
        btnConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val eventDate = etEventDate.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || eventDate.isEmpty()) {
                Toast.makeText(this, "Please enter all details including event date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if date already booked for this package
            val bookedForPackage = bookedDates.getOrDefault(packageName, mutableListOf())
            if (bookedForPackage.contains(eventDate)) {
                Toast.makeText(this, "No vacancy on $eventDate for this package. Please select another date.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Add date to booked list
            bookedForPackage.add(eventDate)
            bookedDates[packageName] = bookedForPackage

            // Navigate to ConfirmDecorActivity
            val intent = Intent(this, ConfirmDecorActivity::class.java)
            intent.putExtra("PACKAGE_NAME", packageName)
            intent.putExtra("BASE_PRICE", basePrice)
            intent.putExtra("GST_AMOUNT", gstAmount)
            intent.putExtra("TOTAL_AMOUNT", totalAmount)
            intent.putExtra("USER_NAME", name)
            intent.putExtra("USER_PHONE", phone)
            intent.putExtra("EVENT_DATE", eventDate)
            startActivity(intent)
        }
    }
}
