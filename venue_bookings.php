<?php
// Include database connection
include 'dbcon.php';

// Read JSON input
$input = file_get_contents("php://input");
$data = json_decode($input, true);

// Validate required fields
if (
    isset($data['hall_name']) && isset($data['location']) &&
    isset($data['wedding_date']) && isset($data['start_time']) &&
    isset($data['end_time']) && isset($data['bride_name']) &&
    isset($data['groom_name']) && isset($data['package'])
) {
    $hall_name = $data['hall_name'];
    $location = $data['location'];
    $wedding_date = $data['wedding_date'];
    $start_time = $data['start_time'];
    $end_time = $data['end_time'];
    $bride_name = $data['bride_name'];
    $groom_name = $data['groom_name'];
    $package = $data['package'];

    // Prepare SQL insert
    $stmt = $conn->prepare("INSERT INTO venue_bookings 
        (hall_name, location, wedding_date, start_time, end_time, bride_name, groom_name, package) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssssss", $hall_name, $location, $wedding_date, $start_time, $end_time, $bride_name, $groom_name, $package);

    if ($stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "Booking confirmed successfully"]);
    } else {
        echo json_encode(["status" => "error", "message" => $stmt->error]);
    }

    $stmt->close();
} else {
    echo json_encode(["status" => "error", "message" => "Required fields missing"]);
}

$conn->close();
?>
