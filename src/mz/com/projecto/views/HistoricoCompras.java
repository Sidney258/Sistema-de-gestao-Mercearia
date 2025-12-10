package mz.com.projecto.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import mz.com.projecto.classes.ProdutoComprado;
import mz.com.projecto.controladores.ProdutoCompradoControllers;
import mz.com.projecto.helpers.Helpers;

public class HistoricoCompras extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JScrollPane scroll;
    private ProdutoCompradoControllers controller;

    public HistoricoCompras() {
        setTitle("Produtos Mais Comprados");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel titulo = new JLabel("Top 5 Produtos Mais Comprados");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(260, 20, 350, 30);
        add(titulo);

        modelo = new DefaultTableModel(new String[]{"Produto", "Quantidade Comprada"}, 0);
        tabela = new JTable(modelo);
        tabela.setEnabled(false);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));

        scroll = new JScrollPane(tabela);
        scroll.setBounds(60, 80, 670, 320);
        add(scroll);

        carregarDados();
    }

    private void carregarDados() {
        controller = new ProdutoCompradoControllers();
        controller.carregarDoFicheiro("produtosComprados.dat");

        ArrayList<ProdutoComprado> lista = controller.getProdutosComprados();

        if (lista.isEmpty()) {
            Helpers.mensagemAlertaInformacao("Ainda nao ha vendas registadas");
            modelo.setRowCount(0);
            return;
        }

        lista.sort(Comparator.comparingInt(ProdutoComprado::getQuantidade).reversed());

        modelo.setRowCount(0);

        int limite = Math.min(5, lista.size());
        for (int i = 0; i < limite; i++) {
            ProdutoComprado p = lista.get(i);
            modelo.addRow(new Object[]{
                p.getNome(),
                p.getQuantidade()});
        }
    }

}
