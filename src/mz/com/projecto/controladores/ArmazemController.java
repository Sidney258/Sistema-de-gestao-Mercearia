package mz.com.projecto.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import mz.com.projecto.helpers.Helpers;
import mz.com.projecto.classes.Armazem;
import mz.com.projecto.classes.Cliente;

public class ArmazemController {
    
    private List<Armazem> armazem = new ArrayList<>();
    
    private final String CAMINHO_FICHEIRO = "armazem.dat";

    public ArmazemController() {
    }
    
    public List<Armazem> getArmazem() {
        return armazem;
    }
    
    public void actualizar(int id, String nome) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        if (getArmazem(id) != null) {
            Armazem armazem = getArmazem(id);
            armazem.setNome(nome);
            
            guardarEmFicheiro(CAMINHO_FICHEIRO);
            Helpers.mensagemAlertaInformacao("Armazem editado com sucesso");
        }
            
    }
    
    public boolean adicionar(int id, String nome) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        if (getArmazem(id) == null) {
            armazem.add(new Armazem(id, nome));
            guardarEmFicheiro(CAMINHO_FICHEIRO);
            return true;
        }
        return false;
    }
    
    public void remover(int id) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        if (getArmazem(id) != null) {
            Armazem storage = getArmazem(id);
            armazem.remove(storage);
            
            guardarEmFicheiro(CAMINHO_FICHEIRO);
            
            Helpers.mensagemAlertaInformacao("Armazem removido com sucesso");
        }
    }
    
    public Armazem getArmazem(int id) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        for (int i = 0; i < armazem.size(); i++) {
            if (armazem.get(i).getId() == id) {
                return armazem.get(i);
            }
        }
        return null;
    }
    
        public void guardarEmFicheiro(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(armazem);
//            Helpers.mensagemAlertaInformacao("Clientes guardados com sucesso em " + caminho);
        } catch (IOException e) {
            Helpers.mensagemAlertaErro("Erro ao guardar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDoFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            armazem = (List<Armazem>) ois.readObject();
//            Helpers.mensagemAlertaInformacao("Clientes carregados com sucesso de " + caminho);
        } catch (FileNotFoundException e) {
            Helpers.mensagemAlertaInformacao("Nenhum ficheiro encontrado, iniciando lista vazia.");
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaErro("Erro ao carregar clientes: " + e.getMessage());
        }
    }
        
}
