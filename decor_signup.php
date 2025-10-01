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

// Read and decode JSON input
$input = json_decode(file_get_contents("php://input"), true);

// If JSON is not parsed correctly
if (!is_array($input)) {
    echo json_encode([
        "status" => "error",
        "message" => "Invalid or missing JSON input"
    ]);
    exit;
}

// Required fields
$required_fields = ['name', 'shop_name', 'location', 'mobile_number', 'services', 'package', 'password', 'confirm_password'];

foreach ($required_fields as $field) {
    if (!isset($input[$field]) || trim($input[$field]) === '') {
        echo json_encode([
            "status" => "error",
            "message" => "Missing or empty field: $field"
        ]);
        exit;
    }
}

// Assign variables
$name = trim($input['name']);
$shop_name = trim($input['shop_name']);
$location = trim($input['location']);
$mobile_number = trim($input['mobile_number']);
$services = trim($input['services']);
$package = trim($input['package']);
$password = $input['password'];
$confirm_password = $input['confirm_password'];

// Check if passwords match
if ($password !== $confirm_password) {
    echo json_encode([
        "status" => "error",
        "message" => "Passwords do not match"
    ]);
    exit;
}

// Hash the password
$hashed_password = password_hash($password, PASSWORD_DEFAULT);

// Insert into database
$sql = "INSERT INTO decor_signup (name, shop_name, location, mobile_number, services, package, password) 
        VALUES (?, ?, ?, ?, ?, ?, ?)";
$stmt = $conn->prepare($sql);
$stmt->bind_param("sssssss", $name, $shop_name, $location, $mobile_number, $services, $package, $hashed_password);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Signup successful",
        "vendor_id" => $stmt->insert_id
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Signup failed: " . $stmt->error
    ]);
}

$conn->close();
?>
