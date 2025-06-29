<?php
include 'db_config.php';

$data = json_decode(file_get_contents("php://input"));
$user_id = $data->user_id;
$total_price = $data->total_price;
$cart = $data->cart;

$conn->query("INSERT INTO orders (user_id, total_price) VALUES ('$user_id', '$total_price')");
$order_id = $conn->insert_id;

foreach ($cart as $item) {
    $name = $item->name;
    $quantity = $item->quantity;

    // Get item_id from name
    $q = $conn->query("SELECT id FROM menu WHERE name = '$name'");
    $r = $q->fetch_assoc();
    $item_id = $r['id'];

    $conn->query("INSERT INTO order_items (order_id, item_id, quantity) VALUES ('$order_id', '$item_id', '$quantity')");
}

echo json_encode(["message" => "Order placed!", "order_id" => $order_id]);
?>
