package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.modelo.Produto;

import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {

    private RecyclerView recyclerCarrinho;
    private TextView textQtd, textTotal;
    private Button buttonConfirmar;
    private CarrinhoAdapter carrinhoAdapter;
    private List<Produto> listaCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // Referências aos componentes da interface
        recyclerCarrinho = findViewById(R.id.recyclerProdutos);
        textQtd = findViewById(R.id.textQtd);
        textTotal = findViewById(R.id.textTotal);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        // Obtem a lista de produtos adicionados ao carrinho
        listaCarrinho = CarrinhoSingleton.getInstance().getProdutos();

        // Configuração do RecyclerView
        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));
        carrinhoAdapter = new CarrinhoAdapter(listaCarrinho, this::atualizarResumo);
        recyclerCarrinho.setAdapter(carrinhoAdapter);

        atualizarResumo();

        // Botão "Confirmar Pedido"
        buttonConfirmar.setOnClickListener(v ->
                Toast.makeText(this, "Pedido confirmado!", Toast.LENGTH_SHORT).show());
    }

    // Atualiza a quantidade e o valor total do carrinho
    private void atualizarResumo() {
        int totalItens = 0;
        double totalValor = 0.0;

        for (Produto produto : listaCarrinho) {
            int quantidade = produto.getQuantidade();
            double preco;

            try {
                preco = Double.parseDouble(produto.getPreco());
            } catch (NumberFormatException e) {
                preco = 0.0; // evita crash se o preço estiver mal formatado
            }

            totalItens += quantidade;
            totalValor += preco * quantidade;
        }

        textQtd.setText("Qtd. Itens: " + totalItens);
        textTotal.setText(String.format("Total: R$ %.2f", totalValor));
    }
}
