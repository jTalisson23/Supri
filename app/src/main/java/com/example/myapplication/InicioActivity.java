package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private ViewPager2 viewPagerCarrosel;
    private int paginaAtual = 0;
    private final Handler handler = new Handler();
    private final int delay = 3000;

    private FirebaseAuth mAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView textSaudacao = findViewById(R.id.textSaudacao);
        if (user != null && user.getEmail() != null) {
            textSaudacao.setText("Olá, " + user.getEmail() + "!");
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
                if (mAuth.getCurrentUser() != null) {
                    // Usuário está logado → abre tela de perfil
                    startActivity(new Intent(this, PerfilActivity.class));
                } else {
                    // Usuário NÃO está logado → vai para tela de login
                    Toast.makeText(this, "Faça login para acessar seu perfil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                return true;
            }

            return false;
        });

        // Botão de acesso à tela de produtos
        CardView btnIrParaProdutos = findViewById(R.id.BntirParalimpeza);
        btnIrParaProdutos.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, productsfa.class);
            startActivity(intent);
        });

        CardView bntIrparaUtensilios = findViewById(R.id.bntIrparaUtensilios);
        bntIrparaUtensilios.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, UtensiliosActivity.class);
            startActivity(intent);
        });

        CardView bntIrparaMateriais = findViewById(R.id.bntIrparaMateriais);
        bntIrparaMateriais.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, MateriaisActivity.class);
            startActivity(intent);
        });

        CardView bntTodos = findViewById(R.id.bntTodos);
        bntTodos.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, TodosProdActivity.class);
            startActivity(intent);
        });

        // Botão de acesso à tela de cadastro de produto
        Button btnAbrirCadastro = findViewById(R.id.btnAbrirCadastro);
        btnAbrirCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, CadastroProdutoActivity.class);
            startActivity(intent);
        });

        // Carrossel
        viewPagerCarrosel = findViewById(R.id.viewPagerCarrosel);
        List<Integer> imagensCarrosel = Arrays.asList(
                R.drawable.banner_fretegratis,
                R.drawable.banner_desconto,
                R.drawable.banner_oferta_especial
        );

        CarroselAdaptador adaptador = new CarroselAdaptador(this, imagensCarrosel);
        viewPagerCarrosel.setAdapter(adaptador);

        // Loop automático do carrossel
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
