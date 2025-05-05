package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PedidoConfirmado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_confirmado);

        // Recupera o número do pedido
        int numeroPedido = getIntent().getIntExtra("numeroPedido", -1);

        // Exibe o número do pedido em um TextView
        if (numeroPedido != -1) {
            // Atualiza o TextView com o número do pedido
            TextView numeroPedidoText = findViewById(R.id.numeroPedidoTextView);
            numeroPedidoText.setText("Pedido nº #" + numeroPedido);

            // Exibe o Toast com o número do pedido
            Toast.makeText(this, "Pedido confirmado! Número: #" + numeroPedido, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Erro ao recuperar número do pedido", Toast.LENGTH_SHORT).show();
        }

        // Após 3 segundos, ir para a tela inicial
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(PedidoConfirmado.this, InicioActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}


