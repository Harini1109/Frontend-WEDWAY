<?php
header("Content-Type: application/json");

// DB connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Get vendor_id and booking_id from URL
$vendor_id = $_GET['vendor_id'] ?? '';
$booking_id = $_GET['booking_id'] ?? '';

if (empty($vendor_id) || empty($booking_id)) {
    echo json_encode(["status" => "error", "message" => "Vendor ID and Booking ID are required."]);
    exit;
}

// Fetch vendor name from venue_signup
$vendor_stmt = $conn->prepare("SELECT name FROM venue_signup WHERE vendor_id = ?");
$vendor_stmt->bind_param("i", $vendor_id);
$vendor_stmt->execute();
$vendor_result = $vendor_stmt->get_result();

if ($vendor_result->num_rows == 0) {
    echo json_encode(["status" => "error", "message" => "Vendor not found."]);
    exit;
}

$vendor = $vendor_result->fetch_assoc();

// Fetch booking details using only booking_id
$booking_stmt = $conn->prepare("
    SELECT bride_name, groom_name, hall_name, wedding_date 
    FROM venue_bookings 
    WHERE booking_id = ?
");
$booking_stmt->bind_param("i", $booking_id);
$booking_stmt->execute();
$booking_result = $booking_stmt->get_result();

$bookings = [];

while ($row = $booking_result->fetch_assoc()) {
    $bookings[] = [
        "bride_and_groom" => $row['bride_name'] . " & " . $row['groom_name'],
        "venue" => $row['hall_name'],
        "wedding_date" => date("dS M Y", strtotime($row['wedding_date']))
    ];
}

// Final response
echo json_encode([
    "status" => "success",
    "vendor_name" => $vendor['name'],
    "bookings" => $bookings
]);
?>
