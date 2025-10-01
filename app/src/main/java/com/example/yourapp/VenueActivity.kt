package com.example.yourapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

data class Hall(
    val name: String,
    val rating: String,
    val price: String,
    val description: List<String>,
    val imageRes: Int,
    val city: String,
    val maxCapacity: Int
)

class VenueActivity : AppCompatActivity() {
    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var backbtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)
        navHome = findViewById(R.id.navHome)
        navProfile = findViewById(R.id.navProfile)
        navSettings = findViewById(R.id.navSettings)
        backbtn=findViewById(R.id.ivBack)

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
        backbtn.setOnClickListener {
            val intent = Intent(this, WeddingtypeActivity::class.java)
            startActivity(intent)
        }
        val container = findViewById<LinearLayout>(R.id.hallContainer)

        // Get city and guestCount from Intent
        val city = intent.getStringExtra("city") ?: "Chennai"
        val guestCount = intent.getIntExtra("guestCount", 400)

        val halls = listOf(
            // ---------------- Chennai ----------------
            Hall(
                "Chennai Royal Palace (Below 500)",
                "⭐ 4.7",
                "Price: ₹80,000"
                ,
                listOf(
                    " Location: No. 15, Veeramani Road, T-Nagar, Chennai" ,
                            "Contact: +91 98401 76543 / +91 44 2456 7890",
                    "Located in central Chennai",
                    "Elegant South Indian architecture",
                    "Capacity 400 guests",
                    "Fully air-conditioned",
                    "Ample car parking",
                    "Luxury bridal suite",
                    "Premium lighting setup",
                    "Separate veg & non-veg kitchen",
                    "Dining hall with 200 seating",
                    "Stage with LED lighting",
                    "Wheelchair accessible",
                    "CCTV monitored security",
                    "Generator backup",
                    "Custom décor allowed",
                    "Nearby 3-star hotels"
                ),
                R.drawable.chennai1,
                city = "Chennai",
                maxCapacity = 500
            ),
            Hall(
                "Chennai Grand Convention (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,50,000",
                listOf(
                    " Location: Door No. 82, East Coast Road, Vettuvankeni, Chennai 600115 ",
                    "Contact: +91-98847 55582 / +91-98840 55522 / +91-98410 95556",
                    "Capacity up to 800 guests",
                    "Designer stage with LED wall",
                    "VIP guest lounge",
                    "Central AC with purification",
                    "Dedicated 200-car parking",
                    "Bridal & Groom suites",
                    "Dining for 400 at once",
                    "Modern sound system",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Customizable décor options",
                    "Security staff available",
                    "Luxury interiors",
                    "Housekeeping included",
                    "Metro connectivity nearby"
                ),
                R.drawable.chennai2,
                city = "Chennai",
                maxCapacity = 1000
            ),
            Hall(
                "Chennai Luxury Mahal (Above 1000)",
                "⭐ 4.8",
                "Price: ₹2,50,000",
                listOf(
                    "Location: 1/2, Cathedral Road, Egmore, Chennai 600008",
                    "Contact: +91 98412 33456 / +91 44 2815 6789",
                    "Capacity 1500+ guests",
                    "Palatial architecture",
                    "Royal chandeliers",
                    "Large valet parking",
                    "Multiple dining floors",
                    "Luxury bridal & groom suites",
                    "5-star level interiors",
                    "LED-lit stage",
                    "Backup generator",
                    "Wheelchair access",
                    "24/7 security staff",
                    "Event management tie-ups",
                    "Professional catering services",
                    "CCTV surveillance",
                    "Nearby 5-star hotels"
                ),
                R.drawable.chennaai3,
                city = "Chennai",
                maxCapacity = 2000
            ),// ---------------- Coimbatore ----------------
            Hall(
                "Coimbatore Classic Hall (Below 500)",
                "⭐ 4.5",
                "Price: ₹70,000",
                listOf(
                    "No. 52, Bharathi Nagar, Peelamedu, Coimbatore 641004",
                    "Contact: +91 98412 39456 / +91 44 2915 6789",
                    "Located in Peelamedu",
                    "Capacity 450 guests",
                    "Central AC hall",
                    "Elegant interiors",
                    "Parking for 80 cars",
                    "Bridal room available",
                    "LED stage lighting",
                    "Dining with 200 capacity",
                    "Wheelchair friendly",
                    "Backup power",
                    "CCTV coverage",
                    "Custom floral décor allowed",
                    "Nearby hotels",
                    "Housekeeping team",
                    "Affordable package"
                ),
                R.drawable.coim1,
                city = "Coimbatore",
                maxCapacity = 500
            ),
            Hall(
                "Coimbatore Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,40,000",
                listOf(
                    " Location: 4/155 B-E, Pudhu Road, Perur (via), Mathampatti, Coimbatore – 641010",
                    "Contact: +91 70106 58923 / +91 98422 26165",
                    "Capacity 900 guests",
                    "Premium interiors",
                    "Large dining hall",
                    "Separate kitchens",
                    "Car parking 150+",
                    "LED stage setup",
                    "Central AC system",
                    "Generator backup",
                    "Wheelchair access",
                    "Professional staff",
                    "Nearby bus stand",
                    "Security services",
                    "VIP guest lounge",
                    "Decoration allowed",
                    "Modern sound system"
                ),
                R.drawable.coim2,
                city = "Coimbatore",
                maxCapacity = 1000
            ),
            Hall(
                "Coimbatore Royal Convention (Above 1000)",
                "⭐ 4.7",
                "Price: ₹2,20,000",
                listOf(
                    "Location:Muthalipalayam Pirivu, Coimbatore",
                    "Contact: +91 94875 08483",
                    "Capacity 1600 guests",
                    "Royal design interiors",
                    "Stage with LED wall",
                    "Dining for 800",
                    "Separate veg & non-veg kitchens",
                    "Luxury bridal suite",
                    "Car parking 300+",
                    "Backup power generator",
                    "Security and CCTV",
                    "Wheelchair friendly",
                    "Decoration services",
                    "Nearby 4-star hotels",
                    "Professional catering support",
                    "Spacious lawns",
                    "Housekeeping included"
                ),
                R.drawable.coim3,
                city = "Coimbatore",
                maxCapacity = 2000
            ),

            // ---------------- Madurai ----------------
            Hall(
                "Madurai Meenakshi Mahal (Below 500)",
                "⭐ 4.5",
                "Price: ₹65,000",
                listOf(
                    " Location: Near Meenakshi Amman Temple, Madurai",
                    " Contact: +91 99445 66667",
                    "Capacity 400 guests",
                    "Traditional design",
                    "Located near temple",
                    "Parking 60 cars",
                    "Central AC hall",
                    "Stage with lighting",
                    "Dining hall separate",
                    "Bridal suite available",
                    "Wheelchair accessible",
                    "Backup generator",
                    "Housekeeping team",
                    "CCTV monitored",
                    "Custom décor",
                    "Nearby budget hotels",
                    "Affordable pricing"
                ),
                R.drawable.mad1,
                city = "Madurai",
                maxCapacity = 500
            ),
            Hall(
                "Madurai Heritage Hall (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,20,000",
                listOf(
                    "Location: 10, Madurai – Dindigul Road, Madurai, Tamil Nadu 625014",
                    "Contact: +91 452 435 5555",
                    "Capacity 700 guests",
                    "Traditional interiors",
                    "Stage with LED backdrop",
                    "Central AC",
                    "Large dining section",
                    "Car parking 120+",
                    "Bridal suite",
                    "Wheelchair friendly",
                    "Backup power",
                    "Security staff",
                    "Nearby railway station",
                    "Housekeeping team",
                    "Decoration allowed",
                    "Professional catering tie-ups",
                    "Affordable packages"
                ),
                R.drawable.mad2,
                city = "Madurai",
                maxCapacity = 1000
            ),
            Hall(
                "Madurai Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹2,00,000",
                listOf(
                    "Location:East Coast Road, Kilakarai - 623517, Ramanathapuram District, Tamil Nadu",
                    "Contact:91 98840 87101 / +91 98840 87102",
                    "Capacity 1400 guests",
                    "Palatial style design",
                    "Bridal & groom suites",
                    "LED-lit stage",
                    "Separate dining floors",
                    "Wheelchair accessible",
                    "Backup generator",
                    "Car parking 250+",
                    "Nearby 4-star hotels",
                    "Custom décor allowed",
                    "Security & CCTV",
                    "Professional staff",
                    "AC hall",
                    "In-house decorators",
                    "VIP lounge"
                ),
                R.drawable.mad3,
                city = "Madurai",
                maxCapacity = 2000
            ),

            // ---------------- Trichy ----------------
            Hall(
                "Trichy Classic Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹60,000",
                listOf(
                    "Location: 1, Williams Road, Cantonment, Tiruchirappalli – 620001",
                    "Contact: +91 431 241 1111",
                    "Capacity 450 guests",
                    "Located near bus stand",
                    "Elegant interiors",
                    "Central AC hall",
                    "Stage with LED lighting",
                    "Dining for 200",
                    "Car parking 70+",
                    "Bridal room",
                    "Wheelchair access",
                    "Generator backup",
                    "Custom décor allowed",
                    "Security staff",
                    "Nearby budget hotels",
                    "Housekeeping team",
                    "Professional staff"
                ),
                R.drawable.images,
                city = "Trichy",
                maxCapacity = 500
            ),
            Hall(
                "Trichy Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,30,000",
                listOf(
                    "Capacity 850 guests",
                    "Premium interiors",
                    "VIP lounge",
                    "Stage with LED wall",
                    "Car parking 150+",
                    "Dining hall large",
                    "Separate kitchens",
                    "Wheelchair accessible",
                    "Generator backup",
                    "Housekeeping team",
                    "Nearby railway station",
                    "Security services",
                    "Professional catering tie-ups",
                    "Custom décor allowed",
                    "Modern sound setup"
                ),
                R.drawable.images,
                city = "Trichy",
                maxCapacity = 1000
            ),
            Hall(
                "Trichy Luxury Palace (Above 1000)",
                "⭐ 4.8",
                "Price: ₹2,10,000",
                listOf(
                    "Capacity 1600 guests",
                    "Royal interiors",
                    "Luxury bridal suites",
                    "Car parking 300+",
                    "LED-lit stage",
                    "Multiple dining halls",
                    "Generator backup",
                    "Wheelchair friendly",
                    "Nearby hotels",
                    "Custom décor",
                    "Security services",
                    "CCTV monitoring",
                    "Professional catering",
                    "VIP lounge",
                    "Premium sound & light"
                ),
                R.drawable.images,
                city = "Trichy",
                maxCapacity = 2000
            ),

            // ---------------- Salem ----------------
            Hall(
                "Salem Heritage Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹55,000",
                listOf(
                    " Location: 1313 Mill Street SE, Suite 200, Salem",
                    "Contact: +91 676356757",
                    "Capacity 420 guests",
                    "Located centrally",
                    "Traditional interiors",
                    "Dining for 180",
                    "Car parking 60+",
                    "Stage with lights",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Bridal suite",
                    "Housekeeping",
                    "Nearby hotels",
                    "Affordable packages",
                    "Custom décor allowed",
                    "Security services",
                    "Professional staff"
                ),
                R.drawable.mad1,
                city = "Salem",
                maxCapacity = 500
            ),
            Hall(
                "Salem Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,10,000",
                listOf(
                    "Location: Salem, Tamil Nadu",
                    "Contact: +91 427 400 0000",
                    "Capacity 800 guests",
                    "Premium interiors",
                    "Stage with LED wall",
                    "Dining 400 capacity",
                    "Car parking 120+",
                    "Separate kitchens",
                    "Central AC hall",
                    "Bridal room",
                    "Wheelchair access",
                    "Backup power",
                    "Nearby bus stand",
                    "Housekeeping team",
                    "Custom décor",
                    "Security & CCTV",
                    "Professional staff"
                ),
                R.drawable.sal2,
                city = "Salem",
                maxCapacity = 1000
            ),
            Hall(
                "Salem Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹1,90,000",
                listOf(
                    "Location: Near Saradha College, Convent Road, Salem-16",
                    "Contact: +91 90926 92111",
                    "Capacity 1300 guests",
                    "Luxury interiors",
                    "LED-lit stage",
                    "Dining 700 capacity",
                    "Car parking 250+",
                    "Bridal & groom suites",
                    "Wheelchair access",
                    "Backup power",
                    "Security services",
                    "CCTV monitoring",
                    "Nearby hotels",
                    "Custom décor allowed",
                    "Housekeeping staff",
                    "Professional catering tie-ups",
                    "Modern lighting"
                ),
                R.drawable.sal3,
                city = "Salem",
                maxCapacity = 2000
            ),Hall(
                "ERode Heritage Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹55,000",
                listOf(
                    "Location: 382, EVN Road, Near Railway Station, Erode - 638001",
                    " Contact: +91 424 222 0000 / +91 9843 770 000",
                    "Capacity 420 guests",
                    "Located centrally",
                    "Traditional interiors",
                    "Dining for 180",
                    "Car parking 60+",
                    "Stage with lights",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Bridal suite",
                    "Housekeeping",
                    "Nearby hotels",
                    "Affordable packages",
                    "Custom décor allowed",
                    "Security services",
                    "Professional staff"
                ),
                R.drawable.chennai1,
                city = "Erode",
                maxCapacity = 500
            ),
            Hall(
                "Erode Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,10,000",
                listOf(
                    "Location: Karur Main Road, Solar, Erode",
                    " Contact: +91 81237 49744",
                    "Capacity 800 guests",
                    "Premium interiors",
                    "Stage with LED wall",
                    "Dining 400 capacity",
                    "Car parking 120+",
                    "Separate kitchens",
                    "Central AC hall",
                    "Bridal room",
                    "Wheelchair access",
                    "Backup power",
                    "Nearby bus stand",
                    "Housekeeping team",
                    "Custom décor",
                    "Security & CCTV",
                    "Professional staff"
                ),
                R.drawable.erode2,
                city = "Erode",
                maxCapacity = 1000
            ),
            Hall(
                "Erode Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹1,90,000",
                listOf(
                    "Location: 304 Bangalore Road, Karungalpalayam, Erode – 638003",
                    "Contact: +91 9999 724 242",
                    "Capacity 1300 guests",
                    "Luxury interiors",
                    "LED-lit stage",
                    "Dining 700 capacity",
                    "Car parking 250+",
                    "Bridal & groom suites",
                    "Wheelchair access",
                    "Backup power",
                    "Security services",
                    "CCTV monitoring",
                    "Nearby hotels",
                    "Custom décor allowed",
                    "Housekeeping staff",
                    "Professional catering tie-ups",
                    "Modern lighting"
                ),
                R.drawable.erode3,
                city = "Salem",
                maxCapacity = 2000
            ),
            Hall(
                "Vellore Heritage Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹55,000",
                listOf(
                    "Location: Officers Line, Krishna Nagar, Vellore",
                    "Contact: +91 94433 12345",
                    "Capacity 420 guests",
                    "Located centrally",
                    "Traditional interiors",
                    "Dining for 180",
                    "Car parking 60+",
                    "Stage with lights",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Bridal suite",
                    "Housekeeping",
                    "Nearby hotels",
                    "Affordable packages",
                    "Custom décor allowed",
                    "Security services",
                    "Professional staff"
                ),
                R.drawable.vel1,
                city = "Vellore",
                maxCapacity = 500
            ),
            Hall(
                "Vellore Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,10,000",
                listOf(
                    " Location: No 4E, Arcot Main Road, Alamelumangapuram, Vellore – 632009",
                    "Contact: +91 9999 724 242",
                    "Capacity 800 guests",
                    "Premium interiors",
                    "Stage with LED wall",
                    "Dining 400 capacity",
                    "Car parking 120+",
                    "Separate kitchens",
                    "Central AC hall",
                    "Bridal room",
                    "Wheelchair access",
                    "Backup power",
                    "Nearby bus stand",
                    "Housekeeping team",
                    "Custom décor",
                    "Security & CCTV",
                    "Professional staff"
                ),
                R.drawable.vel2,
                city = "Vellore",
                maxCapacity = 1000
            ),
            Hall(
                "Vellore Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹1,90,000",
                listOf(
                    " Location: Bangalore Road, Vellore Ho, Vellore – 632001 (Konavattam)",
                    "Contact: +91 9999 724 242",
                    "Capacity 1300 guests",
                    "Luxury interiors",
                    "LED-lit stage",
                    "Dining 700 capacity",
                    "Car parking 250+",
                    "Bridal & groom suites",
                    "Wheelchair access",
                    "Backup power",
                    "Security services",
                    "CCTV monitoring",
                    "Nearby hotels",
                    "Custom décor allowed",
                    "Housekeeping staff",
                    "Professional catering tie-ups",
                    "Modern lighting"
                ),
                R.drawable.vel3,
                city = "Vellore",
                maxCapacity = 2000
            ),Hall(
                "Thirunelveli Heritage Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹55,000",
                listOf(
                    " Location: Near New Bus Stand, Tirunelveli",
                    "Contact: +91 9999 724 242",
                    "Capacity 420 guests",
                    "Located centrally",
                    "Traditional interiors",
                    "Dining for 180",
                    "Car parking 60+",
                    "Stage with lights",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Bridal suite",
                    "Housekeeping",
                    "Nearby hotels",
                    "Affordable packages",
                    "Custom décor allowed",
                    "Security services",
                    "Professional staff"
                ),
                R.drawable.tiru1,
                city = "Tirunelveli",
                maxCapacity = 500
            ),
            Hall(
                "Tirunelveli Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,10,000",
                listOf(
                    "Location: Near New Bus Stand, Tirunelveli",
                    " Contact: +91 75488 7051",
                    "Capacity 800 guests",
                    "Premium interiors",
                    "Stage with LED wall",
                    "Dining 400 capacity",
                    "Car parking 120+",
                    "Separate kitchens",
                    "Central AC hall",
                    "Bridal room",
                    "Wheelchair access",
                    "Backup power",
                    "Nearby bus stand",
                    "Housekeeping team",
                    "Custom décor",
                    "Security & CCTV",
                    "Professional staff"
                ),
                R.drawable.tiru2,
                city = "Tirunelveli",
                maxCapacity = 1000
            ),
            Hall(
                "Thirunelveli Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹1,90,000",
                listOf(
                    " Location: 210, Madurai Road, Shankar Nagar, Tirunelveli – 627357",
                    "Contact: +91 9999 724 242",
                    "Capacity 1300 guests",
                    "Luxury interiors",
                    "LED-lit stage",
                    "Dining 700 capacity",
                    "Car parking 250+",
                    "Bridal & groom suites",
                    "Wheelchair access",
                    "Backup power",
                    "Security services",
                    "CCTV monitoring",
                    "Nearby hotels",
                    "Custom décor allowed",
                    "Housekeeping staff",
                    "Professional catering tie-ups",
                    "Modern lighting"
                ),
                R.drawable.tiru3,
                city = "Tirunelveli",
                maxCapacity = 2000
            ),Hall(
                "Tuticorin Heritage Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹55,000",
                listOf(
                    " Location: No. 11, Near Sivan Kovil Street, Alagesapuram, Thoothukudi – 628002",
                    "Contact: +91 98941 40607",
                    "Capacity 420 guests",
                    "Located centrally",
                    "Traditional interiors",
                    "Dining for 180",
                    "Car parking 60+",
                    "Stage with lights",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Bridal suite",
                    "Housekeeping",
                    "Nearby hotels",
                    "Affordable packages",
                    "Custom décor allowed",
                    "Security services",
                    "Professional staff"
                ),
                R.drawable.tut1,
                city = "Tuticorin",
                maxCapacity = 500
            ),
            Hall(
                "Tuticorin Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,10,000",
                listOf(
                    "Location:Tuticorin,Tamil Nadu",
                    "Conatct: +91 9876543235",
                    "Capacity 800 guests",
                    "Premium interiors",
                    "Stage with LED wall",
                    "Dining 400 capacity",
                    "Car parking 120+",
                    "Separate kitchens",
                    "Central AC hall",
                    "Bridal room",
                    "Wheelchair access",
                    "Backup power",
                    "Nearby bus stand",
                    "Housekeeping team",
                    "Custom décor",
                    "Security & CCTV",
                    "Professional staff"
                ),
                R.drawable.tut2,
                city = "Tuticorin",
                maxCapacity = 1000
            ),
            Hall(
                "Tuticorin Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹1,90,000",
                listOf(
                    "Location:Tuticotin,Tamil Nadu",
                    "Contact:+91 7836839397",
                    "Capacity 1300 guests",
                    "Luxury interiors",
                    "LED-lit stage",
                    "Dining 700 capacity",
                    "Car parking 250+",
                    "Bridal & groom suites",
                    "Wheelchair access",
                    "Backup power",
                    "Security services",
                    "CCTV monitoring",
                    "Nearby hotels",
                    "Custom décor allowed",
                    "Housekeeping staff",
                    "Professional catering tie-ups",
                    "Modern lighting"
                ),
                R.drawable.tut3,
                city = "Tuticorin",
                maxCapacity = 2000
            ),
            Hall(
                "NAgercoil Heritage Hall (Below 500)",
                "⭐ 4.4",
                "Price: ₹55,000",
                listOf(
                    " Location: Vadasery, Nagercoil",
                    "Contact:+91 9089876543",
                    "Capacity 420 guests",
                    "Located centrally",
                    "Traditional interiors",
                    "Dining for 180",
                    "Car parking 60+",
                    "Stage with lights",
                    "Backup generator",
                    "Wheelchair accessible",
                    "Bridal suite",
                    "Housekeeping",
                    "Nearby hotels",
                    "Affordable packages",
                    "Custom décor allowed",
                    "Security services",
                    "Professional staff"
                ),
                R.drawable.tiru1,
                city = "Nagercoil",
                maxCapacity = 500
            ),
            Hall(
                "NAgercoil Grand Mahal (500–1000)",
                "⭐ 4.6",
                "Price: ₹1,10,000",
                listOf(
                    "Location: Agastheeswaram, Nagercoil, Tamil Nadu",
                    "Contact:+91 7873936482",
                    "Capacity 800 guests",
                    "Premium interiors",
                    "Stage with LED wall",
                    "Dining 400 capacity",
                    "Car parking 120+",
                    "Separate kitchens",
                    "Central AC hall",
                    "Bridal room",
                    "Wheelchair access",
                    "Backup power",
                    "Nearby bus stand",
                    "Housekeeping team",
                    "Custom décor",
                    "Security & CCTV",
                    "Professional staff"
                ),
                R.drawable.nag2,
                city = "Nagercoil",
                maxCapacity = 1000
            ),
            Hall(
                "Nagercoil Royal Palace (Above 1000)",
                "⭐ 4.7",
                "Price: ₹1,90,000",
                listOf(
                    "Location: Nagercoil, Tamil Nadu",
                    "Contact:+91 9898986547",
                    "Capacity 1300 guests",
                    "Luxury interiors",
                    "LED-lit stage",
                    "Dining 700 capacity",
                    "Car parking 250+",
                    "Bridal & groom suites",
                    "Wheelchair access",
                    "Backup power",
                    "Security services",
                    "CCTV monitoring",
                    "Nearby hotels",
                    "Custom décor allowed",
                    "Housekeeping staff",
                    "Professional catering tie-ups",
                    "Modern lighting"
                ),
                R.drawable.chennaai3,
                city = "Nagercoil",
                maxCapacity = 2000
            )

            // Add other cities and halls similarly
        )

        // Filter halls by city AND appropriate guest count range
        val filteredHalls = halls.filter {
            it.city.equals(city, ignoreCase = true) && guestCount <= it.maxCapacity
        }

        // Show all matching halls
        filteredHalls.forEach {  hall ->
            val card = CardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(16, 16, 16, 16) }
                radius = 16f
                cardElevation = 8f
                setContentPadding(16, 16, 16, 16)
                setOnClickListener {
                    // Go to BookingActivity
                    val intent = android.content.Intent(this@VenueActivity, VenueBookingActivity::class.java)
                    intent.putExtra("hallName", hall.name)
                    intent.putExtra("hallPrice", hall.price)
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

            val hallImage = ImageView(this).apply {
                setImageResource(hall.imageRes)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 400
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            val hallName = TextView(this).apply {
                text = hall.name
                textSize = 20f
                setTextColor(Color.BLACK)
                setPadding(0, 12, 0, 0)
            }

            val hallRating = TextView(this).apply {
                text = hall.rating
                setPadding(0, 4, 0, 0)
            }

            val hallPrice = TextView(this).apply {
                text = hall.price
                setPadding(0, 4, 0, 0)
            }

            val hallDescription = TextView(this).apply {
                text = hall.description.joinToString("\n• ", prefix = "• ")
                setPadding(0, 8, 0, 0)
            }

            linearLayout.apply {
                addView(hallImage)
                addView(hallName)
                addView(hallRating)
                addView(hallPrice)
                addView(hallDescription)
            }
            // Make the card clickable to go to booking page
            card.setOnClickListener {
                val intent = Intent(this, VenueBookingActivity::class.java)
                intent.putExtra("hallName", hall.name)

                // Extract price as number from hall.price string (e.g., "Price: ₹80,000")
                val priceString = hall.price.replace("Price: ₹", "").replace(",", "").trim()
                val hallPrice = priceString.toDoubleOrNull() ?: 0.0

                intent.putExtra("hallPrice", hallPrice)
                startActivity(intent)
            }


            card.addView(linearLayout)
            container.addView(card)
        }

        // Show message if no halls match
        if (filteredHalls.isEmpty()) {
            val noHallText = TextView(this).apply {
                text = "No halls available for $guestCount guests in $city."
                textSize = 18f
                setTextColor(Color.RED)
                setPadding(16, 16, 16, 16)
            }
            container.addView(noHallText)
        }
    }
}
