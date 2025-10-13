package com.simats.wedway

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var nextBtn: Button
    private lateinit var brideNameEt: EditText
    private lateinit var groomNameEt: EditText
    private lateinit var weddingDateEt: EditText
    private lateinit var weddingTimeEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize EditTexts and Button
        brideNameEt = findViewById(R.id.etBrideName)
        groomNameEt = findViewById(R.id.etGroomName)
        weddingDateEt = findViewById(R.id.etWeddingDate)
        weddingTimeEt = findViewById(R.id.etWeddingTime)
        nextBtn = findViewById(R.id.btnNext)

        // Date Picker
        weddingDateEt.setOnClickListener { showDatePicker() }

        // Time Picker
        weddingTimeEt.setOnClickListener { showTimePicker() }

        // Next button click
        nextBtn.setOnClickListener {
            if (validateInputs()) {
                val brideName = brideNameEt.text.toString().trim()
                val groomName = groomNameEt.text.toString().trim()
                val weddingDate = weddingDateEt.text.toString().trim()
                val weddingTime = weddingTimeEt.text.toString().trim()

                // ✅ Save to SharedPreferences
                val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("clientName", brideName) // using bride as client
                editor.putString("brideName", brideName)
                editor.putString("groomName", groomName)
                editor.putString("weddingDate", weddingDate)
                editor.putString("weddingTime", weddingTime)
                editor.apply()

                // Navigate to HomeUserActivity
                val intent = Intent(this, HomeUserActivity::class.java)
                startActivity(intent)
                finish() // prevent going back to details again
            }
        }
    }

    // Show DatePicker
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                weddingDateEt.setText("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    // Show TimePicker
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this,
            { _, hour, minute ->
                weddingTimeEt.setText(String.format("%02d:%02d", hour, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    // Validate all inputs
    private fun validateInputs(): Boolean {
        val brideName = brideNameEt.text.toString().trim()
        val groomName = groomNameEt.text.toString().trim()
        val weddingDate = weddingDateEt.text.toString().trim()
        val weddingTime = weddingTimeEt.text.toString().trim()
        return when {
            brideName.isEmpty() -> { brideNameEt.error = "Bride’s name is required"; false }
            groomName.isEmpty() -> { groomNameEt.error = "Groom’s name is required"; false }
            weddingDate.isEmpty() -> { weddingDateEt.error = "Wedding date is required"; false }
            weddingTime.isEmpty() -> { weddingTimeEt.error = "Wedding time is required"; false }
            else -> true
        }
    }
}
