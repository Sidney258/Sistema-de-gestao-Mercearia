package mz.com.projecto.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;

import mz.com.projecto.classes.Fornecedor;
import mz.com.projecto.classes.Produto;
import mz.com.projecto.controladores.FornecedoresControllers;
import mz.com.projecto.helpers.Helpers;

public class Fornecedores extends JFrame implements ActionListener {

    private JLabel lbId, lbNome, lbNuit, lbPesquisa;
    private JTextField txtId, txtNome, txtNuit, txtPesquisarNome;
    private JButton btnAdicionar, btnActualizar, btnRemover, btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JScrollPane scroll;

    private FornecedoresControllers controller = new FornecedoresControllers();

    public Fornecedores() {
        setTitle("Fornecedores");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        controller = new FornecedoresControllers(); 
        controller.getFornecedores(); 

        lbId = new JLabel("Id:");
        lbId.setBounds(80, 60, 100, 25);
        add(lbId);

        txtId = new JTextField();
        txtId.setBounds(180, 60, 200, 25);
        add(txtId);

        lbNome = new JLabel("Nome:");
        lbNome.setBounds(80, 95, 100, 25);
        add(lbNome);

        txtNome = new JTextField();
        txtNome.setBounds(180, 95, 200, 25);
        add(txtNome);

        lbNuit = new JLabel("Nuit:");
        lbNuit.setBounds(80, 130, 100, 25);
        add(lbNuit);

        txtNuit = new JTextField();
        txtNuit.setBounds(180, 130, 200, 25);
        add(txtNuit);

        btnAdicionar = new JButton("adicionar", new ImageIcon("imagens/Kyo-Tux-Delikate-Add.16.png"));
        btnAdicionar.setBounds(420, 60, 120, 30);
        add(btnAdicionar);

        btnActualizar = new JButton("actualizar", new ImageIcon("imagens/Custom-Icon-Design-Flatastic-1-Edit.16.png"));
        btnActualizar.setBounds(420, 95, 120, 30);
        add(btnActualizar);

        btnRemover = new JButton("remover", new ImageIcon("imagens/Ampeross-Qetto-2-Trash.16.png"));
        btnRemover.setBounds(420, 130, 120, 30);
        add(btnRemover);

        lbPesquisa = new JLabel("Pesquisa aqui");
        lbPesquisa.setBounds(80, 180, 200, 25);
        add(lbPesquisa);

        JLabel lbPesquisaNome = new JLabel("Nome:");
        lbPesquisaNome.setBounds(80, 210, 50, 25);
        add(lbPesquisaNome);

        txtPesquisarNome = new JTextField();
        txtPesquisarNome.setBounds(130, 210, 200, 25);
        add(txtPesquisarNome);

        btnPesquisar = new JButton("pesquisar");
        btnPesquisar.setBounds(340, 210, 100, 25);
        add(btnPesquisar);


        modelo = new DefaultTableModel(new String[]{"Id", "Nome", "Nuit"}, 0);
        tabela = new JTable(modelo);
        scroll = new JScrollPane(tabela);
        scroll.setBounds(80, 250, 550, 130);
        add(scroll);
        
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    txtId.setText(modelo.getValueAt(linha, 0).toString());
                    txtNome.setText(modelo.getValueAt(linha, 1).toString());
                    txtNuit.setText(modelo.getValueAt(linha, 2).toString());
                }
            }
        });

        btnAdicionar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnRemover.addActionListener(this);
        btnPesquisar.addActionListener(this);

        actualizarTabela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar) {
            adicionarFornecedor();
        } else if (e.getSource() == btnActualizar) {
            actualizarFornecedor();
        } else if (e.getSource() == btnRemover) {
            removerFornecedor();
        } else if (e.getSource() == btnPesquisar) {
        	pesquisarFornecedores();
        }
    }
    
    public void pesquisarFornecedores() {
    	String termo = txtPesquisarNome.getText().trim().toLowerCase();

        if (termo.isEmpty()) {
            actualizarTabela();
            return;
        }

        List<Fornecedor> filtrados = new ArrayList<>();

        for (Fornecedor f : controller.getFornecedores()) {
            if (f.getNome().toLowerCase().contains(termo)) {
                filtrados.add(f);
            }
        }

        modelo.setRowCount(0);
        for (Fornecedor f: filtrados) {
            modelo.addRow(new Object[]{
            	f.getId(),
            	f.getNome(),
            	f.getNuit()
            });
        }

        if (filtrados.isEmpty()) {
            Helpers.mensagemAlertaInformacao("Nenhum Fornecedor encontrado.");
        }
    }

    private void adicionarFornecedor() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String nuit = txtNuit.getText();

            if (nome.isEmpty() || nuit.isEmpty()) {
                Helpers.mensagemAlertaErro("Preencha todos os campos!");
                return;
            }

            if (controller.adicionar(id, nome, nuit)) {
                Helpers.mensagemAlertaInformacao("Fornecedor adicionado com sucesso!");
                limparCampos();
                actualizarTabela();
            }

        } catch (NumberFormatException ex) {
            Helpers.mensagemAlertaErro("O ID deve ser um número inteiro!");
        }
    }

    private void actualizarFornecedor() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String nuit = txtNuit.getText();

            if (controller.actualizar(id, nome, nuit)) {
                Helpers.mensagemAlertaInformacao("Fornecedor actualizado com sucesso!");
                limparCampos();
                actualizarTabela();
            }

        } catch (NumberFormatException ex) {
            Helpers.mensagemAlertaErro("O ID deve ser um número inteiro!");
        }
    }

    private void removerFornecedor() {
        try {
            int id = Integer.parseInt(txtId.getText());
            List<Fornecedor> lista = controller.getFornecedores();
            Fornecedor remover = null;

            for (Fornecedor f : lista) {
                if (f.getId() == id) {
                    remover = f;
                    break;
                }
            }

            if (remover != null) {
                lista.remove(remover);
                Helpers.mensagemAlertaInformacao("Fornecedor removido com sucesso!");
                controller.getFornecedores().remove(remover);
                
                FornecedoresControllers fornecedoresControllers = new FornecedoresControllers(); 
                actualizarTabela();
            } else {
                Helpers.mensagemAlertaErro("Fornecedor com esse ID não encontrado!");
            }

        } catch (NumberFormatException ex) {
            Helpers.mensagemAlertaErro("O ID deve ser um número inteiro!");
        }
    }

    private void actualizarTabela() {
        modelo.setRowCount(0);
        for (Fornecedor f : controller.getFornecedores()) {
            modelo.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                f.getNuit()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtNuit.setText("");
    }
}
