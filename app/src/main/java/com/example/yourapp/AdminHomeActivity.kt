package com.example.yourapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve user details from SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val firstName = prefs.getString("firstName", "N/A")
        val lastName = prefs.getString("lastName", "N/A")
        val email = prefs.getString("email", "N/A")
        val mobile = prefs.getString("mobile", "N/A")
        val gender = prefs.getString("gender", "N/A")

        // Combine and display the user data
        val userDetails = """
            👤 Name: $firstName $lastName
            📧 Email: $email
            📱 Mobile: $mobile
            ⚧ Gender: $gender
        """.trimIndent()

        val textView = findViewById<TextView>(R.id.tvUserDetails)
        textView.text = userDetails
    }
}
