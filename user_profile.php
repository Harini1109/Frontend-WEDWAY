<?php
header("Content-Type: application/json");

// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Get user_id (e.g., from GET or session — here hardcoded for example)
$user_id = isset($_GET['user_id']) ? intval($_GET['user_id']) : 1;

// Fetch profile details
$sql = "SELECT first_name, last_name, mobile_number, gender FROM users WHERE user_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();

    // Mask phone number like 638xxxxx37
    $mobile = $row['mobile_number'];
    $masked_mobile = substr($mobile, 0, 3) . "xxxxx" . substr($mobile, -2);

    $response = [
        "status" => "success",
        "data" => [
            "name" => $row['first_name'] . " " . $row['last_name'],
            "mobile_number" => $masked_mobile,
            "gender" => $row['gender']
        ]
    ];
    echo json_encode($response);
} else {
    echo json_encode(["status" => "error", "message" => "User not found."]);
}

$stmt->close();
$conn->close();
?>
