<?php
header("Content-Type: application/json");

// Database connection
$host = "localhost";
$username = "root";
$password = "";
$database = "wedway"; // Change this to your actual DB name

$conn = new mysqli($host, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    echo json_encode([
        "status" => "error",
        "message" => "Database connection failed: " . $conn->connect_error
    ]);
    exit;
}

// Read and decode JSON input
$input = json_decode(file_get_contents("php://input"), true);

// Extract values from JSON
$VendorName = $input['VendorName'] ?? '';
$Location = $input['Location'] ?? '';
$Rating = $input['Rating'] ?? '';
$ReviewCount = $input['ReviewCount'] ?? '';
$WeddingDate = $input['WeddingDate'] ?? '';
$StartTime = $input['StartTime'] ?? '';
$EndTime = $input['EndTime'] ?? '';
$Mobile = $input['Mobile'] ?? '';
$Venue = $input['Venue'] ?? '';
$BrideName = $input['BrideName'] ?? '';
$GroomName = $input['GroomName'] ?? '';

// Validate required fields
if (
    empty($VendorName) || empty($Location) || empty($WeddingDate) || empty($StartTime) ||
    empty($EndTime) || empty($Mobile) || empty($Venue) || empty($BrideName) || empty($GroomName)
) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing one or more required fields."
    ]);
    exit;
}

// Prepare and execute SQL insert
$stmt = $conn->prepare("INSERT INTO DecorBookings 
    (VendorName, Location, Rating, ReviewCount, WeddingDate, StartTime, EndTime, Mobile, Venue, BrideName, GroomName) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

$stmt->bind_param("ssdiissssss", 
    $VendorName, $Location, $Rating, $ReviewCount, $WeddingDate,
    $StartTime, $EndTime, $Mobile, $Venue, $BrideName, $GroomName
);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Decor booking confirmed successfully."
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Database error: " . $stmt->error
    ]);
}

$stmt->close();
$conn->close();
?>
