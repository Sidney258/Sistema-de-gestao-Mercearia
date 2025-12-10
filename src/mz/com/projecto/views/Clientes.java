package mz.com.projecto.views;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mz.com.projecto.helpers.Helpers;
import mz.com.projecto.classes.Cliente;
import mz.com.projecto.classes.Venda;
import mz.com.projecto.controladores.ClientControllers;

public class Clientes extends JFrame implements ActionListener {

    private JTextField txtId, txtNome, txtEmail, txtTelefone, txtPesquisa;
    private JLabel lblId, lblNome, lblEmail, lblTelefone;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnAdicionar, btnActualizar, btnRemover, btnPesquisa;

    private ClientControllers client = new ClientControllers();

    private final String CAMINHO_FICHEIRO = "clientes.dat";

    public Clientes() {
        setTitle("Clientes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel painelInputs = new JPanel(new GridLayout(4, 2));
        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 5, 5));
        JPanel painelPesquisa = new JPanel(new GridLayout(1, 3));
        painelPesquisa.setBorder(BorderFactory.createTitledBorder("Pesquisa aqui"));
        JPanel painelTabela = new JPanel();

        lblId = new JLabel("Id:");
        txtId = new JTextField(20);
        lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);
        lblEmail = new JLabel("E-mail:");
        txtEmail = new JTextField(20);
        lblTelefone = new JLabel("Telefone:");
        txtTelefone = new JTextField(20);

        painelInputs.add(lblId);
        painelInputs.add(txtId);
        painelInputs.add(lblNome);
        painelInputs.add(txtNome);
        painelInputs.add(lblEmail);
        painelInputs.add(txtEmail);
        painelInputs.add(lblTelefone);
        painelInputs.add(txtTelefone);

        txtPesquisa = new JTextField();
        btnPesquisa = new JButton("pesquisar", new ImageIcon("imagens/Iconleak-Atrous-Search.16.png"));
        painelPesquisa.add(new JLabel("Nome:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnPesquisa);

        btnAdicionar = new JButton("adicionar", new ImageIcon("imagens/Kyo-Tux-Delikate-Add.16.png"));
        btnActualizar = new JButton("actualizar", new ImageIcon("imagens/Custom-Icon-Design-Flatastic-1-Edit.16.png"));
        btnRemover = new JButton("remover", new ImageIcon("imagens/Ampeross-Qetto-2-Trash.16.png"));

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnActualizar);
        painelBotoes.add(btnRemover);

        modelo = new DefaultTableModel(new String[]{"id", "nome", "email", "telefone", "data"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        painelTabela.add(scroll);

        add(painelInputs);
        add(painelBotoes);
        add(painelPesquisa);
        add(painelTabela);

        client.carregarClientesDoFicheiro(CAMINHO_FICHEIRO);
        actualizarTabela();

        btnAdicionar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnRemover.addActionListener(this);
        btnPesquisa.addActionListener(this);
        
        

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    txtId.setText(modelo.getValueAt(linha, 0).toString());
                    txtNome.setText(modelo.getValueAt(linha, 1).toString());
                    txtEmail.setText(modelo.getValueAt(linha, 2).toString());
                    txtTelefone.setText(modelo.getValueAt(linha, 3).toString());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar) {
            adicionarCliente();
        }
        if (e.getSource() == btnActualizar) {
            actualizarCliente();
        }
        if (e.getSource() == btnRemover) {
            removerCliente();
        }
        if (e.getSource() == btnPesquisa) {
        	pesquisarClientes();
        }
    }

    public void adicionarCliente() {
        if (verificarCampos()) {
            Helpers.mensagemAlertaInformacao("Preencha todos os campos");
        } else {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                String telefone = txtTelefone.getText();
                String email = txtEmail.getText();
                Date dataRegisto = new Date();

                if (client.adicionar(id, nome, email, telefone, dataRegisto)) {
                    client.guardarClientesEmFicheiro(CAMINHO_FICHEIRO);
                    actualizarTabela();
                    Helpers.mensagemAlertaInformacao("Cliente adicionado com sucesso");
                    limparCampos();
                } else {
                    Helpers.mensagemAlertaErro("Já existe um cliente com esse ID");
                }

            } catch (NumberFormatException exception) {
                Helpers.mensagemAlertaErro("O ID deve ser um número inteiro");
            }
        }
    }

    public void actualizarCliente() {
        if (verificarCampos()) {
            Helpers.mensagemAlertaInformacao("Selecione um cliente e preencha os campos para actualizar");
        } else {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                String email = txtEmail.getText();
                String telefone = txtTelefone.getText();

                client.actualizar(id, nome, email, telefone);
                client.guardarClientesEmFicheiro(CAMINHO_FICHEIRO);
                actualizarTabela();
                limparCampos();
            } catch (NumberFormatException exception) {
                Helpers.mensagemAlertaErro("O ID deve ser um número inteiro");
            }
        }
    }

    public void removerCliente() {
        if (verificarCampos()) {
            Helpers.mensagemAlertaInformacao("Selecione um cliente e preencha os campos para actualizar");
        } else {
            try {
                int id = Integer.parseInt(txtId.getText());
                client.remover(id);
                client.guardarClientesEmFicheiro(CAMINHO_FICHEIRO);
                actualizarTabela();
            } catch (NumberFormatException e) {
                Helpers.mensagemAlertaErro("O ID deve ser um número inteiro");
            }
        }
    }

    public boolean verificarCampos() {
        String id = txtId.getText();
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        return nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || id.isEmpty();
    }

    public void limparCampos() {
        txtEmail.setText(null);
        txtId.setText(null);
        txtNome.setText(null);
        txtTelefone.setText(null);
    }

    public void actualizarTabela() {
        modelo.setRowCount(0);
        for (Cliente cliente : client.getClientes()) {
            modelo.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                Helpers.formatarData(cliente.getDataRegisto())
            });
        }
    }
    
    public void pesquisarClientes() {
        String termo = txtPesquisa.getText().trim().toLowerCase();

        if (termo.isEmpty()) {
            actualizarTabela();
            return;
        }

        List<Cliente> filtrados = new ArrayList<>();

        for (Cliente c : client.getClientes()) {
            if (c.getNome().toLowerCase().contains(termo)) {
                filtrados.add(c);
            }
        }

        modelo.setRowCount(0);
        for (Cliente cliente : filtrados) {
            modelo.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                Helpers.formatarData(cliente.getDataRegisto())
            });
        }

        if (filtrados.isEmpty()) {
            Helpers.mensagemAlertaInformacao("Nenhum cliente encontrado.");
        }
    }

    
}
