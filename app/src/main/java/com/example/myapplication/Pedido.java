package com.example.myapplication.modelo;

import java.util.List;
import com.example.myapplication.modelo.Produto;

public class Pedido {
    private String userId;
    private List<Produto> produtos;
    private long timestamp;

    public Pedido() {} // Construtor vazio para Firebase

    public Pedido(String userId, List<Produto> produtos, long timestamp) {
        this.userId = userId;
        this.produtos = produtos;
        this.timestamp = timestamp;
    }

    public String getUserId() { return userId; }
    public List<Produto> getProdutos() { return produtos; }
    public long getTimestamp() { return timestamp; }
}

