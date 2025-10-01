<?php 
header("Content-Type: application/json"); 
 
// Database connection 
$servername = "localhost"; 
$username   = "root"; // your DB username 
$password   = "";     // your DB password 
$dbname     = "wedway"; // change to your DB name 
 
$conn = new mysqli($servername, $username, $password, $dbname); 
 
// Check connection 
if ($conn->connect_error) { 
    echo json_encode(["status" => "error", "message" => $conn->connect_error]); 
    exit; 
} 
 
// Get booking_id from URL: example get_booking.php?booking_id=1 
$booking_id = $_GET['booking_id'] ?? ''; 
 
if (empty($booking_id)) { 
    echo json_encode(["status" => "error", "message" => "No booking ID provided"]); 
    exit; 
} 
 
// Fetch booking details (venue_name changed to hall_name)
$sql = "SELECT booking_id, hall_name, location, wedding_date,  
               start_time, end_time, bride_name, groom_name, package, created_at 
        FROM venue_bookings 
        WHERE booking_id = ?"; 
$stmt = $conn->prepare($sql); 
$stmt->bind_param("i", $booking_id); 
$stmt->execute(); 
$result = $stmt->get_result(); 
 
if ($result->num_rows > 0) { 
    $row = $result->fetch_assoc(); 
    echo json_encode(["status" => "success", "data" => $row]); 
} else { 
    echo json_encode(["status" => "error", "message" => "No booking found"]); 
} 
 
$stmt->close(); 
$conn->close(); 
?>
