package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.modelo.Produto;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private final List<Produto> lista;
    private final Context context;

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

        // Carrega imagem do Firebase Storage com Glide
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("produtos/" + produto.getImagem());

        Glide.with(context)
                .load(storageReference)
                .placeholder(R.drawable.placeholder)
                .into(holder.imagemProduto);

        // Evita bug de reciclagem: remove listener anterior
        holder.checkboxSelecionar.setOnCheckedChangeListener(null);

        // Marca o checkbox com o estado atual do produto
        holder.checkboxSelecionar.setChecked(produto.isSelecionado());

        // Listener atualizado
        holder.checkboxSelecionar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            produto.setSelecionado(isChecked);
            if (isChecked && produto.getQuantidade() < 1) {
                produto.setQuantidade(1); // Define quantidade padrão
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, descricao, preco;
        ImageView imagemProduto;
        CheckBox checkboxSelecionar;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txtNomeProduto);
            descricao = itemView.findViewById(R.id.txtDescricaoProduto);
            preco = itemView.findViewById(R.id.txtPrecoProduto);
            imagemProduto = itemView.findViewById(R.id.imgProduto);
            checkboxSelecionar = itemView.findViewById(R.id.checkboxSelecionar);
        }
    }

    // Método público que retorna todos os produtos selecionados
    public List<Produto> getSelecionados() {
        List<Produto> selecionados = new ArrayList<>();
        for (Produto p : lista) {
            if (p.isSelecionado()) {
                selecionados.add(p);
            }
        }
        return selecionados;
    }
}
