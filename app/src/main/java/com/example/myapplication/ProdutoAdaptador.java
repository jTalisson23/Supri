package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Produto;
import java.util.List;

public class ProdutoAdaptador extends RecyclerView.Adapter<ProdutoAdaptador.ProdutoViewHolder> {

    private List<Produto> listaProdutos;
    private OnProdutoAtualizadoListener listener;

    public ProdutoAdaptador(List<Produto> listaProdutos, OnProdutoAtualizadoListener listener) {
        this.listaProdutos = listaProdutos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.textNome.setText(produto.getNome());
        holder.textPreco.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
        holder.textQuantidade.setText(String.valueOf(produto.getQuantidade()));

        holder.btnMais.setOnClickListener(v -> {
            produto.setQuantidade(produto.getQuantidade() + 1);
            notifyItemChanged(position);
            if (listener != null) listener.onAtualizarTotais(listaProdutos);
        });

        holder.btnMenos.setOnClickListener(v -> {
            if (produto.getQuantidade() > 1) {
                produto.setQuantidade(produto.getQuantidade() - 1);
                notifyItemChanged(position);
                if (listener != null) listener.onAtualizarTotais(listaProdutos);
            }
        });

        holder.btnLixeira.setOnClickListener(v -> {
            listaProdutos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaProdutos.size());
            if (listener != null) listener.onAtualizarTotais(listaProdutos);
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textPreco, textQuantidade;
        Button btnMais, btnMenos;
        ImageView btnLixeira;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNome);
            textPreco = itemView.findViewById(R.id.textPreco);
            textQuantidade = itemView.findViewById(R.id.textQuantidade);
            btnMais = itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            btnLixeira = itemView.findViewById(R.id.btnLixeira);
        }
    }

    public interface OnProdutoAtualizadoListener {
        void onAtualizarTotais(List<Produto> produtos);
    }
}
