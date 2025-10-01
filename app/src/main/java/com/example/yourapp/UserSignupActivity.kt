package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserSignupActivity : AppCompatActivity() {

    private lateinit var signupBtn: Button
    private lateinit var signinBtn: TextView
    private lateinit var firstNameEt: EditText
    private lateinit var lastNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confirmPasswordEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_signup)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind views
        signupBtn = findViewById(R.id.signUpBtn)
        signinBtn = findViewById(R.id.signInText)
        firstNameEt = findViewById(R.id.firstName)
        lastNameEt = findViewById(R.id.lastName)
        emailEt = findViewById(R.id.email)
        passwordEt = findViewById(R.id.password)
        confirmPasswordEt = findViewById(R.id.confirmPassword)

        // Signup button click
        signupBtn.setOnClickListener {
            if (validateInputs()) {
                val firstName = firstNameEt.text.toString().trim()
                val lastName = lastNameEt.text.toString().trim()
                val email = emailEt.text.toString().trim()

                // Save user data in SharedPreferences
                val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("firstName", firstName)
                editor.putString("lastName", lastName)
                editor.putString("email", email)
                editor.apply()

                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()

                // Navigate to HomeUserActivity
                val intent = Intent(this, DetailsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Signin button click
        signinBtn.setOnClickListener {
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(): Boolean {
        val firstName = firstNameEt.text.toString().trim()
        val lastName = lastNameEt.text.toString().trim()
        val email = emailEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()
        val confirmPassword = confirmPasswordEt.text.toString().trim()

        return when {
            firstName.isEmpty() -> {
                firstNameEt.error = "First name is required"
                false
            }
            lastName.isEmpty() -> {
                lastNameEt.error = "Last name is required"
                false
            }
            email.isEmpty() -> {
                emailEt.error = "Email is required"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailEt.error = "Enter a valid email"
                false
            }
            password.isEmpty() -> {
                passwordEt.error = "Password is required"
                false
            }
            password.length < 6 -> {
                passwordEt.error = "Password must be at least 6 characters"
                false
            }
            confirmPassword.isEmpty() -> {
                confirmPasswordEt.error = "Confirm password is required"
                false
            }
            password != confirmPassword -> {
                confirmPasswordEt.error = "Passwords do not match"
                false
            }
            else -> true
        }
    }
}
