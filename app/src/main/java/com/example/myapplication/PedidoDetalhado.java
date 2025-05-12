
package com.example.myapplication;

public class PedidoDetalhado {
    private String codigo;
    private String status;
    private String data;
    private String ano;
    private double valorTotal;
    private int qtdTotal;
    private boolean expandido;

    public PedidoDetalhado(String codigo, String status, String data, String ano, double valorTotal, int qtdTotal) {
        this.codigo = codigo;
        this.status = status;
        this.data = data;
        this.ano = ano;
        this.valorTotal = valorTotal;
        this.qtdTotal = qtdTotal;
        this.expandido = false;
    }

    public String getCodigo() { return codigo; }
    public String getStatus() { return status; }
    public String getData() { return data; }
    public String getAno() { return ano; }
    public double getValorTotal() { return valorTotal; }
    public int getQtdTotal() { return qtdTotal; }
    public boolean isExpandido() { return expandido; }
    public void setExpandido(boolean expandido) { this.expandido = expandido; }
}
