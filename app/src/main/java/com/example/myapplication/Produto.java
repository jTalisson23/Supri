package com.example.myapplication.modelo;

import com.example.myapplication.Categoria;

public class Produto {
    private String id;
    private String nome;
    private String descricao;
    private String preco;
    private String imagem;
    private int quantidade;
    private boolean selecionado; // ✅ NOVO

    private Categoria categoria;

    public Produto() {} // Necessário para Firebase

    public Produto(String id, String nome, String descricao, String preco, String imagem, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
        this.quantidade = quantidade;
        this.selecionado = false;
    }

    public Produto(String id, String nome, String descricao, String preco, String imagem, int quantidade, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
        this.quantidade = quantidade;
        this.selecionado = false;
        this.categoria = categoria;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getPreco() { return preco; }
    public void setPreco(String preco) { this.preco = preco; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public Categoria getCategoria(){
        return this.categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }
}