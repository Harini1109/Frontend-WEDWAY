<?php
header("Content-Type: application/json");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wedway";

// Connect to database
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Database connection failed."]));
}

// Fetch all caterers
$sql = "SELECT id, name, shop_name, location, services, cuisines, min_package, max_package FROM catering_signup";
$result = $conn->query($sql);

$caterers = [];

$default_image_url = "C:\Users\harin\OneDrive\Desktop\pdd\caters.png"; // Change this to your server path

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $caterers[] = [
            "name" => $row["name"],
            "shop_name" => $row["shop_name"],
            "location" => $row["location"],
            "services" => explode(",", $row["services"]),
            "cuisines" => explode(",", $row["cuisines"]),
            "min_package" => $row["min_package"],
            "max_package" => $row["max_package"],
            "image" => $default_image_url
        ];
    }

    echo json_encode(["status" => "success", "data" => $caterers]);
} else {
    echo json_encode(["status" => "error", "message" => "No catering vendors found."]);
}

$conn->close();
?>
