package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InicioActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Obter o nome do usuário da Intent
        String nomeUsuario = getIntent().getStringExtra("nome_usuario");

        // Referenciar o TextView de saudação
        TextView textSaudacao = findViewById(R.id.textSaudacao);

        // Exibir o nome do usuário ou mensagem padrão
        if (nomeUsuario != null && !nomeUsuario.isEmpty()) {
            textSaudacao.setText("Olá, " + nomeUsuario + "!");
        } else {
            textSaudacao.setText("Olá, Seja Bem Vindo!");
        }

        // Referenciar o BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Definir a navegação do BottomNavigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Navegação para as diferentes telas no Bottom Navigation
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

        // Referenciar o botão "Ir para Produtos"
        Button btnIrParaProdutos = findViewById(R.id.BntirParalimpeza);

        // Definir o clique do botão
        btnIrParaProdutos.setOnClickListener(v -> {
            // Navegar para a tela de Produtos (ProductsActivity)
            Intent intent = new Intent(InicioActivity.this, productsfa.class);
            startActivity(intent);
        });
    }
}
