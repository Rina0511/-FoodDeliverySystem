<?php
include 'db_config.php';
$data = json_decode(file_get_contents("php://input"));

$customer_id = $data->customer_id;
$items = json_encode($data->items);
$total = $data->total;

// Set status to "Pending"
$status = "Pending";

$stmt = $conn->prepare("INSERT INTO orders (customer_id, items, total, payment_status) VALUES (?, ?, ?, ?)");
$stmt->bind_param("isds", $customer_id, $items, $total, $status);

if ($stmt->execute()) {
    $order_id = $conn->insert_id;
    echo json_encode(["payment_status" => "success", "order_id" => $order_id]);
} else {
    echo json_encode(["payment_status" => "error", "error" => $stmt->error]);
}
?>
