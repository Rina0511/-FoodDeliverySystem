<?php
include 'db_config.php';

$data = json_decode(file_get_contents("php://input"));
$username = $data->username;
$password = $data->password;

$query = "SELECT * FROM users WHERE username = '$username' AND password = '$password'";
$result = $conn->query($query);

if ($result->num_rows > 0) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "failed"]);
}
?>
