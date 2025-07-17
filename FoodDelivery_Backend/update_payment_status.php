<?php
include 'db_config.php';

$data = json_decode(file_get_contents("php://input"));
$order_id = $data->order_id;
$new_payment_status = "Paid"; // You can also accept from $data if dynamic

$sql = "UPDATE orders SET payment_status = ? WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("si", $new_payment_status, $order_id);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo json_encode(["payment_status" => "success"]);
} else {
    echo json_encode(["payment_status" => "failed"]);
}
?>
