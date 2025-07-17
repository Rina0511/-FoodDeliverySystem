<?php
include 'db_config.php';

$data = json_decode(file_get_contents("php://input"));
$order_id = $data->order_id;
$rider_id = $data->rider_id;

$sql = "UPDATE orders 
        SET delivery_status = 'assigned', assigned_rider = ? 
        WHERE id = ? AND delivery_status = 'pending'";


$stmt = $conn->prepare($sql);
$stmt->bind_param("ii", $rider_id, $order_id);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "failed", "reason" => "Could not assign order"]);
}
?>
