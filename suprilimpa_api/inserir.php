<?php
include 'db.php';

$nome = "Produto de Teste";
$quantidade = 10;
$preco = 29.90;

$sql = "INSERT INTO produtos (nome, quantidade, preco) VALUES ('$nome', $quantidade, $preco)";

if ($conn->query($sql) === TRUE) {
    echo "Produto inserido com sucesso!";
} else {
    echo "Erro: " . $conn->error;
}
?>
