package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VendorsActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView

    private lateinit var btnVenue: Button
    private lateinit var btnCatering: Button
    private lateinit var btnMusic: Button
    private lateinit var btnDecor: Button
    private lateinit var btnInvitation: Button
    private lateinit var btncake:Button
    private lateinit var btnreturn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendors)

        // Bottom navigation views
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Vendor buttons
        btnVenue = findViewById(R.id.btnVenue)
        btnCatering = findViewById(R.id.btnCatering)
        btnMusic = findViewById(R.id.btnMusic)
        btnDecor = findViewById(R.id.btnDecor)
        btnInvitation = findViewById(R.id.btnInvitation)
        btncake=findViewById(R.id.btnCake)
        btnreturn=findViewById(R.id.btnReturnGifts)


        // Apply window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bottom navigation click listeners
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeUserActivity::class.java))
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        navSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        backbtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Vendor buttons click listeners
        btnVenue.setOnClickListener {
            startActivity(Intent(this, WeddingtypeActivity::class.java))
        }

        btnCatering.setOnClickListener {
            startActivity(Intent(this, WeddingtypeActivity::class.java))
        }

        btnMusic.setOnClickListener {
            startActivity(Intent(this, MusicActivity::class.java))
        }

        btnDecor.setOnClickListener {
            startActivity(Intent(this, DecorActivity::class.java))
        }

        btnInvitation.setOnClickListener {
            startActivity(Intent(this, InvitationGenerationActivity::class.java))
        }
        btncake.setOnClickListener {
            startActivity(Intent(this, CakeorderActivity::class.java))
        }
        btnreturn.setOnClickListener {
            startActivity(Intent(this, ReturngiftActivity::class.java))
        }

    }
}
