package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.modelo.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.recyclerview.widget.GridLayoutManager;


import java.util.ArrayList;
import java.util.List;

public class UtensiliosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utensilios);

        recyclerView = findViewById(R.id.recyclerProdutosUtensilos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3 colunas

        listaProdutos = new ArrayList<>();
        adapter = new ProdutoAdapter(listaProdutos, this);
        recyclerView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("produtos");

        Query query = ref.orderByChild("categoria/nome")
                        .equalTo("Utensílios");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProdutos.clear();
                for (DataSnapshot dado : snapshot.getChildren()) {
                    Produto produto = dado.getValue(Produto.class);
                    listaProdutos.add(produto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UtensiliosActivity.this, "Erro ao carregar produtos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
