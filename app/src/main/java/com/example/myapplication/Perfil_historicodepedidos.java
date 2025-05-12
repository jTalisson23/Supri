package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Perfil_historicodepedidos extends AppCompatActivity {

    private RecyclerView recyclerPedidos;
    private PedidoAdapterExpandable adapter;
    private List<PedidoDetalhado> listaPedidos;
    private FirebaseFirestore firestore;
    private String uidUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_historicodepedidos);

        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));

        listaPedidos = new ArrayList<>();
        adapter = new PedidoAdapterExpandable(listaPedidos);
        recyclerPedidos.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        uidUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        carregarPedidosDoAno("2025"); // ou use o ano atual dinamicamente
    }

    private void carregarPedidosDoAno(String ano) {
        DocumentReference docAno = firestore.collection("pedidos").document(ano);

        docAno.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                Map<String, Object> pedidosMap = snapshot.getData();

                for (String pedidoId : pedidosMap.keySet()) {
                    Map<String, Object> pedidoData = (Map<String, Object>) pedidosMap.get(pedidoId);

                    if (pedidoData != null && uidUsuario.equals(pedidoData.get("userId"))) {
                        String status = (String) pedidoData.get("status");
                        Object timestampObj;
                        timestampObj = pedidoData.get("timestamp");
                        Long timestamp = null;

                        if (timestampObj instanceof Long) {
                            timestamp = (Long) timestampObj;
                        } else if (timestampObj instanceof com.google.firebase.Timestamp) {
                            timestamp = ((com.google.firebase.Timestamp) timestampObj).toDate().getTime();
                        }

                        String dataFormatada = formatarData(timestamp);

                        firestore.collection("pedidos")
                                .document(ano)
                                .collection(pedidoId)
                                .document("produtos")
                                .get()
                                .addOnSuccessListener(produtoSnap -> {
                                    double total = 0;
                                    int quantidade = 0;

                                    if (produtoSnap.exists()) {
                                        for (String key : produtoSnap.getData().keySet()) {
                                            Object obj = produtoSnap.get(key);
                                            if (obj instanceof Map) {
                                                Map<String, Object> item = (Map<String, Object>) obj;
                                                Long qtd = (Long) item.get("quantidade");

                                                double preco = 0;
                                                Object precoObj = item.get("preco");
                                                if (precoObj instanceof Double) preco = (Double) precoObj;
                                                else if (precoObj instanceof Long) preco = ((Long) precoObj).doubleValue();

                                                total += preco * (qtd != null ? qtd : 1);
                                                quantidade += (qtd != null ? qtd : 1);
                                            }
                                        }
                                    }

                                    PedidoDetalhado pedido = new PedidoDetalhado(pedidoId, status, dataFormatada, ano, total, quantidade);
                                    listaPedidos.add(pedido);
                                    adapter.notifyDataSetChanged();
                                });
                    }
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Erro ao carregar pedidos", Toast.LENGTH_SHORT).show();
        });
    }

    private String formatarData(Long timestamp) {
        if (timestamp == null) return "-";
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}
