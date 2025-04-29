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


import com.example.myapplication.ProdutoAdaptador;


public class CarrinhoActivity extends AppCompatActivity implements ProdutoAdaptador.OnProdutoAtualizadoListener {

    RecyclerView recyclerCarrinho;
    TextView textQtd, textTotal;
    Button buttonConfirmar;
    List<Produto> listaProdutos;
    ProdutoAdaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        recyclerCarrinho = findViewById(R.id.recyclerProdutos);
        textQtd = findViewById(R.id.textQtd);
        textTotal = findViewById(R.id.textTotal);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        // Criação da lista e produto inicial
        listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto("Limpador Desengordurante - Lavanda", 12.00, 1, R.drawable.lavanda));

        // Adaptador com listener
        ProdutoAdaptador adaptor = new ProdutoAdaptador(listaProdutos, this);
        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));
        recyclerCarrinho.setAdapter(adaptor);

        // Atualizar totais no início
        atualizarResumo(listaProdutos);

        buttonConfirmar.setOnClickListener(v -> {
            Toast.makeText(this, "Pedido confirmado!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onAtualizarTotais(List<Produto> produtos) {
        atualizarResumo(produtos);
    }

    private void atualizarResumo(List<Produto> produtos) {
        int totalItens = 0;
        double totalValor = 0.0;

        for (Produto p : produtos) {
            totalItens += p.getQuantidade();
            totalValor += p.getPreco() * p.getQuantidade();
        }

        textQtd.setText("Qtd. Itens: " + totalItens);
        textTotal.setText(String.format("Total: R$ %.2f", totalValor));
    }
}