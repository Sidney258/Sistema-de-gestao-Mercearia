package mz.com.projecto.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import mz.com.projecto.classes.Venda;
import mz.com.projecto.classes.Cliente;
import mz.com.projecto.helpers.Helpers;

public class HistoricoVendas extends JFrame implements ActionListener {

    private JLabel lblPesquisa;
    private JTextField txtPesquisa;
    private JButton btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JScrollPane scroll;

    private final String CAMINHO_VENDAS = "vendas.dat";
    private List<Venda> historico = new ArrayList<>();

    public HistoricoVendas() {
        setTitle("Histórico de Vendas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        lblPesquisa = new JLabel("Cliente:");
        lblPesquisa.setBounds(50, 30, 80, 25);
        add(lblPesquisa);

        txtPesquisa = new JTextField();
        txtPesquisa.setBounds(120, 30, 200, 25);
        add(txtPesquisa);

        btnPesquisar = new JButton("Pesquisar", new ImageIcon("imagens/Iconleak-Atrous-Search.16.png"));
        btnPesquisar.setBounds(340, 30, 120, 25);
        add(btnPesquisar);

        modelo = new DefaultTableModel(new String[]{"Cliente", "Total da Venda", "Data"}, 0);
        tabela = new JTable(modelo);
        scroll = new JScrollPane(tabela);
        scroll.setBounds(50, 80, 680, 330);
        add(scroll);

        btnPesquisar.addActionListener(this);

        carregarHistorico();
        preencherTabela(historico);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPesquisar) {
            String termo = txtPesquisa.getText().trim().toLowerCase();
            if (termo.isEmpty()) {
                preencherTabela(historico);
            } else {
                List<Venda> filtradas = new ArrayList<>();
                for (Venda v : historico) {
                    Cliente c = v.getCliente();
                    if (c != null && c.getNome().toLowerCase().contains(termo)) {
                        filtradas.add(v);
                    }
                }
                preencherTabela(filtradas);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarHistorico() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_VENDAS))) {
            historico = (List<Venda>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaInformacao("Nenhum histórico encontrado ou erro ao carregar.");
        }
    }

    public void preencherTabela(List<Venda> lista) {
        modelo.setRowCount(0);
        for (Venda v : lista) {
            modelo.addRow(new Object[]{
                v.getCliente().getNome(),
                Helpers.formatarDinheiro(v.total()),
               Helpers.formatarData(v.getDataVenda())
            });
        }
    }
}
