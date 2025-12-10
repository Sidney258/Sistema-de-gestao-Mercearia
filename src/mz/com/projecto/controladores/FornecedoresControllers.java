package mz.com.projecto.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import mz.com.projecto.classes.Fornecedor;
import mz.com.projecto.helpers.Helpers;


public class FornecedoresControllers  {
    
    private List<Fornecedor> fornecedores = new ArrayList<>();
    private final String CAMINHO_FICHEIRO = "fornecedores.dat";

    public FornecedoresControllers() {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
    }

    public boolean adicionar(int id, String nome, String nuit) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);

        if (getFornecedorPorId(id) != null) {
            Helpers.mensagemAlertaErro("Já existe um fornecedor com este ID!");
            return false;
        }

        fornecedores.add(new Fornecedor(id, nome, nuit));
        guardarEmFicheiro(CAMINHO_FICHEIRO);
        return true;
    }

    public boolean actualizar(int id, String novoNome, String novoNuit) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);

        Fornecedor fornecedor = getFornecedorPorId(id);

        if (fornecedor == null) {
            Helpers.mensagemAlertaErro("Fornecedor não encontrado!");
            return false;
        }

        fornecedor.setNome(novoNome);
        fornecedor.setNuit(novoNuit);

        guardarEmFicheiro(CAMINHO_FICHEIRO);
        return true;
    }

    public Fornecedor getFornecedorPorId(int id) {
        for (Fornecedor f : fornecedores) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public void guardarEmFicheiro(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(fornecedores);
        } catch (IOException e) {
            Helpers.mensagemAlertaErro("Erro ao guardar fornecedores: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDoFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            fornecedores = (List<Fornecedor>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Caso o ficheiro não exista ainda
            fornecedores = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaErro("Erro ao carregar fornecedores: " + e.getMessage());
        }
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    
}
