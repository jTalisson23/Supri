<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Produto</title>
</head>
<body>
    <h2>Cadastrar Produto</h2>

    <form action="salvar.php" method="POST">
        <label>Nome:</label><br>
        <input type="text" name="nome" required><br><br>

        <label>Quantidade:</label><br>
        <input type="number" name="quantidade" required><br><br>

        <label>Preço:</label><br>
        <input type="text" name="preco" required><br><br>

        <button type="submit">Salvar Produto</button>
    </form>

    <br><br>
    <a href="listar.php">📦 Ver lista de produtos</a>
</body>
</html>
