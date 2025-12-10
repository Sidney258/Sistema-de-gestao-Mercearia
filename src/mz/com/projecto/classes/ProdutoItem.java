package mz.com.projecto.classes;

import java.io.Serializable;

public class ProdutoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private Produto produtoItem;
    private int quantidade;

    public ProdutoItem(int id, Produto produtoItem, int quantidade) {
        this.id = id;
        this.produtoItem = produtoItem;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProdutoItem() {
        return produtoItem;
    }

    public void setProdutoItem(Produto produtoItem) {
        this.produtoItem = produtoItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionar(int quantidade) {
        this.quantidade += quantidade;
    }
    
    public double subtotal() {
        return getProdutoItem().getPreco() * quantidade;
    }
    
    public double iva() {
        return getProdutoItem().getPreco() * 0.17;
    }

}
