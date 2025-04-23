package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProdutoAdaptador extends RecyclerView.Adapter<ProdutoAdaptador.ProdutoViewHolder> {

    private List<Produto> listaProdutos;

    public ProdutoAdaptador(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
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
        holder.textPreco.setText("Preço: R$ " + String.format("%.2f", produto.getPreco()));
        holder.textQuantidade.setText("Quantidade: " + produto.getQuantidade());
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textPreco, textQuantidade;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNome);
            textPreco = itemView.findViewById(R.id.textPreco);
            textQuantidade = itemView.findViewById(R.id.textQuantidade);
        }
    }
}
