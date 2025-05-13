package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilActivity extends AppCompatActivity {

    private TextView txtEditarPerfil, txtAlterarSenha, txtHistoricoPedidos,
            txtStatusPedidos, txtPolitica, txtAjuda, txtSair, textNomeUsuario;

    private FirebaseFirestore firestore;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Firebase
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uid = user.getUid();
        }

        // Ligando as views pelo ID
        textNomeUsuario = findViewById(R.id.textNomeUsuario);
        txtEditarPerfil = findViewById(R.id.txtEditarPerfil);
        txtAlterarSenha = findViewById(R.id.txtAlterarSenha);
        txtHistoricoPedidos = findViewById(R.id.txtHistoricoPedidos);
        txtStatusPedidos = findViewById(R.id.txtStatusPedidos);
        txtPolitica = findViewById(R.id.txtPolitica);
        txtAjuda = findViewById(R.id.txtAjuda);
        txtSair = findViewById(R.id.txtSair);

        // Botão: Editar perfil
        txtEditarPerfil.setOnClickListener(v -> {
            startActivity(new Intent(PerfilActivity.this, Perfil_editar.class));
        });

        // Botão: Alterar senha
        txtAlterarSenha.setOnClickListener(v -> {
            startActivity(new Intent(PerfilActivity.this, Perfil_alterarsenha.class));
        });

        // Botão: Histórico de pedidos
        txtHistoricoPedidos.setOnClickListener(v -> {
            startActivity(new Intent(PerfilActivity.this, Perfil_historicodepedidos.class));
        });

        // Outros itens de menu
        txtStatusPedidos.setOnClickListener(v -> abrirTela("Status de Pedidos"));
        txtPolitica.setOnClickListener(v -> abrirTela("Política de Privacidade"));
        txtAjuda.setOnClickListener(v -> abrirTela("Central de Ajuda"));

        // Sair da conta
        txtSair.setOnClickListener(v -> sairDaConta());

        // Carregar nome inicial
        carregarNomeDoFirestore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNomeDoFirestore(); // Atualiza nome ao voltar da edição
    }

    private void carregarNomeDoFirestore() {
        if (uid != null) {
            firestore.collection("usuarios").document(uid)
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            String nome = document.getString("nome");
                            if (nome != null && !nome.isEmpty()) {
                                textNomeUsuario.setText(nome);
                            } else {
                                textNomeUsuario.setText("Usuário");
                            }
                        }
                    });
        }
    }

    // ALERTA DE BOTÃO SELECIONADO
    private void abrirTela(String nomeTela) {
        Toast.makeText(this, "Abrir: " + nomeTela, Toast.LENGTH_SHORT).show();
        // Pode adicionar intents personalizados aqui
    }

    private void sairDaConta() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logout realizado!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
