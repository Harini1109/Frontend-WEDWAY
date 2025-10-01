<?php
header("Content-Type: application/json");

// DB connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Read JSON input
$data = json_decode(file_get_contents("php://input"), true);

// Validate required fields
$required_fields = ['first_name', 'last_name', 'mobile_number', 'email', 'gender', 'password', 'confirm_password'];
foreach ($required_fields as $field) {
    if (empty($data[$field])) {
        echo json_encode(["status" => "error", "message" => "$field is required."]);
        exit;
    }
}

// Check if passwords match
if ($data['password'] !== $data['confirm_password']) {
    echo json_encode(["status" => "error", "message" => "Passwords do not match."]);
    exit;
}

// Sanitize and prepare data
$first_name = $conn->real_escape_string($data['first_name']);
$last_name = $conn->real_escape_string($data['last_name']);
$mobile = $conn->real_escape_string($data['mobile_number']);
$email = $conn->real_escape_string($data['email']);
$gender = $conn->real_escape_string($data['gender']);
$password_hash = password_hash($data['password'], PASSWORD_DEFAULT); // hash the password

// Check if email already exists
$check_email = $conn->prepare("SELECT user_id FROM users WHERE email = ?");
$check_email->bind_param("s", $email);
$check_email->execute();
$check_email->store_result();
if ($check_email->num_rows > 0) {
    echo json_encode(["status" => "error", "message" => "Email already exists."]);
    exit;
}

// Insert into database
$stmt = $conn->prepare("INSERT INTO users (first_name, last_name, mobile_number, email, gender, password) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param("ssssss", $first_name, $last_name, $mobile, $email, $gender, $password_hash);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Signup successful."]);
} else {
    echo json_encode(["status" => "error", "message" => "Signup failed."]);
}

$stmt->close();
$conn->close();
?>
