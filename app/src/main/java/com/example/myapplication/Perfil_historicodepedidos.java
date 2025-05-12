package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Perfil_historicodepedidos extends AppCompatActivity {

    private RecyclerView recyclerPedidos;
    private PedidoAdapterExpandable adapter;
    private List<PedidoDetalhado> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_historicodepedidos);

        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));

        listaPedidos = new ArrayList<>();

        // MOCK DE PEDIDOS
        listaPedidos.add(new PedidoDetalhado("001", "Concluído", "10/05/2025", "2025", 120.50, 3));
        listaPedidos.add(new PedidoDetalhado("002", "Em andamento", "11/05/2025", "2025", 85.30, 2));
        listaPedidos.add(new PedidoDetalhado("003", "Cancelado", "12/05/2025", "2025", 0, 0));

        adapter = new PedidoAdapterExpandable(listaPedidos);
        recyclerPedidos.setAdapter(adapter);
    }
}
