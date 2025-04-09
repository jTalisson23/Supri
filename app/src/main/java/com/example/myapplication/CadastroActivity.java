package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroActivity extends AppCompatActivity {

    EditText editTextNome, editTextEmail, editTextUsuario, editTextSenha;
    Button buttonRegister;
    TextView textGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsuario = findViewById(R.id.editTextUsername); // ← mantém o ID
        editTextSenha = findViewById(R.id.editTextPassword);   // ← mantém o ID
        buttonRegister = findViewById(R.id.buttonRegister);
        textGoToLogin = findViewById(R.id.textGoToLogin);

        buttonRegister.setOnClickListener(view -> {
            Log.d("CADASTRO", "Botão clicado"); // mensagem de erro caso o login nao funcione

            String nome = editTextNome.getText().toString();
            String email = editTextEmail.getText().toString();
            String usuario = editTextUsuario.getText().toString();
            String senha = editTextSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
                Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Objeto com campos corretos: nome, email, usuario, senha
            Usuario user = new Usuario(nome, email, usuario, senha);
            Gson gson = new Gson();
            String json = gson.toJson(user);

            String url = "http://10.0.2.2/Projeto/suprilimpa_api/register.php";

            RequestQueue queue = Volley.newRequestQueue(CadastroActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Log.d("CADASTRO", "Resposta: " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String mensagem = obj.getString("mensagem");

                            Toast.makeText(CadastroActivity.this, mensagem, Toast.LENGTH_SHORT).show();

                            if (status.equals("sucesso")) {
                                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CadastroActivity.this, "Erro ao interpretar resposta", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(CadastroActivity.this, "Erro na requisição: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    return json.getBytes();
                }
            };

            queue.add(stringRequest);
        });

        textGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
            finish();
        });
    }
}
