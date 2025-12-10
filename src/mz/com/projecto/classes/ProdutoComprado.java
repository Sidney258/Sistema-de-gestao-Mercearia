package mz.com.projecto.classes;

import java.io.Serializable;

public class ProdutoComprado implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private int quantidade;

    public ProdutoComprado(int id, String nome, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
