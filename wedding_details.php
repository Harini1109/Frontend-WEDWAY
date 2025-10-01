<?php
// Include database connection
include 'dbcon.php'; // make sure this file has your $conn = new mysqli(...) connection code

// Get JSON input from frontend (if using API) or POST data (if form)
$input = file_get_contents("php://input");
$data = json_decode($input, true);

// If receiving normal HTML form POST data, use: 
// $bride_name = $_POST['bride_name'];
// $groom_name = $_POST['groom_name'];
// $wedding_date = $_POST['wedding_date'];
// $wedding_time = $_POST['wedding_time'];

if (isset($data['bride_name'], $data['groom_name'], $data['wedding_date'], $data['wedding_time'])) {
    
    $bride_name = $data['bride_name'];
    $groom_name = $data['groom_name'];
    $wedding_date = $data['wedding_date'];
    $wedding_time = $data['wedding_time'];

    // Prepare and bind
    $stmt = $conn->prepare("INSERT INTO wedding_details (bride_name, groom_name, wedding_date, wedding_time) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $bride_name, $groom_name, $wedding_date, $wedding_time);

    if ($stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "Details saved successfully"]);
    } else {
        echo json_encode(["status" => "error", "message" => $stmt->error]);
    }

    $stmt->close();
} else {
    echo json_encode(["status" => "error", "message" => "Required fields missing"]);
}

$conn->close();
?>
