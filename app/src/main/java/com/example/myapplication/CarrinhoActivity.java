package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.modelo.Produto;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {

    RecyclerView recyclerCarrinho;
    TextView textQtd, textTotal;
    Button buttonConfirmar;
    List<Produto> listaProdutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        recyclerCarrinho = findViewById(R.id.recyclerProdutos);
        textQtd = findViewById(R.id.textQtd);
        textTotal = findViewById(R.id.textTotal);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto("1", "Limpador Lavanda", "Descrição do produto", "12.00", "imagem_url", 1));


        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));


        atualizarResumo(listaProdutos);

        buttonConfirmar.setOnClickListener(v ->
                Toast.makeText(this, "Pedido confirmado!", Toast.LENGTH_SHORT).show());
    }

    public void onProdutoAtualizado(List<Produto> produtos) {
        atualizarResumo(produtos);
    }

    private void atualizarResumo(List<Produto> produtos) {
        int totalItens = 0;
        double totalValor = 0.0;

        for (Produto p : produtos) {
            totalItens += p.getQuantidade();
            totalValor += Double.parseDouble(p.getPreco()) * p.getQuantidade();  // Converte preco para double
        }

        textQtd.setText("Qtd. Itens: " + totalItens);
        textTotal.setText(String.format("Total: R$ %.2f", totalValor));
    }
}
