package com.simats.wedway

import android.Manifest
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*

class TaskSchedulerActivity : AppCompatActivity() {

    private lateinit var etTaskTitle: EditText
    private lateinit var btnPickDate: Button
    private lateinit var btnAddTask: Button
    private lateinit var llAddedTasks: LinearLayout
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_scheduler)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)
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

        etTaskTitle = findViewById(R.id.etTaskTitle)
        btnPickDate = findViewById(R.id.btnPickDate)
        btnAddTask = findViewById(R.id.btnAddTask)
        llAddedTasks = findViewById(R.id.llAddedTasks)

        createNotificationChannel()

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        // Date picker
        btnPickDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val cal = Calendar.getInstance()
                    cal.set(selectedYear, selectedMonth, selectedDay)
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    selectedDate = sdf.format(cal.time)
                    btnPickDate.text = selectedDate
                }, year, month, day
            )
            datePickerDialog.show()
        }

        // Add task button
        btnAddTask.setOnClickListener {
            val taskTitle = etTaskTitle.text.toString().trim()
            if (taskTitle.isEmpty()) {
                Toast.makeText(this, "Please enter a task title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Please pick a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                addTaskToLayout(taskTitle, selectedDate)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val selected = sdf.parse(selectedDate)
                val today = Calendar.getInstance().apply { resetTime() }.time
                val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1); resetTime() }.time

                val sharedPref = getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
                val editor = sharedPref.edit()

                when {
                    selected == today -> {
                        showNotification(taskTitle)
                    }
                    selected == tomorrow -> {
                        val tomorrowTasks = sharedPref.getStringSet("TomorrowTasks", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
                        tomorrowTasks.add(taskTitle)
                        editor.putStringSet("TomorrowTasks", tomorrowTasks)
                    }
                }
                editor.apply()

                etTaskTitle.text.clear()
                btnPickDate.text = "Pick Date"
                selectedDate = ""

                Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error adding task: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addTaskToLayout(title: String, date: String) {
        val taskLayout = LinearLayout(this)
        taskLayout.orientation = LinearLayout.HORIZONTAL
        taskLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        taskLayout.setPadding(12, 12, 12, 12)

        val tvTitle = TextView(this)
        tvTitle.text = title
        tvTitle.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)
        tvTitle.textSize = 16f

        val tvDate = TextView(this)
        tvDate.text = date
        tvDate.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        tvDate.textSize = 16f

        taskLayout.addView(tvTitle)
        taskLayout.addView(tvDate)

        val divider = View(this)
        divider.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            1
        )
        divider.setBackgroundColor(resources.getColor(android.R.color.darker_gray))

        llAddedTasks.addView(taskLayout)
        llAddedTasks.addView(divider)
    }

    private fun showNotification(title: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please allow notifications in settings", Toast.LENGTH_SHORT).show()
                return
            }
        }
        val builder = NotificationCompat.Builder(this, "task_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Task Due Today!")
            .setContentText("Task \"$title\" is due today!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TaskSchedulerChannel"
            val descriptionText = "Channel for task reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("task_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Helper extension
    private fun Calendar.resetTime() {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}
