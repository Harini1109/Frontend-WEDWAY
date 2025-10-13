package com.simats.wedway

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {

    private lateinit var startBtn: Button
    private lateinit var subscribeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)

        // Apply edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get Started Button
        startBtn = findViewById(R.id.btnGetStarted)
        startBtn.setOnClickListener {
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

        // Subscribe Button
        subscribeBtn = findViewById(R.id.btnSubscribe)
        subscribeBtn.setOnClickListener {
            // Navigate to SubscriptionActivity (create this activity)
            val intent = Intent(this, SubscriptionActivity::class.java)
            startActivity(intent)
        }
    }
}
