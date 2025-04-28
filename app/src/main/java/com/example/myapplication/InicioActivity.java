package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InicioActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        String nomeUsuario = getIntent().getStringExtra("nome_usuario");

        TextView textSaudacao = findViewById(R.id.textSaudacao);

        if (nomeUsuario != null && !nomeUsuario.isEmpty()) {
            textSaudacao.setText("Olá, " + nomeUsuario + "!");
        } else {
            textSaudacao.setText("Olá, Seja Bem Vindo!");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Você já está na Home!", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_carrinho) {
                startActivity(new Intent(this, CarrinhoActivity.class));
                return true;
            } else if (id == R.id.nav_pesquisar) {
                Toast.makeText(this, "Pesquisar clicado!", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_perfil) {
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            }

            return false;
        });
    }
}
