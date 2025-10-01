<?php
header("Content-Type: application/json");

// DB connection
$servername = "localhost";
$username = "root";
$password = "";
$database = "wedway";

$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Example: assuming you're fetching for a specific admin_id (e.g., from session)
$admin_id = 1;

// Use the actual column names from your admin_signup table
$sql = "SELECT first_name, last_name, mobile_number, gender FROM admin_signup WHERE admin_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $admin_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $admin = $result->fetch_assoc();

    // Combine first and last name
    $name = $admin['first_name'] . ' ' . $admin['last_name'];

    echo json_encode([
        "status" => "success",
        "data" => [
            "name" => $name,
            "mobile_number" => $admin['mobile_number'],
            "gender" => $admin['gender']
        ]
    ]);
} else {
    echo json_encode(["status" => "error", "message" => "Admin not found."]);
}

$stmt->close();
$conn->close();
?>
