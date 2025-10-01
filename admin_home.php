<?php
header("Content-Type: application/json");

// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check DB connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Simulated admin_id – replace with session or actual request parameter
$admin_id = 1;

// Prepare SQL query (using first_name and last_name)
$sql = "SELECT first_name, last_name, email, mobile_number FROM admin_signup WHERE admin_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $admin_id);
$stmt->execute();

$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();

    $response = [
        "status" => "success",
        "data" => [
            "full_name" => $row["first_name"] . " " . $row["last_name"],
            "email" => $row["email"],
            "mobile_number" => $row["mobile_number"]
        ]
    ];
    echo json_encode($response);
} else {
    echo json_encode(["status" => "error", "message" => "Admin not found."]);
}

$stmt->close();
$conn->close();
?>
