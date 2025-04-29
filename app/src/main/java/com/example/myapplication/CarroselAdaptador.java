package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarroselAdaptador extends RecyclerView.Adapter<CarroselAdaptador.CarroselViewHolder> {

    private final List<Integer> imagens;
    private final Context context;

    public CarroselAdaptador(Context context, List<Integer> imagens) {
        this.context = context;
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public CarroselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrosel, parent, false);
        return new CarroselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarroselViewHolder holder, int position) {
        holder.imageView.setImageResource(imagens.get(position));
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }

    static class CarroselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CarroselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageCarrosel);
        }
    }
}
