<?php
include 'db_config.php';

$result = $conn->query("SELECT * FROM menu");
$items = [];

while ($row = $result->fetch_assoc()) {
    $items[] = $row;
}

echo json_encode($items);
?>
