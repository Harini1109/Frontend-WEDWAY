<?php
header('Content-Type: application/json');

// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    echo json_encode(["status" => "error", "message" => "Database connection failed"]);
    exit;
}

// Fetch all vendor requests
$sql = "SELECT shop_name, mobile_number, location, address, email, shop_category FROM shop_requests";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $vendors = [];

    while ($row = $result->fetch_assoc()) {
        $vendors[] = [
            "shop_name" => $row['shop_name'],
            "mobile_number" => $row['mobile_number'],
            "location" => $row['location'],
            "address" => $row['address'],
            "email" => $row['email'],
            "shop_category" => $row['shop_category']
        ];
    }

    echo json_encode(["status" => "success", "data" => $vendors]);
} else {
    echo json_encode(["status" => "success", "data" => [], "message" => "No requests found"]);
}

$conn->close();
?>
