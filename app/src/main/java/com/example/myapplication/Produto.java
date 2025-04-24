package com.example.myapplication;

public class Produto {
    private String nome;
    private double preco;
    private int quantidade;
    private int imagemResid;

    public Produto(String nome, double preco, int quantidade, int imagemResid) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagemResid = imagemResid;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }
    public int getImagemResid() { return imagemResid; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
