package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textCreateAccount;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textCreateAccount = findViewById(R.id.textCreateAccount);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("E-mail inválido");
                return;
            }

            if (senha.length() < 6) {
                editTextPassword.setError("Senha deve ter no mínimo 6 caracteres");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String emailUsuario = user != null ? user.getEmail() : "Usuário";

                            Intent intent = new Intent(this, InicioActivity.class);
                            intent.putExtra("nome_usuario", emailUsuario);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Login inválido. Verifique e-mail e senha.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        textCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
            finish();
        });
    }
}
