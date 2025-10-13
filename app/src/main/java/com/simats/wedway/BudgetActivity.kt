package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class BudgetActivity : AppCompatActivity() {

    private lateinit var etTotalBudget: EditText
    private lateinit var etVenue: EditText
    private lateinit var etCatering: EditText
    private lateinit var etDecoration: EditText
    private lateinit var etOther: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView
    private lateinit var tvNote: TextView
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView


    companion object {
        private const val PREFS_NAME = "WeddingAppPrefs"
        private const val KEY_TOTAL_BUDGET = "TotalBudget"
        private const val KEY_VENUE = "VenueBudget"
        private const val KEY_CATERING = "CateringBudget"
        private const val KEY_DECORATION = "DecorationBudget"
        private const val KEY_OTHER = "OtherBudget"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        // Initialize budget views
        etTotalBudget = findViewById(R.id.etTotalBudget)
        etVenue = findViewById(R.id.etVenue)
        etCatering = findViewById(R.id.etCatering)
        etDecoration = findViewById(R.id.etDecoration)
        etOther = findViewById(R.id.etOther)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvResult = findViewById(R.id.tvResult)
        tvNote = findViewById(R.id.tvNote)

        // Load saved budget
        loadBudget()

        btnCalculate.setOnClickListener {
            calculateRemainingBudget()
        }

        // Initialize bottom navigation views
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Set navigation click listeners
        navHome.setOnClickListener { startActivity(Intent(this, HomeUserActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        navSettings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
        backbtn.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
    }

    private fun calculateRemainingBudget() {
        val total = etTotalBudget.text.toString().toDoubleOrNull()
        val venue = etVenue.text.toString().toDoubleOrNull() ?: 0.0
        val catering = etCatering.text.toString().toDoubleOrNull() ?: 0.0
        val decoration = etDecoration.text.toString().toDoubleOrNull() ?: 0.0
        val other = etOther.text.toString().toDoubleOrNull() ?: 0.0

        if (total == null) {
            Toast.makeText(this, "Please enter a valid total budget", Toast.LENGTH_SHORT).show()
            return
        }

        val used = venue + catering + decoration + other
        val remaining = total - used

        tvResult.text = "💵 Total Budget: $total\n" +
                "🏰 Venue: $venue\n" +
                "🍽️ Catering: $catering\n" +
                "🎀 Decoration: $decoration\n" +
                "🛒 Other: $other\n\n" +
                "✅ Remaining Budget: $remaining"

        tvNote.text = if (remaining < 0) {
            "⚠️ Warning: You have exceeded your total budget! Consider reducing some expenses."
        } else {
            "💡 Reminder: Keep some extra funds for emergency or unexpected expenses."
        }

        // Save budget to SharedPreferences
        saveBudget(total, venue, catering, decoration, other)
    }

    private fun saveBudget(total: Double, venue: Double, catering: Double, decoration: Double, other: Double) {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        sharedPref.edit().apply {
            putFloat(KEY_TOTAL_BUDGET, total.toFloat())
            putFloat(KEY_VENUE, venue.toFloat())
            putFloat(KEY_CATERING, catering.toFloat())
            putFloat(KEY_DECORATION, decoration.toFloat())
            putFloat(KEY_OTHER, other.toFloat())
            apply()
        }
    }

    private fun loadBudget() {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        etTotalBudget.setText(sharedPref.getFloat(KEY_TOTAL_BUDGET, 0f).toString())
        etVenue.setText(sharedPref.getFloat(KEY_VENUE, 0f).toString())
        etCatering.setText(sharedPref.getFloat(KEY_CATERING, 0f).toString())
        etDecoration.setText(sharedPref.getFloat(KEY_DECORATION, 0f).toString())
        etOther.setText(sharedPref.getFloat(KEY_OTHER, 0f).toString())
    }

    // Call this on logout to clear saved budget
    private fun clearBudget() {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        sharedPref.edit().clear().apply()
        etTotalBudget.text.clear()
        etVenue.text.clear()
        etCatering.text.clear()
        etDecoration.text.clear()
        etOther.text.clear()
        tvResult.text = ""
        tvNote.text = ""
    }
}
