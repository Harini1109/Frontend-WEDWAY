package com.example.yourapp

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class InvitaionActivity : AppCompatActivity() {

    private lateinit var cardPreview: CardView
    private lateinit var tvTitle: TextView
    private lateinit var tvCouple: TextView
    private lateinit var tvVenue: TextView
    private lateinit var tvDateTime: TextView
    private lateinit var tvNotes: TextView
    private lateinit var btnSave: Button
    private lateinit var btnShare: Button
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitaion)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)

        // Set navigation click listeners
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

        // Initialize views
        cardPreview = findViewById(R.id.cardPreview)
        tvTitle = findViewById(R.id.tvPreviewTitle)
        tvCouple = findViewById(R.id.tvPreviewCouple)
        tvVenue = findViewById(R.id.tvPreviewVenue)
        tvDateTime = findViewById(R.id.tvPreviewDateTime)
        tvNotes = findViewById(R.id.tvPreviewNotes)
        btnSave = findViewById(R.id.btnSave)
        btnShare = findViewById(R.id.btnShare)

        // Receive data from previous activity
        val bride = intent.getStringExtra("bride") ?: "Bride"
        val groom = intent.getStringExtra("groom") ?: "Groom"
        val location = intent.getStringExtra("location") ?: "Venue"
        val date = intent.getStringExtra("date") ?: "Date"
        val time = intent.getStringExtra("time") ?: "Time"
        val notes = intent.getStringExtra("notes")
            ?: "We look forward to celebrating this joyous occasion with you!"

        // Set text views
        tvTitle.text = "Wedding Invitation"
        tvCouple.text = "$bride ❤ $groom"
        tvVenue.text = "At: $location"
        tvDateTime.text = "On: $date, $time"
        tvNotes.text = notes

        // Save button click
        btnSave.setOnClickListener {
            val bitmap = getBitmapFromView(cardPreview)
            val savedUri = saveImageToGallery(bitmap)
            if (savedUri != null) {
                Toast.makeText(this, "Invitation saved to gallery!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save invitation", Toast.LENGTH_SHORT).show()
            }
        }

        // Share button click
        btnShare.setOnClickListener {
            val bitmap = getBitmapFromView(cardPreview)
            val savedUri = saveImageToGallery(bitmap)
            if (savedUri != null) {
                shareImage(savedUri)
            } else {
                Toast.makeText(this, "Failed to share invitation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Convert CardView to Bitmap
    private fun getBitmapFromView(view: CardView): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // Save bitmap to gallery (supports Android Q and above)
    private fun saveImageToGallery(bitmap: Bitmap): Uri? {
        val filename = "Invitation_${System.currentTimeMillis()}.png"
        var imageUri: Uri? = null

        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Invitations")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                imageUri?.let { uri ->
                    resolver.openOutputStream(uri)?.use { outStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                    } ?: return null
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }
            } else {
                // For older devices
                val imagesDir = android.os.Environment.getExternalStoragePublicDirectory(
                    android.os.Environment.DIRECTORY_PICTURES
                ).toString() + "/Invitations"
                val file = java.io.File(imagesDir)
                if (!file.exists()) file.mkdirs()
                val imageFile = java.io.File(file, filename)
                val outStream = java.io.FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
                outStream.close()
                imageUri = Uri.fromFile(imageFile)

                // Notify gallery
                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri))
            }
            imageUri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Share image via other apps
    private fun shareImage(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Invitation via"))
    }
}
