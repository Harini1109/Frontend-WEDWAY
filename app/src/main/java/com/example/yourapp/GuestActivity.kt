package com.example.yourapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GuestActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var btnPickContact: Button
    private lateinit var backbtn:ImageView
    private lateinit var guestListView: ListView
    private val guestList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    companion object {
        private const val REQUEST_CONTACT = 1
        private const val REQUEST_PERMISSION_READ_CONTACTS = 100
        private const val PREFS_NAME = "WeddingAppPrefs"
        private const val KEY_GUEST_LIST = "GuestList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        btnPickContact = findViewById(R.id.btnPickContact)
        guestListView = findViewById(R.id.guestListView)
        backbtn=findViewById(R.id.ivBack)

        // Load saved guest list
        loadGuestList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, guestList)
        guestListView.adapter = adapter

        // Bottom navigation
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

        btnPickContact.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_PERMISSION_READ_CONTACTS
                )
            } else {
                pickContact()
            }
        }
    }

    private fun pickContact() {
        val contactPickerIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(contactPickerIntent, REQUEST_CONTACT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK) {
            val contactUri: Uri? = data?.data
            contactUri?.let { uri ->
                val cursor = contentResolver.query(
                    uri,
                    null,
                    null,
                    null,
                    null
                )
                cursor?.use {
                    if (it.moveToFirst()) {
                        val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        val contactName = it.getString(nameIndex)
                        if (!guestList.contains(contactName)) { // avoid duplicates
                            guestList.add(contactName)
                            adapter.notifyDataSetChanged()
                            saveGuestList() // save after adding
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_CONTACTS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                pickContact()
            } else {
                Toast.makeText(this, "Permission denied to read contacts", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save guest list to SharedPreferences
    private fun saveGuestList() {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        sharedPref.edit().putStringSet(KEY_GUEST_LIST, guestList.toSet()).apply()
    }

    // Load guest list from SharedPreferences
    private fun loadGuestList() {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedSet = sharedPref.getStringSet(KEY_GUEST_LIST, emptySet()) ?: emptySet()
        guestList.clear()
        guestList.addAll(savedSet)
    }

    // Clear guest list on logout
    private fun clearGuestList() {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        sharedPref.edit().remove(KEY_GUEST_LIST).apply()
        guestList.clear()
        adapter.notifyDataSetChanged()
    }
}
