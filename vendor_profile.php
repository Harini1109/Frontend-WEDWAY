<?php
header('Content-Type: application/json');

// DB connection
$servername = "localhost";
$username = "root";
$password = "";
$database = "wedway";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_error) {
    die(json_encode([
        "status" => "error",
        "message" => "Database connection failed: " . $conn->connect_error
    ]));
}

// Get vendor_id from GET or POST
$vendor_id = isset($_GET['vendor_id']) ? intval($_GET['vendor_id']) : 0;

if ($vendor_id <= 0) {
    echo json_encode([
        "status" => "error",
        "message" => "Invalid or missing vendor ID"
    ]);
    exit;
}

// Fetch vendor details
$sql = "SELECT name, mobile_number, shop_name FROM venue_signup WHERE vendor_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $vendor_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $vendor = $result->fetch_assoc();

    echo json_encode([
        "status" => "success",
        "vendor_profile" => [
            "name" => $vendor['name'],
            "mobile_number" => $vendor['mobile_number'],
            "shop_name" => $vendor['shop_name'],
            "shop_category" => "Venue" // static for venue_signup
        ]
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Vendor not found."
    ]);
}

$conn->close();
?>
