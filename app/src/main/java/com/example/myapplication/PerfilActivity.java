package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PerfilActivity extends AppCompatActivity {

    private TextView txtEditarPerfil, txtAlterarSenha, txtHistoricoPedidos,
            txtStatusPedidos, txtPolitica, txtAjuda, txtSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Ligando as views pelo ID
        txtEditarPerfil = findViewById(R.id.txtEditarPerfil);
        txtAlterarSenha = findViewById(R.id.txtAlterarSenha);
        txtHistoricoPedidos = findViewById(R.id.txtHistoricoPedidos);
        txtStatusPedidos = findViewById(R.id.txtStatusPedidos);
        txtPolitica = findViewById(R.id.txtPolitica);
        txtAjuda = findViewById(R.id.txtAjuda);
        txtSair = findViewById(R.id.txtSair);

        // Eventos de clique
        txtEditarPerfil.setOnClickListener(v -> abrirTela("Editar Perfil"));
        txtAlterarSenha.setOnClickListener(v -> abrirTela("Alterar Senha"));
        txtHistoricoPedidos.setOnClickListener(v -> abrirTela("Histórico de Pedidos"));
        txtStatusPedidos.setOnClickListener(v -> abrirTela("Status de Pedidos"));
        txtPolitica.setOnClickListener(v -> abrirTela("Política de Privacidade"));
        txtAjuda.setOnClickListener(v -> abrirTela("Central de Ajuda"));
        txtSair.setOnClickListener(v -> sairDaConta());
    }

    private void abrirTela(String nomeTela) {
        Toast.makeText(this, "Abrir: " + nomeTela, Toast.LENGTH_SHORT).show();

        // Exemplo para abrir nova tela:
        // Intent intent = new Intent(this, EditarPerfilActivity.class);
        // startActivity(intent);
    }

    private void sairDaConta() {
        FirebaseAuth.getInstance().signOut(); // Faz logout do Firebase
        Toast.makeText(this, "Logout realizado!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpa a pilha
        startActivity(intent);
        finish();
    }
}
