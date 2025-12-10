package mz.com.projecto.views;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import mz.com.projecto.classes.Produto;
import mz.com.projecto.classes.Venda;
import mz.com.projecto.helpers.Helpers;

public class ProdutoStockBaixo extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JScrollPane scroll;
    
    private final String CAMINHO_FICHEIRO = "produtos.dat";
    private List<Produto> produtosStockBaixo = new ArrayList<>();
    
    public ProdutoStockBaixo() {
        setTitle("Produtos com baixo stock");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        modelo = new DefaultTableModel(new String[]{"produto", "quantidade"}, 0);
        tabela = new JTable(modelo);
        scroll = new JScrollPane(tabela);
        scroll.setBounds(50, 80, 680, 330);
        add(scroll);
        
        carregarProdutos();
        preencherTabela(produtosStockBaixo);
    }

    private void preencherTabela(List<Produto> lista) {
        modelo.setRowCount(0);
        for (Produto produto : lista) {
            if (produto.getQuantidade() < produto.getMinimoStock()) {
                modelo.addRow(new Object[]{
                    produto.getNome(),
                    produto.getQuantidade()
                });
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void carregarProdutos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_FICHEIRO))) {
            produtosStockBaixo = (List<Produto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaInformacao("Nenhum histórico encontrado ou erro ao carregar.");
        }
    }
}


