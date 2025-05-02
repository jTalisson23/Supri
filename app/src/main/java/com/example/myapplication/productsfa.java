package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.modelo.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class productsfa extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products); // Corrigido: deve ser um layout de activity

        recyclerView = findViewById(R.id.recyclerProdutos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        listaProdutos = new ArrayList<>();
        adapter = new ProdutoAdapter(listaProdutos, this);
        recyclerView.setAdapter(adapter);

        Button buttonVerCarrinho = findViewById(R.id.btnVerCarrinho);
        buttonVerCarrinho.setOnClickListener(v -> {
            List<Produto> selecionados = adapter.getSelecionados();

            if (selecionados.isEmpty()) {
                Toast.makeText(this, "Selecione pelo menos um produto", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Produto produto : selecionados) {
                produto.setQuantidade(1);
                CarrinhoSingleton.getInstance().adicionarProduto(produto);
            }

            startActivity(new Intent(this, CarrinhoActivity.class));
        });

        carregarProdutosFirebase();
    }

    private void carregarProdutosFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProdutos.clear();
                for (DataSnapshot dado : snapshot.getChildren()) {
                    Produto produto = dado.getValue(Produto.class);
                    if (produto != null) {
                        listaProdutos.add(produto);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(productsfa.this, "Erro ao carregar produtos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
