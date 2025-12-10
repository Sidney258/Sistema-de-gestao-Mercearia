package mz.com.projecto.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import mz.com.projecto.classes.Produto;
import mz.com.projecto.classes.ProdutoComprado;
import mz.com.projecto.helpers.Helpers;

public class ProdutoCompradoControllers {

    ArrayList<ProdutoComprado> produtosComprados = new ArrayList<>();
    private final String CAMINHO_FICHEIRO = "produtosComprados.dat";
    
    public ProdutoCompradoControllers() {
        
    }

    public ArrayList<ProdutoComprado> getProdutosComprados() {
        return produtosComprados;
    }
    
    public void adicionar(int id, String nome, int quantidade) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        if (encontrar(id) == null) {
            produtosComprados.add(new ProdutoComprado(id, nome, quantidade));
        } else {
            ProdutoComprado produto = encontrar(id);
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        }
    }
    
    public void guardarEmFicheiro(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(produtosComprados);
        } catch (IOException e) {
            Helpers.mensagemAlertaErro("Erro ao guardar produtos: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void carregarDoFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            produtosComprados = (ArrayList<ProdutoComprado>) (List<ProdutoComprado>) ois.readObject();
        } catch (FileNotFoundException e) {
            produtosComprados = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaErro("Erro: " + e.getMessage());
        }
    }
    
    public ProdutoComprado encontrar(int id) {
        for (ProdutoComprado produto : produtosComprados) {
            if (produto.getId() == id) { 
                return produto;
            }
        }
        return null;
    }

}
