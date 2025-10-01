<?php
header('Content-Type: application/json');

// DB connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check DB connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Connection failed."]));
}

// Get JSON input
$data = json_decode(file_get_contents("php://input"), true);

$first_name = $data['first_name'] ?? '';
$last_name = $data['last_name'] ?? '';
$mobile_number = $data['mobile_number'] ?? '';
$email = $data['email'] ?? '';
$gender = $data['gender'] ?? '';
$password = $data['password'] ?? '';
$confirm_password = $data['confirm_password'] ?? '';

// Validate required fields
if (
    empty($first_name) || empty($last_name) || empty($mobile_number) || empty($email) ||
    empty($gender) || empty($password) || empty($confirm_password)
) {
    echo json_encode(["status" => "error", "message" => "All fields are required."]);
    exit;
}

// Check if passwords match
if ($password !== $confirm_password) {
    echo json_encode(["status" => "error", "message" => "Passwords do not match."]);
    exit;
}

// Check for existing email
$check_stmt = $conn->prepare("SELECT admin_id FROM admin_signup WHERE email = ?");
$check_stmt->bind_param("s", $email);
$check_stmt->execute();
$check_stmt->store_result();

if ($check_stmt->num_rows > 0) {
    echo json_encode(["status" => "error", "message" => "Email already registered."]);
    exit;
}

// Hash the password
$hashed_password = password_hash($password, PASSWORD_DEFAULT);

// Insert into admin_signup table
$insert_stmt = $conn->prepare("
    INSERT INTO admin_signup (first_name, last_name, mobile_number, email, gender, password)
    VALUES (?, ?, ?, ?, ?, ?)
");
$insert_stmt->bind_param("ssssss", $first_name, $last_name, $mobile_number, $email, $gender, $hashed_password);

if ($insert_stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Admin registered successfully."]);
} else {
    echo json_encode(["status" => "error", "message" => "Registration failed."]);
}
?>
