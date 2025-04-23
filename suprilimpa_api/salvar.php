<?php
include 'db.php'; // Certifique-se de que a conexão está sendo feita corretamente

// Pegando os dados do formulário
$nome = $_POST['nome'];
$quantidade = $_POST['quantidade'];
$preco = $_POST['preco'];

// Preparando e executando a consulta SQL
$stmt = $conn->prepare("INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)");
$stmt->bind_param("sdi", $nome, $quantidade, $preco);

if ($stmt->execute()) {
    echo "✅ Produto cadastrado com sucesso!";
} else {
    echo "❌ Erro ao cadastrar: " . $stmt->error;
}

// Fechando a conexão e a consulta
$stmt->close();
$conn->close();
?>
