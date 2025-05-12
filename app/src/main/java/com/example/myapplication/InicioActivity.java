package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Arrays;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private ViewPager2 viewPagerCarrosel;
    private int paginaAtual = 0;
    private final Handler handler = new Handler();
    private final int delay = 3000;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private TextView textSaudacao, textEndereco;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        textSaudacao = findViewById(R.id.textSaudacao);
        textEndereco = findViewById(R.id.textEndereco);

        // Buscar nome e endereço do Firestore
        if (user != null) {
            String uid = user.getUid();

            firestore.collection("usuarios").document(uid)
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            String nome = document.getString("nome");
                            textSaudacao.setText("Olá, " + nome + "!");

                            if (document.contains("endereco")) {
                                String rua = document.get("endereco.rua") != null ? document.get("endereco.rua").toString() : "";
                                String numero = document.get("endereco.numero") != null ? document.get("endereco.numero").toString() : "";
                                textEndereco.setText(rua + " - " + numero);
                            }
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erro ao carregar dados do usuário", Toast.LENGTH_SHORT).show());
        } else {
            textSaudacao.setText("Olá, Seja Bem Vindo!");
        }

        // Navegação inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Já estamos na Home!", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_carrinho) {
                startActivity(new Intent(this, CarrinhoActivity.class));
                return true;
            } else if (id == R.id.nav_pesquisar) {
                startActivity(new Intent(this, TodosProdActivity.class));
                return true;
            } else if (id == R.id.nav_perfil) {
                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(this, PerfilActivity.class));
                } else {
                    Toast.makeText(this, "Faça login para acessar seu perfil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                return true;
            }

            return false;
        });

        // Botões de categorias
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

        // Botão de cadastro de produto
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