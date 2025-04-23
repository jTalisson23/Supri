<?php
include 'db.php';

$sql = "SELECT * FROM produtos";
$resultado = $conn->query($sql);

$produtos = [];

if ($resultado->num_rows > 0) {
    while ($linha = $resultado->fetch_assoc()) {
        $produtos[] = $linha;
    }
}

header('Content-Type: application/json');
echo json_encode($produtos);
?>
