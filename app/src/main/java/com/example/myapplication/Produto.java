package com.example.myapplication.modelo;

public class Produto {
    private String id;
    private String nome;
    private double preco;
    private int quantidade;
    private int imagem;

    public Produto() {}

    public Produto(String nome, double preco, int quantidade, int imagem) {
        this.id = nome + "_" + System.currentTimeMillis();
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagem = imagem;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }
    public int getImagem() { return imagem; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
