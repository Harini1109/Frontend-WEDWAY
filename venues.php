<?php
header("Content-Type: application/json");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Connection failed."]));
}

$vendor_id = isset($_GET['vendor_id']) ? intval($_GET['vendor_id']) : 0;

if ($vendor_id <= 0) {
    echo json_encode(["status" => "error", "message" => "Invalid vendor ID."]);
    exit;
}

$sql = "SELECT * FROM venue_signup WHERE vendor_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $vendor_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    echo json_encode(["status" => "error", "message" => "Venue not found."]);
} else {
    $venue = $result->fetch_assoc();

    // Default image URL
    $default_image_url = "c:\Users\harin\OneDrive\Desktop\pdd\hall.png"; // replace with your actual image URL or local path

    $response = [
        "status" => "success",
        "data" => [
            "venue_name" => $venue['shop_name'],
            "location" => $venue['location'],
            "capacity" => $venue['capacity'],
            "space" => $venue['space'],
            "parking" => $venue['parking'],
            "amenities" => explode(",", $venue['amenities']),
            "min_package" => $venue['min_package'],
            "max_package" => $venue['max_package'],
            "image_url" => $default_image_url
        ]
    ];

    echo json_encode($response);
}

$stmt->close();
$conn->close();
?>
