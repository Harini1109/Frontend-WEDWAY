<?php
header("Content-Type: application/json");

// Database credentials
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

// Connect to MySQL
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Get JSON input
$data = json_decode(file_get_contents("php://input"), true);

// Validate required fields
$required_fields = ['name', 'shop_name', 'location', 'mobile_number', 'services', 'cuisines', 'min_package', 'max_package', 'password', 'confirm_password'];
foreach ($required_fields as $field) {
    if (empty($data[$field])) {
        echo json_encode(["status" => "error", "message" => "Missing field: $field"]);
        exit;
    }
}

// Password match check
if ($data['password'] !== $data['confirm_password']) {
    echo json_encode(["status" => "error", "message" => "Passwords do not match."]);
    exit;
}

// Hash password
$hashedPassword = password_hash($data['password'], PASSWORD_BCRYPT);

// Prepare and bind
$sql = "INSERT INTO catering_signup (name, shop_name, location, mobile_number, services, cuisines, min_package, max_package, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
$stmt = $conn->prepare($sql);
$stmt->bind_param(
    "ssssssdds",
    $data['name'],
    $data['shop_name'],
    $data['location'],
    $data['mobile_number'],
    $data['services'],
    $data['cuisines'],
    $data['min_package'],
    $data['max_package'],
    $hashedPassword
);

// Execute
if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Vendor registerd successfully."]);
} else {
    echo json_encode(["status" => "error", "message" => "Signup failed."]);
}

// Close connections
$stmt->close();
$conn->close();
?>
