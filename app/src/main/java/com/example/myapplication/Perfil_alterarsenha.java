package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil_alterarsenha extends AppCompatActivity {

    private EditText editSenhaAtual, editNovaSenha;
    private Button btnAlterarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alterarsenha);

        // Configura a ActionBar com botão de voltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Alterar Senha");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editSenhaAtual = findViewById(R.id.editSenhaAtual);
        editNovaSenha = findViewById(R.id.editNovaSenha);
        btnAlterarSenha = findViewById(R.id.btnAlterarSenha);

        btnAlterarSenha.setOnClickListener(v -> {
            String senhaAtual = editSenhaAtual.getText().toString().trim();
            String novaSenha = editNovaSenha.getText().toString().trim();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null && senhaAtual.length() > 0 && novaSenha.length() >= 6) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), senhaAtual);

                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(novaSenha).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                new AlertDialog.Builder(this)
                                        .setTitle("Sucesso")
                                        .setMessage("Sua senha foi alterada com sucesso.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", (dialog, which) -> {
                                            Intent intent = new Intent(Perfil_alterarsenha.this, PerfilActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .show();
                            } else {
                                Toast.makeText(this, "Erro ao alterar a senha.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "Senha atual incorreta.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Preencha os campos corretamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Trata o clique no botão de voltar da ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
