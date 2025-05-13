package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Perfil_editar extends AppCompatActivity {

    private EditText editNome, editCep, editRua, editNumero, editBairro, editCidade, editEstado;
    private Button btnSalvar, btnBuscarCep;
    private FirebaseFirestore firestore;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_editar); // nome correto do XML

        // ActionBar com botão voltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Editar Perfil");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        firestore = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Referências
        editNome = findViewById(R.id.editNome);
        editCep = findViewById(R.id.editCep);
        editRua = findViewById(R.id.editRua);
        editNumero = findViewById(R.id.editNumero);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);
        btnSalvar = findViewById(R.id.btnSalvarAlteracoes);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);

        // Carrega dados existentes do Firestore
        firestore.collection("usuarios").document(uid)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        editNome.setText(document.getString("nome"));
                        Map<String, Object> endereco = (Map<String, Object>) document.get("endereco");
                        if (endereco != null) {
                            editCep.setText((String) endereco.get("cep"));
                            editRua.setText((String) endereco.get("rua"));
                            editNumero.setText((String) endereco.get("numero"));
                            editBairro.setText((String) endereco.get("bairro"));
                            editCidade.setText((String) endereco.get("cidade"));
                            editEstado.setText((String) endereco.get("estado"));
                        }
                    }
                });

        // Buscar CEP (ViaCEP)
        btnBuscarCep.setOnClickListener(v -> buscarEndereco(editCep.getText().toString().trim()));

        // Salvar alterações
        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String cep = editCep.getText().toString().trim();
            String rua = editRua.getText().toString().trim();
            String numero = editNumero.getText().toString().trim();
            String bairro = editBairro.getText().toString().trim();
            String cidade = editCidade.getText().toString().trim();
            String estado = editEstado.getText().toString().trim();

            if (nome.isEmpty() || cep.isEmpty() || rua.isEmpty() || numero.isEmpty()
                    || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> endereco = new HashMap<>();
            endereco.put("cep", cep);
            endereco.put("rua", rua);
            endereco.put("numero", numero);
            endereco.put("bairro", bairro);
            endereco.put("cidade", cidade);
            endereco.put("estado", estado);

            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nome);
            dados.put("endereco", endereco);

            firestore.collection("usuarios").document(uid)
                    .update(dados)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, PerfilActivity.class));
                        finish(); // Fecha tela de edição
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    // Buscar dados do ViaCEP
    private void buscarEndereco(String cep) {
        new Thread(() -> {
            try {
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject json = new JSONObject(result.toString());

                runOnUiThread(() -> {
                    editRua.setText(json.optString("logradouro", ""));
                    editBairro.setText(json.optString("bairro", ""));
                    editCidade.setText(json.optString("localidade", ""));
                    editEstado.setText(json.optString("uf", ""));
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }

    // Botão voltar (ActionBar)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Volta para a tela anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
