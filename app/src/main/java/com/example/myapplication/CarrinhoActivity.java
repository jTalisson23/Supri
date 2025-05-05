package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.modelo.Pedido;

import com.example.myapplication.modelo.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        if (listaCarrinho.isEmpty()) {
            Toast.makeText(this, "Carrinho vazio", Toast.LENGTH_SHORT).show();
            buttonConfirmar.setEnabled(false);
        }

        // Configuração do RecyclerView
        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));
        carrinhoAdapter = new CarrinhoAdapter(listaCarrinho, this::atualizarResumo);
        recyclerCarrinho.setAdapter(carrinhoAdapter);

        atualizarResumo();

        // Botão "Confirmar Pedido"
        buttonConfirmar.setOnClickListener(this::onClick);

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

    private void onClick(View v) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Você precisa estar logado para confirmar o pedido", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        gerarNumeroPedidoEEnviar();

    }
    private void salvarPedidoFirebase(String numeroPedido) {
        String anoAtual = String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
        DatabaseReference pedidosRef = FirebaseDatabase.getInstance().getReference("pedidos")
                .child(anoAtual)
                .child(numeroPedido);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Pedido pedido = new Pedido(userId, listaCarrinho, System.currentTimeMillis());

        pedidosRef.setValue(pedido).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Pedido confirmado com número #" + numeroPedido, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, PedidoConfirmado.class);
                intent.putExtra("numeroPedido", Integer.parseInt(numeroPedido));  // Envia o número do pedido
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Erro ao salvar pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gerarNumeroPedidoEEnviar() {
        String anoAtual = String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
        DatabaseReference pedidosRef = FirebaseDatabase.getInstance().getReference("pedidos").child(anoAtual);

        pedidosRef.child("contador").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                long numero = 1;
                if (task.getResult().exists()) {
                    numero = task.getResult().getValue(Long.class) + 1;
                }

                pedidosRef.child("contador").setValue(numero); // atualiza contador

                String numeroPedido = String.valueOf(numero);
                salvarPedidoFirebase(numeroPedido);

            } else {
                Toast.makeText(this, "Erro ao gerar número do pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
