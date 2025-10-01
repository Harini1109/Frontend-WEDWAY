package com.example.yourapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class InvitationGenerationActivity : AppCompatActivity() {
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation_generation)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Set navigation click listenerso
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
            val intent = Intent(this, ConfirmMusicActivity::class.java)
            startActivity(intent)
        }
        val etBride = findViewById<EditText>(R.id.etBrideName)
        val etGroom = findViewById<EditText>(R.id.etGroomName)
        val etLocation = findViewById<EditText>(R.id.etEventLocation)
        val etDate = findViewById<EditText>(R.id.etEventDate)
        val etTime = findViewById<EditText>(R.id.etEventTime)
        val etNotes = findViewById<EditText>(R.id.etEventNotes)
        val btnGenerate = findViewById<Button>(R.id.btnGenerateInvitation)

        // Date Picker
        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, y, m, d ->
                etDate.setText(String.format("%02d/%02d/%04d", d, m + 1, y))
            }, year, month, day)
            dpd.show()
        }

        // Time Picker
        etTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, { _, h, m ->
                val amPm = if (h >= 12) "PM" else "AM"
                val hourFormatted = if (h > 12) h - 12 else if (h == 0) 12 else h
                etTime.setText(String.format("%02d:%02d %s", hourFormatted, m, amPm))
            }, hour, minute, false)
            tpd.show()
        }

        // Navigate to Invitation Preview Page
        btnGenerate.setOnClickListener {
            val bride = etBride.text.toString().trim()
            val groom = etGroom.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val date = etDate.text.toString().trim()
            val time = etTime.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            if (bride.isNotEmpty() && groom.isNotEmpty() && location.isNotEmpty()
                && date.isNotEmpty() && time.isNotEmpty()) {

                val intent = Intent(this, InvitaionActivity::class.java)
                intent.putExtra("bride", bride)
                intent.putExtra("groom", groom)
                intent.putExtra("location", location)
                intent.putExtra("date", date)
                intent.putExtra("time", time)
                intent.putExtra("notes", notes)
                startActivity(intent)
            } else {
                // Optional: show error if any field is empty
                etBride.error = if (bride.isEmpty()) "Required" else null
                etGroom.error = if (groom.isEmpty()) "Required" else null
                etLocation.error = if (location.isEmpty()) "Required" else null
                etDate.error = if (date.isEmpty()) "Required" else null
                etTime.error = if (time.isEmpty()) "Required" else null
            }
        }
    }
}
