<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");

$input = json_decode(file_get_contents("php://input"), true);

$usuario = $input['usuario'];
$senha = $input['senha'];

$conn = new mysqli("localhost", "root", "", "suprilimpa");

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Erro de conexão"]);
    exit();
}

$sql = "SELECT * FROM usuarios WHERE usuario = '$usuario' AND senha = '$senha'";
$result = $conn->query($sql);

if ($result && $result->num_rows > 0) {
    echo json_encode(["success" => true, "message" => "Login OK"]);
} else {
    echo json_encode(["success" => false, "message" => "Usuário ou senha inválidos"]);
}

$conn->close();
?>
