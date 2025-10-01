<?php
header("Content-Type: application/json");

// DB connection
$host = "localhost";
$username = "root";
$password = "";
$database = "wedway"; // change to your actual DB name

$conn = new mysqli($host, $username, $password, $database);

if ($conn->connect_error) {
    echo json_encode(["status" => "error", "message" => "Database connection failed."]);
    exit;
}

// Read and decode raw JSON input
$input = json_decode(file_get_contents("php://input"), true);

$VendorName = $input['VendorName'] ?? '';
$Location = $input['Location'] ?? '';
$Rating = $input['Rating'] ?? '';
$ReviewCount = $input['ReviewCount'] ?? '';
$WeddingDate = $input['WeddingDate'] ?? '';
$StartTime = $input['StartTime'] ?? '';
$EndTime = $input['EndTime'] ?? '';
$Mobile = $input['Mobile'] ?? '';
$Package = $input['Package'] ?? '';

// Validate required fields
if (empty($VendorName) || empty($Location) || empty($WeddingDate) || empty($StartTime) || empty($EndTime) || empty($Mobile)) {
    echo json_encode(["status" => "error", "message" => "Missing one or more required fields."]);
    exit;
}

// Insert into database
$stmt = $conn->prepare("INSERT INTO CateringBookings (VendorName, Location, Rating, ReviewCount, WeddingDate, StartTime, EndTime, Mobile, Package) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
$stmt->bind_param("ssdiissss", $VendorName, $Location, $Rating, $ReviewCount, $WeddingDate, $StartTime, $EndTime, $Mobile, $Package);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Booking confirmed."]);
} else {
    echo json_encode(["status" => "error", "message" => "Database error: " . $stmt->error]);
}

$stmt->close();
$conn->close();
?>
