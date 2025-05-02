package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.modelo.Produto;

public class CarrinhoSingleton {
    private static CarrinhoSingleton instance;
    private final List<Produto> produtos = new ArrayList<>();

    private CarrinhoSingleton() {}

    public static synchronized CarrinhoSingleton getInstance() {
        if (instance == null) {
            instance = new CarrinhoSingleton();
        }
        return instance;
    }

    public void adicionarProduto(Produto novo) {
        for (Produto p : produtos) {
            if (p.getId().equals(novo.getId())) {
                p.setQuantidade(p.getQuantidade() + novo.getQuantidade());
                return;
            }
        }
        produtos.add(novo);
    }

    public void setProdutosSelecionados(List<Produto> selecionados) {
        for (Produto novo : selecionados) {
            adicionarProduto(novo); // respeita ID e soma quantidade
        }
    }

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos); // retorno seguro
    }

    public void removerProduto(Produto produto) {
        produtos.removeIf(p -> p.getId().equals(produto.getId()));
    }

    public void limparCarrinho() {
        produtos.clear();
    }

    public void atualizarQuantidade(String id, int novaQtd) {
        for (Produto p : produtos) {
            if (p.getId().equals(id)) {
                p.setQuantidade(Math.max(1, novaQtd));
                break;
            }
        }
    }
}
