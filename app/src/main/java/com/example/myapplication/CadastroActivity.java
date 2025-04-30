package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private TextView textGoToLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();

        editTextNome = findViewById(R.id.editTextNome); // usado para exibição, não enviado
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textGoToLogin = findViewById(R.id.textGoToLogin);

        buttonRegister.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("E-mail inválido");
                return;
            }

            if (senha.length() < 6) {
                editTextPassword.setError("Mínimo 6 caracteres");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Cadastro feito com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        textGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
