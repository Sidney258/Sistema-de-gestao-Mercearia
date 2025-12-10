package mz.com.projecto.controladores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import mz.com.projecto.classes.Produto;
import mz.com.projecto.classes.Fornecedor;
import mz.com.projecto.helpers.Helpers;

public class ProdutosControllers implements Serializable {

    private List<Produto> produtos = new ArrayList<>();
    private final String CAMINHO_FICHEIRO = "produtos.dat";

    public ProdutosControllers() {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public boolean adicionar(int id, String nome, String descricao, Fornecedor fornecedor,
            int quantidade, int minimoStock, int codigoArmazem, double preco) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);

        if (encontrar(id) == null) {
            Produto novo = new Produto(id, nome, descricao, fornecedor, quantidade, minimoStock, codigoArmazem, preco);
            produtos.add(novo);
            guardarEmFicheiro(CAMINHO_FICHEIRO);
            return true;
        } else {
            Helpers.mensagemAlertaErro("Já existe um produto com este ID!");
            return false;
        }
    }

    public Produto encontrar(int id) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void adicionarQuantidade(int id, int quantidade) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        Produto p = encontrar(id);
        if (p != null) {
            p.adicionar(quantidade);
            guardarEmFicheiro(CAMINHO_FICHEIRO);
            Helpers.mensagemAlertaInformacao("Quantidade actualizada com sucesso!");
        } else {
            Helpers.mensagemAlertaErro("Produto não encontrado!");
        }
    }

    public void remover(int id) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        Produto p = encontrar(id);
        if (p != null) {
            produtos.remove(p);
            guardarEmFicheiro(CAMINHO_FICHEIRO);
            Helpers.mensagemAlertaInformacao("Produto removido com sucesso!");
        } else {
            Helpers.mensagemAlertaErro("Produto não encontrado!");
        }
    }

    public void actualizar(int id, String nome, String descricao, Fornecedor fornecedor,
            int quantidade, int minimoStock, int codigoArmazem, double preco) {
        carregarDoFicheiro(CAMINHO_FICHEIRO);
        Produto p = encontrar(id);
        if (p != null) {
            p.setNome(nome);
            p.setDescricao(descricao);
            p.setFornecedor(fornecedor);
            p.setQuantidade(quantidade);
            p.setMinimoStock(minimoStock);
            p.setCodigoArmazem(codigoArmazem);
            p.setPreco(preco);

            guardarEmFicheiro(CAMINHO_FICHEIRO);
            Helpers.mensagemAlertaInformacao("Produto atualizado com sucesso!");
        } else {
            Helpers.mensagemAlertaErro("Produto não encontrado!");
        }
    }

    public void guardarEmFicheiro(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(produtos);
        } catch (IOException e) {
            Helpers.mensagemAlertaErro("Erro ao guardar produtos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDoFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            produtos = (List<Produto>) ois.readObject();
        } catch (FileNotFoundException e) {
            produtos = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaErro("Erro ao carregar produtos: " + e.getMessage());
        }
    }
}
