<?php
header('Content-Type: application/json');
session_start();

$host = "localhost";
$username = "root";
$password = "";
$database = "wedway"; // update with your DB name

$conn = new mysqli($host, $username, $password, $database);
if ($conn->connect_error) {
    echo json_encode(["status" => "error", "message" => "Connection failed"]);
    exit;
}

// Get user_id from session or GET parameter
$user_id = $_SESSION['user_id'] ?? $_GET['user_id'] ?? 0;

if ($user_id == 0) {
    echo json_encode(["status" => "error", "message" => "User not found"]);
    exit;
}

// Fetch user info
$stmtUser = $conn->prepare("SELECT first_name FROM users WHERE user_id = ?");
$stmtUser->bind_param("i", $user_id);
$stmtUser->execute();
$userResult = $stmtUser->get_result();
$user = $userResult->fetch_assoc();
$firstName = $user['first_name'] ?? 'Client';

// Fetch wedding details
$stmtWed = $conn->prepare("SELECT bride_name, groom_name, wedding_date, wedding_time FROM wedding_details WHERE id = ?");
$stmtWed->bind_param("i", $user_id); // assuming wedding_details.id == user_id
$stmtWed->execute();
$wedResult = $stmtWed->get_result();
$wed = $wedResult->fetch_assoc();

$response = [
    "status" => "success",
    "welcome_message" => "Welcome, " . $firstName,
    "subtitle" => "Let's plan your day perfect",
    "banner" => [
        "title" => "Plan Your Perfect Day",
        "subtitle" => "Wedding planning made simple",
        "image_url" => "C:\Users\harin\Downloads\FRAME - 2025-08-04T195106.821 1.png" // placeholder, replace with actual URL
    ],
    "wedding_details" => [
        "bride_name" => $wed['bride_name'] ?? 'N/A',
        "groom_name" => $wed['groom_name'] ?? 'N/A',
        "wedding_date" => $wed['wedding_date'] ?? 'N/A',
        "wedding_time" => $wed['wedding_time'] ?? 'N/A'
    ],
    "options" => [
        "venues" => "Venues",
        "caterers" => "Caterers",
        "decors" => "Decors"
    ],
    "navigation" => [
        "home" => "Home",
        "vendors" => "Vendors",
        "profile" => "Profile",
        "chatbot" => "Chatbot"
    ]
];

echo json_encode($response, JSON_PRETTY_PRINT);
?>
