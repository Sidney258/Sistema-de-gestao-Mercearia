package mz.com.projecto.classes;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private Date dataRegisto;

    public Cliente(int id, String nome, String email, String telefone, Date dataRegisto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataRegisto = dataRegisto;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

}
