package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NoteActivity : AppCompatActivity() {

    private lateinit var etNote: EditText
    private lateinit var btnSaveNote: Button
    private lateinit var noteListView: ListView

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView

    private lateinit var notesAdapter: ArrayAdapter<String>
    private var notesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        etNote = findViewById(R.id.etNote)
        btnSaveNote = findViewById(R.id.btnSaveNote)
        noteListView = findViewById(R.id.noteListView)

        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

        // Load saved notes
        loadNotes()

        // Set up adapter
        notesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)
        noteListView.adapter = notesAdapter

        // Save note button
        btnSaveNote.setOnClickListener {
            val noteText = etNote.text.toString().trim()
            if (noteText.isNotEmpty()) {
                notesList.add(noteText)
                notesAdapter.notifyDataSetChanged()
                saveNotes()
                etNote.text.clear()
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            }
        }

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
    }

    private fun loadNotes() {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        val savedNotes = sharedPref.getStringSet("Notes", emptySet()) ?: emptySet()
        notesList.clear()
        notesList.addAll(savedNotes)
    }

    private fun saveNotes() {
        val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
        sharedPref.edit().putStringSet("Notes", notesList.toSet()).apply()
    }
}
