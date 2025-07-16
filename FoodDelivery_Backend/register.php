<?php
include 'db_config.php'; 

$data = json_decode(file_get_contents("php://input"));

$email = $data->email;
$username = $data->username;
$password = $data->password;
$confirmPassword = $data->confirmPassword;
$phone = $data->phone;
$address = $data->address; 
$role = $data->role; 

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

$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// Insert user with hashed password and address
$insertQuery = "INSERT INTO users (email, username, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
$stmt = $conn->prepare($insertQuery);
$stmt->bind_param("ssssss", $email, $username, $hashedPassword, $phone, $address, $role);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "User registered successfully."]);
} else {
    echo json_encode(["status" => "error", "message" => "Database error: " . $conn->error]);
}
?>
