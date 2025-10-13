package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
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
    private lateinit var mobileEt: EditText
    private lateinit var genderEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_signup)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signupBtn = findViewById(R.id.signUpBtn)
        signinBtn = findViewById(R.id.signInText)
        firstNameEt = findViewById(R.id.firstName)
        lastNameEt = findViewById(R.id.lastName)
        emailEt = findViewById(R.id.email)
        passwordEt = findViewById(R.id.password)
        confirmPasswordEt = findViewById(R.id.confirmPassword)
        mobileEt = findViewById(R.id.mobileNumber)
        genderEt = findViewById(R.id.gender)

        signupBtn.setOnClickListener {
            if (validateInputs()) {
                val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("firstName", firstNameEt.text.toString().trim())
                editor.putString("lastName", lastNameEt.text.toString().trim())
                editor.putString("email", emailEt.text.toString().trim())
                editor.putString("mobile", mobileEt.text.toString().trim())
                editor.putString("gender", genderEt.text.toString().trim())
                editor.apply()

                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DetailsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        signinBtn.setOnClickListener {
            startActivity(Intent(this, UserLoginActivity::class.java))
        }
    }

    private fun validateInputs(): Boolean {
        val firstName = firstNameEt.text.toString().trim()
        val lastName = lastNameEt.text.toString().trim()
        val email = emailEt.text.toString().trim()
        val mobile = mobileEt.text.toString().trim()
        val gender = genderEt.text.toString().trim()
        val password = passwordEt.text.toString().trim()
        val confirmPassword = confirmPasswordEt.text.toString().trim()

        return when {
            firstName.isEmpty() -> { firstNameEt.error = "First name is required"; false }
            lastName.isEmpty() -> { lastNameEt.error = "Last name is required"; false }
            email.isEmpty() -> { emailEt.error = "Email is required"; false }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> { emailEt.error = "Invalid email"; false }
            mobile.isEmpty() -> { mobileEt.error = "Mobile number is required"; false }
            gender.isEmpty() -> { genderEt.error = "Gender is required"; false }
            password.isEmpty() -> { passwordEt.error = "Password is required"; false }
            confirmPassword.isEmpty() -> { confirmPasswordEt.error = "Confirm password is required"; false }
            password != confirmPassword -> { confirmPasswordEt.error = "Passwords do not match"; false }
            else -> true
        }
    }
}
