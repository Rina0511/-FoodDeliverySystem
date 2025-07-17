<?php
include 'db_config.php';

$rider_id = $_GET['rider_id'] ?? null;

if (!is_numeric($rider_id)) {
    echo json_encode(["error" => "Invalid rider ID"]);
    exit;
}

// Fetch orders where:
// - Status is "pending" (not yet assigned), OR
// - Status is "assigned" and assigned to this rider
$sql = "SELECT 
            orders.id,
            orders.customer_id,
            orders.delivery_status,
            orders.created_at,
            orders.assigned_rider,
            users.address
        FROM orders
        JOIN users ON orders.customer_id = users.id
        WHERE delivery_status = 'pending'
           OR (delivery_status = 'assigned' AND assigned_rider = ?)";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $rider_id);
$stmt->execute();
$result = $stmt->get_result();

$orders = [];

while ($row = $result->fetch_assoc()) {
    $orders[] = $row;
}

header('Content-Type: application/json');
echo json_encode($orders);
?>
