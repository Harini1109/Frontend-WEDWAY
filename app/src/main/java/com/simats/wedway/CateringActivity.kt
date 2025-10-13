package com.simats.wedway

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.content.Intent

data class CateringPackage(
    val name: String,
    val pricePerPlate: Int, // price per plate
    val dishes: List<String>,
    val imageRes: Int
)

class CateringActivity : AppCompatActivity() {
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catering)
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

        val container = findViewById<LinearLayout>(R.id.cateringContainer)

        // Get region, religion, and guestCount from Intent
        val region = intent.getStringExtra("region") ?: "South Indian"
        val religion = intent.getStringExtra("religion") ?: "Hindu"

        val packages = getCateringPackages(region, religion)

        // Display all packages
        packages.forEach { pkg ->
            val card = CardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(16, 16, 16, 16) }
                radius = 16f
                cardElevation = 8f
                setContentPadding(16, 16, 16, 16)

                // Navigate to BookingActivity when clicked
                setOnClickListener {
                    val intent = Intent(this@CateringActivity, CateringBookingActivity::class.java)
                    intent.putExtra("packageName", pkg.name)
                    intent.putExtra("pricePerPlate", pkg.pricePerPlate)
                    intent.putStringArrayListExtra("dishes", ArrayList(pkg.dishes))
                    startActivity(intent)
                }
            }

            val linearLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            val imageView = ImageView(this).apply {
                setImageResource(pkg.imageRes)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 400
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            val packageName = TextView(this).apply {
                text = pkg.name
                textSize = 20f
                setTextColor(Color.BLACK)
                setPadding(0, 12, 0, 0)
            }

            val packagePrice = TextView(this).apply {
                text = "Price per plate: ₹${pkg.pricePerPlate}"
                setPadding(0, 4, 0, 0)
            }

            val packageDishes = TextView(this).apply {
                text = pkg.dishes.joinToString("\n• ", prefix = "• ")
                setPadding(0, 8, 0, 0)
            }

            linearLayout.apply {
                addView(imageView)
                addView(packageName)
                addView(packagePrice)
                addView(packageDishes)
            }

            card.addView(linearLayout)
            container.addView(card)
        }
    }

    private fun getCateringPackages(region: String, religion: String): List<CateringPackage> {
        val southIndianVegDishes = listOf(
            "Medu Vada", "Mini Idli with chutney", "Masala Dosa Roll", "Paneer 65", "Veg Spring Rolls",
            "Sambar Rice", "Rasam Rice", "Lemon Rice", "Vegetable Kurma", "Poriyal",
            "Avial", "Curd Rice", "Appalam (Papad)", "Payasam", "Kesari", "Welcome Drink"
        )

        val northIndianVegDishes = listOf(
            "Paneer Tikka", "Hara Bhara Kebab", "Veg Pakora", "Corn Chaat", "Aloo Tikki",
            "Dal Makhani", "Paneer Butter Masala", "Jeera Rice", "Naan/Roti", "Vegetable Pulao",
            "Mixed Vegetable Curry", "Raita", "Gulab Jamun", "Rasmalai", "Welcome Drink"
        )

        val southIndianNonVegDishes = listOf(
            "Chicken 65", "Fish Fry", "Egg Roast", "Mutton Sukka", "Prawn Varuval",
            "Chicken Biryani", "Fish Curry with Rice", "Mutton Curry", "Egg Masala", "Pepper Chicken",
            "Chettinad Chicken", "Vegetable Kurma", "Appalam / Papad", "Payasam", "Welcome Drink"
        )

        val northIndianNonVegDishes = listOf(
            "Chicken Tikka", "Mutton Seekh Kebab", "Fish Amritsari", "Prawn Masala", "Egg Bhurji",
            "Butter Chicken", "Mutton Rogan Josh", "Chicken Biryani", "Paneer Butter Masala", "Dal Tadka",
            "Naan/Roti", "Jeera Rice", "Gulab Jamun", "Rasmalai", "Welcome Drink"
        )

        val imageRes = R.drawable.southveg
        val packageList = mutableListOf<CateringPackage>()
        val basePricePerPlate = 250 // ₹250 per plate as base

        for (i in 1..3) {
            val (name, dishes, price) = when {
                region.equals("South Indian", true) && religion.equals("Hindu", true) ->
                    Triple("South Indian Veg Package $i", southIndianVegDishes, basePricePerPlate + i * 50)
                region.equals("South Indian", true) && (religion.equals("Christian", true) || religion.equals("Muslim", true)) ->
                    Triple("South Indian Non-Veg Package $i", southIndianNonVegDishes, basePricePerPlate + i * 100)
                region.equals("North Indian", true) && religion.equals("Hindu", true) ->
                    Triple("North Indian Veg Package $i", northIndianVegDishes, basePricePerPlate + i * 50)
                region.equals("North Indian", true) && (religion.equals("Christian", true) || religion.equals("Muslim", true)) ->
                    Triple("North Indian Non-Veg Package $i", northIndianNonVegDishes, basePricePerPlate + i * 100)
                else -> Triple("Custom Package $i", southIndianVegDishes, basePricePerPlate)
            }
            packageList.add(
                CateringPackage(
                    name = name,
                    pricePerPlate = price,
                    dishes = dishes,
                    imageRes = imageRes
                )
            )
        }

        return packageList
    }
}
