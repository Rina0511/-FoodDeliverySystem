<?php
include 'db_config.php'; 

// Get the incoming JSON from Java
$data = json_decode(file_get_contents("php://input"));

$email = $data->email;
$username = $data->username;
$password = $data->password;
$confirmPassword = $data->confirmPassword;
$phone = $data->phone;
$role = $data->role; 

//validation
if ($password !== $confirmPassword) {
    echo json_encode(["status" => "error", "message" => "Passwords do not match."]);
    exit;
}

// Check if username already exists
$checkQuery = "SELECT * FROM users WHERE username = ?";
$stmt = $conn->prepare($checkQuery);
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    echo json_encode(["status" => "error", "message" => "Username already exists."]);
    exit;
}

// Insert user with role
$insertQuery = "INSERT INTO users (email, username, password, phone, role) VALUES (?, ?, ?, ?, ?)";
$stmt = $conn->prepare($insertQuery);
$stmt->bind_param("sssss", $email, $username, $password, $phone, $role);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "User registered successfully."]);
} else {
    echo json_encode(["status" => "error", "message" => "Database error: " . $conn->error]);
}
?>
