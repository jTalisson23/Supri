package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.modelo.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> lista;
    private Context context;

    public ProdutoAdapter(List<Produto> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_produto_lista, parent, false);
        return new ProdutoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = lista.get(position);
        holder.nome.setText(produto.getNome());
        holder.descricao.setText(produto.getDescricao());
        holder.preco.setText("R$ " + produto.getPreco());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, descricao, preco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txtNomeProduto);
            descricao = itemView.findViewById(R.id.txtDescricaoProduto);
            preco = itemView.findViewById(R.id.txtPrecoProduto);
        }
    }
}
