package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private ViewPager2 viewPagerCarrosel;
    private int paginaAtual = 0;
    private final Handler handler = new Handler();
    private final int delay = 3000; // 3 segundos

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Saudação com nome
        String nomeUsuario = getIntent().getStringExtra("nome_usuario");
        TextView textSaudacao = findViewById(R.id.textSaudacao);
        if (nomeUsuario != null && !nomeUsuario.isEmpty()) {
            textSaudacao.setText("Olá, " + nomeUsuario + "!");
        } else {
            textSaudacao.setText("Olá, Seja Bem Vindo!");
        }

        // Navegação inferior
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

        // Botão para produtos
        Button btnIrParaProdutos = findViewById(R.id.BntirParalimpeza);
        btnIrParaProdutos.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, productsfa.class);
            startActivity(intent);
        });

        // --- Carrossel de imagens ---
        viewPagerCarrosel = findViewById(R.id.viewPagerCarrosel);

        List<Integer> imagensCarrosel = Arrays.asList(
                R.drawable.banner_fretegratis,
                R.drawable.banner_desconto,
                R.drawable.banner_oferta_especial
        );

        CarroselAdaptador adaptador = new CarroselAdaptador(this, imagensCarrosel);
        viewPagerCarrosel.setAdapter(adaptador);

        // Auto scroll
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (paginaAtual == imagensCarrosel.size()) {
                    paginaAtual = 0;
                }
                viewPagerCarrosel.setCurrentItem(paginaAtual++, true);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
