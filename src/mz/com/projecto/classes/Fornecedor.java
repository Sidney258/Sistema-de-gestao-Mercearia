package mz.com.projecto.classes;

import java.io.Serializable;

public class Fornecedor implements Serializable {

    private int id;
    private String nome;
    private String nuit;

    public Fornecedor(int id, String nome, String nuit) {
        this.id = id;
        this.nome = nome;
        this.nuit = nuit;
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

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

}
