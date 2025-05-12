package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class PedidoAdapterExpandable extends RecyclerView.Adapter<PedidoAdapterExpandable.ViewHolder> {

    private final List<PedidoDetalhado> listaPedidos;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public PedidoAdapterExpandable(List<PedidoDetalhado> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_expandable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PedidoDetalhado pedido = listaPedidos.get(position);

        holder.txtCodigo.setText("Pedido: #" + pedido.getCodigo());
        holder.txtStatus.setText("Status: " + pedido.getStatus());
        holder.txtData.setText("Data: " + pedido.getData());
        holder.txtResumo.setText("Total: R$ " + pedido.getValorTotal() + " | Itens: " + pedido.getQtdTotal());

        holder.layoutDetalhes.setVisibility(pedido.isExpandido() ? View.VISIBLE : View.GONE);
        holder.btnDetalhar.setText(pedido.isExpandido() ? "Ocultar" : "Ver detalhes");

        holder.btnDetalhar.setOnClickListener(v -> {
            if (!pedido.isExpandido()) {
                carregarItens(holder, pedido);
            } else {
                holder.layoutDetalhes.removeAllViews();
            }
            pedido.setExpandido(!pedido.isExpandido());
            notifyItemChanged(position);
        });
    }

    private void carregarItens(ViewHolder holder, PedidoDetalhado pedido) {
        holder.layoutDetalhes.removeAllViews();

        firestore.collection("pedidos")
                .document(pedido.getAno())
                .collection(pedido.getCodigo())
                .document("produtos") // CORRIGIDO AQUI
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        Map<String, Object> produtos = snapshot.getData();
                        if (produtos != null) {
                            for (String key : produtos.keySet()) {
                                Object obj = produtos.get(key);
                                if (obj instanceof Map) {
                                    Map<String, Object> item = (Map<String, Object>) obj;
                                    String nome = (String) item.get("nome");
                                    Long qtd = (Long) item.get("quantidade");

                                    TextView txt = new TextView(holder.itemView.getContext());
                                    txt.setText("- " + nome + ": " + (qtd != null ? qtd : 0) + "x");
                                    txt.setPadding(0, 8, 0, 8);
                                    holder.layoutDetalhes.addView(txt);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCodigo, txtStatus, txtData, txtResumo;
        Button btnDetalhar;
        LinearLayout layoutDetalhes;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtData = itemView.findViewById(R.id.txtData);
            txtResumo = itemView.findViewById(R.id.txtResumo);
            btnDetalhar = itemView.findViewById(R.id.btnDetalhar);
            layoutDetalhes = itemView.findViewById(R.id.layoutDetalhes);
        }
    }
}
