<?php
include 'db_config.php';

$data = json_decode(file_get_contents("php://input"));
$username = $data->username;
$password = $data->password;
$role = $data->role;

$query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("sss", $username, $password, $role);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    echo json_encode([
        "status" => "success",
        "role" => $row['role'],
        "id" => $row['id'] 
    ]);
} else {
    echo json_encode(["status" => "error", "message" => "Invalid login"]);
}
?>

