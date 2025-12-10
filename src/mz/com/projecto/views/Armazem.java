package mz.com.projecto.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import mz.com.projecto.controladores.*;
import mz.com.projecto.classes.*;
import mz.com.projecto.helpers.Helpers;

public class Armazem extends JFrame implements ActionListener {

	private JTextField txtId, txtNome, txtPesquisarNome;
	private JLabel lbId, lbNome, lbPesquisa;
	private JButton btnAdicionar, btnActualizar, btnRemover, btnPesquisar;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JScrollPane scroll;

	private final String CAMINHO_FICHEIRO = "armazem.dat";

	ArmazemController storages = new ArmazemController();

	public Armazem() {
		setTitle("Armazem");
		setSize(700, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);

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
		lbPesquisa.setFont(new Font("Arial", Font.BOLD, 12));
		lbPesquisa.setForeground(Color.GRAY);
		lbPesquisa.setBounds(80, 170, 200, 25);
		add(lbPesquisa);

		JLabel lbPesquisaNome = new JLabel("Nome:");
		lbPesquisaNome.setBounds(80, 200, 50, 25);
		add(lbPesquisaNome);

		txtPesquisarNome = new JTextField();
		txtPesquisarNome.setBounds(130, 200, 200, 25);
		add(txtPesquisarNome);

		btnPesquisar = new JButton("pesquisar");
		btnPesquisar.setBounds(340, 200, 100, 25);
		add(btnPesquisar);

		modelo = new DefaultTableModel(new String[] { "Id", "Nome" }, 0);
		tabela = new JTable(modelo);
		scroll = new JScrollPane(tabela);
		scroll.setBounds(80, 240, 550, 130);
		add(scroll);

		storages.carregarDoFicheiro(CAMINHO_FICHEIRO);
		actualizarTabela();

		tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int linha = tabela.getSelectedRow();
				if (linha >= 0) {
					txtId.setText(modelo.getValueAt(linha, 0).toString());
					txtNome.setText(modelo.getValueAt(linha, 1).toString());
				}
			}
		});

		btnAdicionar.addActionListener(this);
		btnRemover.addActionListener(this);
		btnActualizar.addActionListener(this);
		btnPesquisar.addActionListener(this);
	}

	public boolean verificarCampos() {
		String id = txtId.getText();
		String nome = txtNome.getText();
		return nome.isEmpty() || id.isEmpty();
	}

	public void limparCampos() {
		txtId.setText(null);
		txtNome.setText(null);

	}

	public void adicionar() {
		if (verificarCampos()) {
			Helpers.mensagemAlertaErro("Preencha todos campos");
		} else {
			try {
				int id;
				String nome;

				id = Integer.parseInt(txtId.getText());
				nome = txtNome.getText();

				if (storages.adicionar(id, nome)) {
					storages.guardarEmFicheiro(CAMINHO_FICHEIRO);
					actualizarTabela();
					limparCampos();
					Helpers.mensagemAlertaInformacao("Armazem registado com sucesso");
				} else {
					Helpers.mensagemAlertaErro("Ja existe um armazem com esse codigos");
				}

			} catch (NumberFormatException e) {
				Helpers.mensagemAlertaErro("O id deve ser um numero inteiro");
			}

		}
	}

	public void actualizarTabela() {
		modelo.setRowCount(0);

		for (mz.com.projecto.classes.Armazem armazem : storages.getArmazem()) {
			modelo.addRow(new Object[] { armazem.getId(), armazem.getNome() });
		}
	}

	public void remover() {
		try {

			int id = Integer.valueOf(txtId.getText());
			storages.remover(id);
			actualizarTabela();

		} catch (Exception e) {
			Helpers.mensagemAlertaErro("digite um id do tipo numerico");
		}

	}

	public void actualizar() {
		try {

			int id = Integer.valueOf(txtId.getText());
			String nome = txtNome.getText();

			storages.actualizar(id, nome);
			actualizarTabela();

		} catch (Exception e) {
			Helpers.mensagemAlertaErro("digite um id do tipo numerico");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdicionar) {
			adicionar();
		} else if (e.getSource() == btnRemover) {
			remover();
		} else if (e.getSource() == btnActualizar) {
			actualizar();
		} else if (e.getSource() == btnPesquisar) {
			pesquisar();
		}
	}

	public void pesquisar() {
    	String termo = txtPesquisarNome.getText().trim().toLowerCase();

        if (termo.isEmpty()) {
            actualizarTabela();
            return;
        }

        List<mz.com.projecto.classes.Armazem> filtrados = new ArrayList<>();

        for (mz.com.projecto.classes.Armazem a : storages.getArmazem()) {
            if (a.getNome().toLowerCase().contains(termo)) {
                filtrados.add(a);
            }
        }

        modelo.setRowCount(0);
        for (mz.com.projecto.classes.Armazem a: filtrados) {
            modelo.addRow(new Object[]{
            	a.getId(),
            	a.getNome()
            });
        }
        
	}
    	
    	

}
