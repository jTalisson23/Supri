package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.modelo.Produto;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    private List<Produto> lista;
    private final Runnable onAtualizarResumo;

    public CarrinhoAdapter(List<Produto> lista, Runnable onAtualizarResumo) {
        this.lista = lista;
        this.onAtualizarResumo = onAtualizarResumo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = lista.get(position);
        holder.nome.setText(produto.getNome());
        holder.preco.setText("R$ " + produto.getPreco());
        holder.quantidade.setText(String.valueOf(produto.getQuantidade()));

        StorageReference ref = FirebaseStorage.getInstance().getReference().child("produtos/" + produto.getImagem());
        Glide.with(holder.context)
                .load(ref)
                .placeholder(R.drawable.placeholder)
                .into(holder.imagemProduto);

        holder.btnMais.setOnClickListener(v -> {
            produto.setQuantidade(produto.getQuantidade() + 1);
            CarrinhoSingleton.getInstance().atualizarQuantidade(produto.getId(), produto.getQuantidade());
            notifyItemChanged(position);
            onAtualizarResumo.run();
        });

        holder.btnMenos.setOnClickListener(v -> {
            if (produto.getQuantidade() > 1) {
                produto.setQuantidade(produto.getQuantidade() - 1);
                CarrinhoSingleton.getInstance().atualizarQuantidade(produto.getId(), produto.getQuantidade());
                notifyItemChanged(position);
                onAtualizarResumo.run();
            }
        });

        holder.btnLixeira.setOnClickListener(v -> {
            CarrinhoSingleton.getInstance().removerProduto(produto);
            lista.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, lista.size());
            onAtualizarResumo.run();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco, quantidade;
        ImageView imagemProduto, btnLixeira;
        Button btnMais, btnMenos;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            nome = itemView.findViewById(R.id.textNome);
            preco = itemView.findViewById(R.id.textPreco);
            quantidade = itemView.findViewById(R.id.textQuantidade);
            imagemProduto = itemView.findViewById(R.id.imageProduto);
            btnMais = itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            btnLixeira = itemView.findViewById(R.id.btnLixeira);
        }
    }
}
