package mz.com.projecto.classes;

import java.io.Serializable;

public class Produto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private Fornecedor fornecedor;
    private int quantidade;
    private int minimoStock;
    private int codigoArmazem;
    private double preco;

    public Produto(int id, String nome, String descricao, Fornecedor fornecedor, int quantidade, int minimoStock, int codigoArmazem, double preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.minimoStock = minimoStock;
        this.codigoArmazem = codigoArmazem;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getMinimoStock() {
        return minimoStock;
    }

    public int getCodigoArmazem() {
        return codigoArmazem;
    }

    public void setCodigoArmazem(int codigoArmazem) {
        this.codigoArmazem = codigoArmazem;
    }

    public void setMinimoStock(int minimoStock) {
        this.minimoStock = minimoStock;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void adicionar(int quantidade) {
        this.quantidade += quantidade;
    }
    
    public void diminuirQuantidade(int quantidade) {
        this.quantidade = this.quantidade - quantidade;
    }
        
}
