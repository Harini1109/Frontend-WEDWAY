package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MusicActivity : AppCompatActivity() {
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Navigation clicks
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
            val intent = Intent(this, ConfirmDecorActivity::class.java)
            startActivity(intent)
        }

        val btnLiveMusic = findViewById<Button>(R.id.btnLiveMusic)
        val btnDJ = findViewById<Button>(R.id.btnDJ)

        btnLiveMusic.setOnClickListener {
            // Navigate to Live Music selection page
            val intent = Intent(this, LiveMusicActivity::class.java)
            startActivity(intent)
        }

        btnDJ.setOnClickListener {
            // Navigate to DJ selection page
            val intent = Intent(this, DjselectionActivity::class.java)
            startActivity(intent)
        }
    }
}
