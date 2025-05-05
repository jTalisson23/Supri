package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;  // Importando o Looper

import androidx.appcompat.app.AppCompatActivity;

public class PedidoConfirmado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_confirmado);

        // Após 3 segundos, ir para a tela inicial
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(PedidoConfirmado.this, InicioActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
