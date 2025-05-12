package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextPassword;
    private EditText editTextCep, editTextRua, editTextNumero;
    private EditText editTextBairro, editTextCidade, editTextEstado;
    private Button buttonRegister, btnBuscarCep;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Referências de layout
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        editTextCep = findViewById(R.id.editTextCep);
        editTextRua = findViewById(R.id.editTextRua);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);

        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Buscar endereço
        btnBuscarCep.setOnClickListener(v -> buscarEnderecoPorCep(editTextCep.getText().toString()));

        // Cadastrar usuário
        buttonRegister.setOnClickListener(view -> {
            String nome = editTextNome.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            String cep = editTextCep.getText().toString().trim();
            String rua = editTextRua.getText().toString().trim();
            String numero = editTextNumero.getText().toString().trim();
            String bairro = editTextBairro.getText().toString().trim();
            String cidade = editTextCidade.getText().toString().trim();
            String estado = editTextEstado.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() ||
                    cep.isEmpty() || rua.isEmpty() || numero.isEmpty() ||
                    bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();

                            Usuario usuario = new Usuario(
                                    nome, email,
                                    new Endereco(cep, rua, numero, bairro, cidade, estado)
                            );

                            firestore.collection("usuarios").document(uid)
                                    .set(usuario)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(this, LoginActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(this, "Erro ao salvar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    // Buscar endereço via ViaCEP
    private void buscarEnderecoPorCep(String cep) {
        new Thread(() -> {
            try {
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject json = new JSONObject(result.toString());

                runOnUiThread(() -> {
                    editTextRua.setText(json.optString("logradouro"));
                    editTextBairro.setText(json.optString("bairro"));
                    editTextCidade.setText(json.optString("localidade"));
                    editTextEstado.setText(json.optString("uf"));
                });

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }

    // Modelo do usuário
    public static class Usuario {
        public String nome, email;
        public Endereco endereco;

        public Usuario() {}

        public Usuario(String nome, String email, Endereco endereco) {
            this.nome = nome;
            this.email = email;
            this.endereco = endereco;
        }
    }

    // Modelo do endereço
    public static class Endereco {
        public String cep, rua, numero, bairro, cidade, estado;

        public Endereco() {}

        public Endereco(String cep, String rua, String numero, String bairro, String cidade, String estado) {
            this.cep = cep;
            this.rua = rua;
            this.numero = numero;
            this.bairro = bairro;
            this.cidade = cidade;
            this.estado = estado;
        }
    }
}
