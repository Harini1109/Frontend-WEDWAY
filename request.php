<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// DB connection
$servername = "localhost";
$username   = "root";
$password   = "";
$dbname     = "wedway"; // change this

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => $conn->connect_error]));
}

// Read JSON
$input = file_get_contents("php://input");
$data  = json_decode($input, true);

if (!$data) {
    echo json_encode(["status" => "error", "message" => "Invalid JSON"]);
    exit;
}

// Assign values
$shop_name     = $data['shop_name'] ?? '';
$owner_name    = $data['owner_name'] ?? '';
$mobile_number = $data['mobile_number'] ?? '';
$location      = $data['location'] ?? '';
$address       = $data['address'] ?? '';
$email         = $data['email'] ?? '';
$shop_category = $data['shop_category'] ?? '';

// Insert
$stmt = $conn->prepare("
    INSERT INTO shop_requests (shop_name, owner_name, mobile_number, location, address, email, shop_category)
    VALUES (?, ?, ?, ?, ?, ?, ?)
");
$stmt->bind_param("sssssss", $shop_name, $owner_name, $mobile_number, $location, $address, $email, $shop_category);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Request submitted successfully"]);
} else {
    echo json_encode(["status" => "error", "message" => $stmt->error]);
}

$stmt->close();
$conn->close();
?>
