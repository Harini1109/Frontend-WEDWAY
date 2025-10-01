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

// Read input
$data = json_decode(file_get_contents("php://input"), true);

// Validate
if (empty($data['email']) || empty($data['password'])) {
    echo json_encode(["status" => "error", "message" => "Email and password are required."]);
    exit;
}

$email = $conn->real_escape_string($data['email']);
$password = $data['password'];

// Fetch user by email
$stmt = $conn->prepare("SELECT user_id, first_name, last_name, password FROM users WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 1) {
    $user = $result->fetch_assoc();

    if (password_verify($password, $user['password'])) {
        // Success
        echo json_encode([
            "status" => "success",
            "message" => "Login successful.",
            "user" => [
                "user_id" => $user['user_id'],
                "first_name" => $user['first_name'],
                "last_name" => $user['last_name'],
                "email" => $email
            ]
        ]);
    } else {
        // Wrong password
        echo json_encode(["status" => "error", "message" => "Invalid password."]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "User not found."]);
}

$stmt->close();
$conn->close();
?>
