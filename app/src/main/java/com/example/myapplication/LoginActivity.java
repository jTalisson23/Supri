package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    TextView textCreateAccount; // MOVI AQUI PARA FICAR JUNTO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializando os componentes
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textCreateAccount = findViewById(R.id.textCreateAccount);

        // "Criar conta vai para tela de cadastro "
        textCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(intent);
            finish(); // Fecha tela de login
        });

        // Clique para fazer login
        buttonLogin.setOnClickListener(view -> {
            String usuario = editTextUsername.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            if (usuario.isEmpty() || senha.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            UsuarioLogin userLogin = new UsuarioLogin(usuario, senha);
            Gson gson = new Gson();
            String json = gson.toJson(userLogin);

            String url = "http://10.0.2.2/Projeto/suprilimpa_api/login.php";

            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                                // Enviando o nome do usuário para a próxima tela
                                Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                                intent.putExtra("nome_usuario", usuario); // Agora passa o nome corretamente
                                startActivity(intent);
                                finish(); // Opcional, fecha tela de login
                            } else {
                                Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Erro no formato da resposta", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(LoginActivity.this, "Erro na conexão: " + error.getMessage(), Toast.LENGTH_SHORT).show()
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
    }
}
