package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.modelo.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CadastroProdutoActivity extends AppCompatActivity {

    EditText editNome, editDescricao, editPreco, editImagem;
    Button btnCadastrar;

    Spinner spinnerCategoria;
    DatabaseReference refProdutos;

    DatabaseReference refCategorias;

    List<Categoria> categorias;

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

        refCategorias = FirebaseDatabase.getInstance().getReference("categorias");

        btnCadastrar.setOnClickListener(v -> cadastrarProduto());

        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        categorias = new ArrayList<Categoria>();

        categorias.add(new Categoria("0", "Selecione a categoria"));

        refCategorias.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categoriaSnapshot : snapshot.getChildren()) {
                    Categoria categoria = categoriaSnapshot.getValue(Categoria.class);
                    categorias.add(categoria);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log.e("Firebase", "Erro ao buscar categorias", error.toException());
            }
        });

        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(
                CadastroProdutoActivity.this,
                android.R.layout.simple_spinner_item,
                categorias
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Desativa o item "Categoria"
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.GREEN);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

    }

    private void cadastrarProduto() {
        String nome = editNome.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String preco = editPreco.getText().toString().trim();
        String imagem = editImagem.getText().toString().trim();
        ;

        if(spinnerCategoria.getSelectedItemPosition() == 0){
            Toast.makeText(this, "Informa a categoria do produto!", Toast.LENGTH_SHORT).show();
            return;
        }

        Categoria categoria = categorias.get(spinnerCategoria.getSelectedItemPosition());

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(preco) || TextUtils.isEmpty(imagem)) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = refProdutos.push().getKey();

        Produto produto = new Produto(id, nome, descricao, preco, imagem, 1, categoria);  // Passando a quantidade (1)


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
