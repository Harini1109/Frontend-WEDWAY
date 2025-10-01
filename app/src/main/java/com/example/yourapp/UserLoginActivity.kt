package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserLoginActivity : AppCompatActivity() {

    private lateinit var signinbtn: Button
    private lateinit var signupBtn: TextView
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_login)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        signinbtn = findViewById(R.id.loginBtn)
        signupBtn = findViewById(R.id.signupText)
        emailEt = findViewById(R.id.emailField)
        passwordEt = findViewById(R.id.passwordField)

        // User Sign Up navigation
        signupBtn.setOnClickListener {
            val intent = Intent(this, UserSignupActivity::class.java)
            startActivity(intent)
        }

        // User Login
        signinbtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Here you can implement real user login validation
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DetailsActivity::class.java))
            finish()
        }
    }
}
