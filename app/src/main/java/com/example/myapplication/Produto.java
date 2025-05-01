package com.example.myapplication.modelo;

public class Produto {
    private String id;
    private String nome;
    private String descricao;
    private String preco;
    private String imagem;
    private int quantidade;  // Adicionado o campo quantidade

    public Produto() {} // Necessário para Firebase

    // Construtor modificado para incluir o campo quantidade
    public Produto(String id, String nome, String descricao, String preco, String imagem, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
        this.quantidade = quantidade;
    }

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

    // Método getter para a quantidade
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
