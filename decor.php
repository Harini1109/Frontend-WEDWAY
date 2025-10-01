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
        "message" => "Connection failed: " . $conn->connect_error
    ]));
}

if (!isset($_GET['id'])) {
    echo json_encode([
        "status" => "error",
        "message" => "ID is required"
    ]);
    exit;
}

$id = $_GET['id'];
$sql = "SELECT * FROM decor_signup WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();

    // Use a default image URL (can also be relative path if hosted locally)
    $default_image_url = "C:\Users\harin\OneDrive\Desktop\pdd\decorss.png"; // update this to actual hosted image path

    // Convert services (stored as CSV string) to array
    $services_array = explode(",", $row['services']);

    $response = [
        "status" => "success",
        "shop_name" => $row['shop_name'],
        "location" => $row['location'],
        "image_url" => $default_image_url,
        "rating" => 4.5,
        "reviews" => 243,
        "services" => $services_array,
        "pricing" => "₹75,000 onwards",
        "customizable_packages" => true
    ];

    echo json_encode($response);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Vendor not found"
    ]);
}

$conn->close();
?>
