package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.modelo.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroProdutoActivity extends AppCompatActivity {

    EditText editNome, editDescricao, editPreco, editImagem;
    Button btnCadastrar;
    DatabaseReference refProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        editNome = findViewById(R.id.editNome);
        editDescricao = findViewById(R.id.editDescricao);
        editPreco = findViewById(R.id.editPreco);
        editImagem = findViewById(R.id.editImagem);
        btnCadastrar = findViewById(R.id.btnCadastrarProduto);

        refProdutos = FirebaseDatabase.getInstance().getReference("produtos");

        btnCadastrar.setOnClickListener(v -> cadastrarProduto());
    }

    private void cadastrarProduto() {
        String nome = editNome.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String preco = editPreco.getText().toString().trim();
        String imagem = editImagem.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(preco) || TextUtils.isEmpty(imagem)) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = refProdutos.push().getKey();

        Produto produto = new Produto(id, nome, descricao, preco, imagem, 1);  // Passando a quantidade (1)


        refProdutos.child(id).setValue(produto).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // Fecha a activity
            } else {
                Toast.makeText(this, "Erro ao cadastrar produto.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
