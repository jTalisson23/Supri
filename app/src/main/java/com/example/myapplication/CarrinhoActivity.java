package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {

    RecyclerView recyclerCarrinho;
    TextView textQtd, textTotal;
    Button buttonConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // Inicialize a RecyclerView primeiro
        recyclerCarrinho = findViewById(R.id.recyclerCarrinho);
        textQtd = findViewById(R.id.textQtd);
        textTotal = findViewById(R.id.textTotal);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        // Crie a lista de produtos
        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto(
                "Limpador Desengordurante - Lavanda",
                12.00,
                1
        ));

        // Inicialize o adaptador
        ProdutoAdaptador adaptador = new ProdutoAdaptador(listaProdutos);

        // Configuração do RecyclerView
        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));
        recyclerCarrinho.setAdapter(adaptador);

        // Comportamento do botão confirmar
        buttonConfirmar.setOnClickListener(v -> {
            Toast.makeText(this, "Pedido confirmado!", Toast.LENGTH_SHORT).show();
        });
    }
}
