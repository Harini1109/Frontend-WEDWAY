<?php
header("Content-Type: application/json");

// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway"; // Your DB name

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Read JSON input
$data = json_decode(file_get_contents("php://input"), true);

// Required fields
$required = ['name', 'shop_name', 'location', 'mobile_number', 'capacity', 'space', 'parking', 'amenities', 'min_package', 'max_package', 'password', 'confirm_password'];

foreach ($required as $field) {
    if (empty($data[$field])) {
        echo json_encode(["status" => "error", "message" => "$field is required."]);
        exit;
    }
}

// Password confirmation
if ($data['password'] !== $data['confirm_password']) {
    echo json_encode(["status" => "error", "message" => "Passwords do not match."]);
    exit;
}

// Sanitize and hash
$name = $conn->real_escape_string($data['name']);
$shop_name = $conn->real_escape_string($data['shop_name']);
$location = $conn->real_escape_string($data['location']);
$mobile_number = $conn->real_escape_string($data['mobile_number']);
$capacity = intval($data['capacity']);
$space = $conn->real_escape_string($data['space']);
$parking = $conn->real_escape_string($data['parking']);
$amenities = $conn->real_escape_string($data['amenities']);
$min_package = floatval($data['min_package']);
$max_package = floatval($data['max_package']);
$hashed_password = password_hash($data['password'], PASSWORD_DEFAULT);

// Insert into table
$stmt = $conn->prepare("INSERT INTO venue_signup 
    (name, shop_name, location, mobile_number, capacity, space, parking, amenities, min_package, max_package, password) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

$stmt->bind_param("ssssisssdds", $name, $shop_name, $location, $mobile_number, $capacity, $space, $parking, $amenities, $min_package, $max_package, $hashed_password);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Venue registered successfully."]);
} else {
    echo json_encode(["status" => "error", "message" => "Registration failed."]);
}

$stmt->close();
$conn->close();
?>
