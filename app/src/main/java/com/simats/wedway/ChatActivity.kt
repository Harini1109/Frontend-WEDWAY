package com.simats.wedway

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ChatbotActivity : AppCompatActivity() {

    private lateinit var messageContainer: LinearLayout
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var scrollView: ScrollView
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn: ImageView

    private var lastUserSaidThanks = false // Flag to track if user said thanks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageContainer = findViewById(R.id.messageContainer)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        scrollView = findViewById(R.id.scrollView)

        // Bottom navigation
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn = findViewById(R.id.ivBack)

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

        // First welcome message
        addBotMessage(
            "👋 Hello! Welcome to *Wedway Planner*. I’m your wedding assistant 💍.\n" +
                    "✨ How can I help you today? Would you like ideas about *Food 🍽️*, *Decoration 🎉*, *Budget 💰*, or *Checklist 📋*?"
        )

        btnSend.setOnClickListener {
            val userMessage = etMessage.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                addUserMessage(userMessage)
                etMessage.text.clear()
                handleBotResponse(userMessage)
            }
        }
    }

    private fun addUserMessage(message: String) {
        val tv = TextView(this).apply {
            text = message
            setPadding(16, 12, 16, 12)
            setBackgroundResource(android.R.color.holo_blue_light)
            setTextColor(ContextCompat.getColor(this@ChatbotActivity, android.R.color.white))
        }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(50, 10, 10, 10)
            gravity = android.view.Gravity.END
        }
        tv.layoutParams = params
        messageContainer.addView(tv)
        scrollToBottom()
    }

    private fun addBotMessage(message: String) {
        val tv = TextView(this).apply {
            text = message
            setPadding(16, 12, 16, 12)
            setBackgroundResource(android.R.color.holo_green_light)
            setTextColor(ContextCompat.getColor(this@ChatbotActivity, android.R.color.black))
        }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(10, 10, 50, 10)
            gravity = android.view.Gravity.START
        }
        tv.layoutParams = params
        messageContainer.addView(tv)
        scrollToBottom()
    }

    private fun handleBotResponse(userMessage: String) {
        val message = userMessage.lowercase()

        when {
            // Greetings
            message.contains("hi") || message.contains("hello") -> {
                addBotMessage(
                    "👋 Hi there! How can I help you today? Would you like ideas about " +
                            "*Food 🍽️*, *Decoration 🎉*, *Budget 💰*, or *Checklist 📋*?"
                )
                lastUserSaidThanks = false
            }

            // Thanks responses
            message.contains("thanks") || message.contains("thank you") -> {
                addBotMessage("😊 You're welcome! Happy to help. Anything else I can assist with?")
                lastUserSaidThanks = true
            }

            // If previous message was thanks and user continues
            lastUserSaidThanks && (message.contains("yes") || message.contains("ok")) -> {
                addBotMessage(
                    "✨ How can I help you today? Would you like ideas about *Food 🍽️*, *Decoration 🎉*, *Budget 💰*, or *Checklist 📋*?"
                )
                lastUserSaidThanks = false
            }

            // Food suggestions
            message.contains("food") -> addBotMessage(
                "🍴 Food Ideas:\n1. Live Food Stations 🍜🔥\n2. Fusion Cuisine 🍱\n3. Luxury Desserts 🍫\n4. Health Corner 🥗\n5. Signature Drinks 🍹"
            )

            // Decoration suggestions
            message.contains("decoration") -> addBotMessage(
                "🎀 Decoration Ideas:\n1. Royal Palace Theme 👑\n2. Rustic Garden 🌿\n3. Beach Vibes 🌊\n4. Floral Wonderland 🌸\n5. Fairy Lights & Candles 🕯️"
            )

            // Budget suggestions
            message.contains("budget") -> addBotMessage(
                "💰 Budget Tips:\n1. Allocate 40% for venue & catering 🏰🍽️\n2. 20% for decoration & flowers 🌸🎀\n3. 10% for attire & jewelry 👗💍\n4. 10% for photography & videography 📸🎥\n5. 20% for miscellaneous & contingency 💡"
            )

            // Checklist suggestions
            message.contains("checklist") -> addBotMessage(
                "📋 Wedding Checklist:\n1. Book venue and catering 🏰🍽️\n2. Finalize guest list 📝\n3. Choose decorations & theme 🎀🌸\n4. Arrange music & entertainment 🎶\n5. Confirm photography & videography 📸"
            )

            // Drinks
            message.contains("drinks") -> addBotMessage(
                "🍹 Drink Ideas:\n1. Mocktail Bar 🍸\n2. Signature Cocktails 🍹\n3. Fruit Punch 🍊🍍"
            )

            // Desserts
            message.contains("dessert") -> addBotMessage(
                "🍰 Dessert Ideas:\n1. Chocolate Fountain 🍫\n2. Cupcake Tower 🧁\n3. Custom Wedding Cake 🎂"
            )

            // Themes
            message.contains("theme") -> addBotMessage(
                "🎨 Wedding Themes:\n1. Vintage Glamour 🌟\n2. Boho Chic 🌿\n3. Winter Wonderland ❄️\n4. Garden Party 🌸"
            )

            // Music
            message.contains("music") -> addBotMessage(
                "🎶 Music Ideas:\n1. Live Band 🎸\n2. DJ Night 🎧\n3. Classical Ensemble 🎻"
            )

            // Default fallback
            else -> addBotMessage(
                "❌ Sorry, I can only help with *Food 🍽️*, *Decoration 🎉*, *Budget 💰*, *Checklist 📋*, " +
                        "*Drinks 🍹*, *Desserts 🍰*, *Themes 🎨*, or *Music 🎶* ideas."
            )
        }
    }

    private fun scrollToBottom() {
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
