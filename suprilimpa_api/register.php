<?php
// Cabeçalhos para permitir requisições externas e JSON
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// Lê o corpo da requisição
$inputJson = file_get_contents("php://input");
$dados = json_decode($inputJson);

if (!$dados) {
    echo json_encode(["status" => "erro", "mensagem" => "Dados JSON inválidos."]);
    exit;
}

// Conexão com o banco
$host = "localhost";
$user = "root";
$pass = "";
$db = "suprilimpa"; // (esse nome você confirmou)

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    echo json_encode(["status" => "erro", "mensagem" => "Erro de conexão"]);
    exit;
}

// Pega os dados do JSON
$nome = $dados->nome ?? '';
$email = $dados->email ?? '';
$usuario = $dados->usuario ?? '';
$senha = $dados->senha ?? '';

// Insere no banco
$sql = "INSERT INTO usuarios (nome, email, usuario, senha) VALUES (?, ?, ?, ?)";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ssss", $nome, $email, $usuario, $senha);

if ($stmt->execute()) {
    echo json_encode(["status" => "sucesso", "mensagem" => "Cadastro realizado com sucesso!"]);
} else {
    echo json_encode(["status" => "erro", "mensagem" => "Erro ao cadastrar."]);
}

$conn->close();
?>
