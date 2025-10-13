package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WeddingtypeActivity : AppCompatActivity() {

    private lateinit var regionSpinner: Spinner
    private lateinit var religionSpinner: Spinner
    private lateinit var guestsSpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var btnVenue: Button
    private lateinit var btnCatering: Button
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weddingtype)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Set navigation click listeners
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
        backbtn.setOnClickListener {
            val intent = Intent(this, HomeUserActivity::class.java)
            startActivity(intent)
        }
        // Handle system insets (status bar/padding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI
        regionSpinner = findViewById(R.id.spinnerRegion)
        religionSpinner = findViewById(R.id.spinnerReligion)
        guestsSpinner = findViewById(R.id.spinnerGuests)
        citySpinner = findViewById(R.id.spinnerCity)
        btnVenue = findViewById(R.id.btnVenue)
        btnCatering = findViewById(R.id.btnCatering)

        // Setup Spinners with adapters
        setupSpinner(regionSpinner, R.array.region_options)
        setupSpinner(religionSpinner, R.array.religion_options)
        setupSpinner(guestsSpinner, R.array.guest_options)
        setupSpinner(citySpinner, R.array.city_options)

        // Venue button click
        btnVenue.setOnClickListener {
            val region = regionSpinner.selectedItem.toString()
            val religion = religionSpinner.selectedItem.toString()
            val guests = guestsSpinner.selectedItem.toString()
            val city = citySpinner.selectedItem.toString()

            // Confirmation toast
            Toast.makeText(this, "Venue Details Saved", Toast.LENGTH_SHORT).show()

            // Navigate to VenueActivity
            val intent = Intent(this, VenueActivity::class.java).apply {
                putExtra("region", region)
                putExtra("religion", religion)
                putExtra("guests", guests)
                putExtra("city", city)
            }
            startActivity(intent)
        }

        // Catering button click
        btnCatering.setOnClickListener {
            val region = regionSpinner.selectedItem.toString()
            val religion = religionSpinner.selectedItem.toString()
            val guests = guestsSpinner.selectedItem.toString()
            val city = citySpinner.selectedItem.toString()

            // Confirmation toast
            Toast.makeText(this, "Catering Details Saved", Toast.LENGTH_SHORT).show()

            // Navigate to CateringActivity
            val intent = Intent(this, CateringActivity::class.java).apply {
                putExtra("region", region)
                putExtra("religion", religion)
                putExtra("guests", guests)
                putExtra("city", city)
            }
            startActivity(intent)
        }
    }

    // Reusable spinner setup function
    private fun setupSpinner(spinner: Spinner, arrayRes: Int) {
        val adapter = ArrayAdapter.createFromResource(
            this,
            arrayRes,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}
