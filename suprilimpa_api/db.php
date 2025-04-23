<?php
$host = "localhost";
$user = "root";
$senha = "";
$banco = "suprilimpa"; // <- nome atualizado do banco

$conn = new mysqli($host, $user, $senha, $banco);

if ($conn->connect_error) {
    die("Erro de conexão: " . $conn->connect_error);
}
?>
=======
<?php
$host = "localhost";
$user = "root";
$senha = "";
$banco = "suprilimpa"; // <- nome atualizado do banco

$conn = new mysqli($host, $user, $senha, $banco);

if ($conn->connect_error) {
    die("Erro de conexão: " . $conn->connect_error);
}
?>
