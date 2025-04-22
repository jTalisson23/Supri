package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CarrinhoActivity extends AppCompatActivity {

    RecyclerView recyclerCarrinho;
    TextView textQtd, textTotal;
    Button buttonConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        recyclerCarrinho = findViewById(R.id.recyclerCarrinho);
        textQtd = findViewById(R.id.textQtd);
        textTotal = findViewById(R.id.textTotal);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        // Aqui você pode futuramente carregar os dados do carrinho

        buttonConfirmar.setOnClickListener(v -> {
            Toast.makeText(this, "Pedido confirmado!", Toast.LENGTH_SHORT).show();
        });
    }
}

