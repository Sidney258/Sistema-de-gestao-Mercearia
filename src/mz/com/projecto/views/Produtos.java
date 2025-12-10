package mz.com.projecto.views;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mz.com.projecto.classes.Armazem;
import mz.com.projecto.classes.Cliente;
import mz.com.projecto.classes.Fornecedor;
import mz.com.projecto.classes.Produto;
import mz.com.projecto.controladores.ArmazemController;
import mz.com.projecto.controladores.FornecedoresControllers;
import mz.com.projecto.controladores.ProdutosControllers;
import mz.com.projecto.helpers.Helpers;

public class Produtos extends JFrame implements ActionListener {

	private JLabel lbId, lbNome, lbArmazem, lbQuantidade, lbStockMinimo, lbFornecedor, lbPreco, lbPesquisa;
	private JTextField txtId, txtNome, txtQuantidade, txtStockMinimo, txtPreco, txtPesquisarNome;
	private JComboBox<String> comboArmazem, comboFornecedor;
	private JButton btnAdicionar, btnActualizar, btnRemover, btnPesquisar, btnAdicionarQuantidade;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JScrollPane scroll;

	private ArmazemController armazemController = new ArmazemController();
	private FornecedoresControllers fornecedoresController = new FornecedoresControllers();
	private ProdutosControllers produtosController = new ProdutosControllers();

	public Produtos() {
		setTitle("Produtos");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);

		lbId = new JLabel("Id:");
		lbId.setBounds(50, 40, 100, 25);
		add(lbId);

		txtId = new JTextField();
		txtId.setBounds(150, 40, 200, 25);
		add(txtId);

		lbNome = new JLabel("Nome:");
		lbNome.setBounds(50, 75, 100, 25);
		add(lbNome);

		txtNome = new JTextField();
		txtNome.setBounds(150, 75, 200, 25);
		add(txtNome);

		lbArmazem = new JLabel("Armazem:");
		lbArmazem.setBounds(50, 110, 100, 25);
		add(lbArmazem);

		comboArmazem = new JComboBox<>(new String[] { "Escolha uma opcao" });
		comboArmazem.setBounds(150, 110, 200, 25);
		add(comboArmazem);

		lbQuantidade = new JLabel("Quantidade:");
		lbQuantidade.setBounds(400, 40, 100, 25);
		add(lbQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(500, 40, 200, 25);
		add(txtQuantidade);

		lbStockMinimo = new JLabel("Stock Mínimo:");
		lbStockMinimo.setBounds(400, 75, 100, 25);
		add(lbStockMinimo);

		txtStockMinimo = new JTextField();
		txtStockMinimo.setBounds(500, 75, 200, 25);
		txtStockMinimo.setText(""+5);
		txtStockMinimo.setEditable(false);
		add(txtStockMinimo);

		lbFornecedor = new JLabel("Fornecedor:");
		lbFornecedor.setBounds(400, 110, 100, 25);
		add(lbFornecedor);

		comboFornecedor = new JComboBox<>(new String[] { "Escolha uma opcao" });
		comboFornecedor.setBounds(500, 110, 200, 25);
		add(comboFornecedor);

		lbPreco = new JLabel("Preço:");
		lbPreco.setBounds(50, 145, 100, 25);
		add(lbPreco);

		txtPreco = new JTextField();
		txtPreco.setBounds(150, 145, 200, 25);
		add(txtPreco);

		btnAdicionar = new JButton("adicionar", new ImageIcon("imagens/Kyo-Tux-Delikate-Add.16.png"));
		btnAdicionar.setBounds(500, 145, 120, 30);
		add(btnAdicionar);

		btnActualizar = new JButton("actualizar", new ImageIcon("imagens/Custom-Icon-Design-Flatastic-1-Edit.16.png"));
		btnActualizar.setBounds(630, 145, 120, 30);
		add(btnActualizar);

		btnRemover = new JButton("remover", new ImageIcon("imagens/Ampeross-Qetto-2-Trash.16.png"));
		btnRemover.setBounds(500, 180, 120, 30);
		add(btnRemover);

		btnAdicionarQuantidade = new JButton("adicionar stock");
		btnAdicionarQuantidade.setBounds(630, 180, 120, 30);
		add(btnAdicionarQuantidade);

		lbPesquisa = new JLabel("Pesquisa aqui");
		lbPesquisa.setBounds(50, 200, 200, 25);
		add(lbPesquisa);

		JLabel lbPesquisaNome = new JLabel("Nome:");
		lbPesquisaNome.setBounds(50, 230, 50, 25);
		add(lbPesquisaNome);

		txtPesquisarNome = new JTextField();
		txtPesquisarNome.setBounds(100, 230, 200, 25);
		add(txtPesquisarNome);

		btnPesquisar = new JButton("pesquisar", new ImageIcon("imagens/Iconleak-Atrous-Search.16.png"));
		btnPesquisar.setBounds(310, 230, 100, 25);
		add(btnPesquisar);

		modelo = new DefaultTableModel(
				new String[] { "Id", "Nome", "Armazem", "Fornecedor", "Quantidade", "Stock Mínimo", "Preço" }, 0);
		tabela = new JTable(modelo);
		scroll = new JScrollPane(tabela);
		scroll.setBounds(50, 270, 700, 150);
		add(scroll);

		carregarArmazensNoComboBox();
		carregarFornecedores();
		carregarProdutosNaTabela();

		tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int linha = tabela.getSelectedRow();
				if (linha >= 0) {
					txtId.setText(modelo.getValueAt(linha, 0).toString());
					txtNome.setText(modelo.getValueAt(linha, 1).toString());
					txtQuantidade.setText(modelo.getValueAt(linha, 4).toString());
					txtStockMinimo.setText(modelo.getValueAt(linha, 5).toString());
					txtPreco.setText(modelo.getValueAt(linha, 6).toString());
				}
			}
		});

		btnAdicionar.addActionListener(this);
		btnActualizar.addActionListener(this);
		btnRemover.addActionListener(this);
		btnAdicionarQuantidade.addActionListener(this);
		btnPesquisar.addActionListener(this);
	}

	public void carregarArmazensNoComboBox() {
		comboArmazem.removeAllItems();
		armazemController.carregarDoFicheiro("armazem.dat");
		List<Armazem> lista = armazemController.getArmazem();

		if (lista.isEmpty()) {
			comboArmazem.addItem("Nenhum armazem registado");
		} else {
			comboArmazem.addItem("Escolha uma opção");
			for (Armazem a : lista) {
				comboArmazem.addItem(a.getNome());
			}
		}
	}

	public void carregarFornecedores() {
		comboFornecedor.removeAllItems();
		comboFornecedor.addItem("Escolha uma opcao");
		fornecedoresController.carregarDoFicheiro("fornecedores.dat");

		List<Fornecedor> listaFornecedores = fornecedoresController.getFornecedores();
		for (Fornecedor f : listaFornecedores) {
			comboFornecedor.addItem(f.getNome());
		}
	}

	public void carregarProdutosNaTabela() {
		modelo.setRowCount(0);
		List<Produto> lista = produtosController.getProdutos();
		for (Produto p : lista) {
			modelo.addRow(new Object[] { p.getId(), p.getNome(), p.getCodigoArmazem(),
					(p.getFornecedor() != null ? p.getFornecedor().getNome() : "Sem fornecedor"), p.getQuantidade(),
					p.getMinimoStock(), Helpers.formatarDinheiro(p.getPreco()) });
		}
	}

	private void adicionarProduto() {
		try {
			int id = Integer.parseInt(txtId.getText());
			String nome = txtNome.getText();
			String armazemSelecionado = (String) comboArmazem.getSelectedItem();
			String fornecedorSelecionado = (String) comboFornecedor.getSelectedItem();
			int quantidade = Integer.parseInt(txtQuantidade.getText());
			int minimo = 5;
			double preco = Double.parseDouble(txtPreco.getText());

			if (armazemSelecionado.equals("Escolha uma opcao") || fornecedorSelecionado.equals("Escolha uma opcao")) {
				Helpers.mensagemAlertaErro("Selecione o armazém e o fornecedor!");
				return;
			}

			Armazem armazem = armazemController.getArmazem().stream()
					.filter(a -> a.getNome().equals(armazemSelecionado)).findFirst().orElse(null);

			Fornecedor fornecedor = fornecedoresController.getFornecedores().stream()
					.filter(f -> f.getNome().equals(fornecedorSelecionado)).findFirst().orElse(null);

			if (produtosController.adicionar(id, nome, "", fornecedor, quantidade, minimo,
					(armazem != null ? armazem.getId() : 0), preco)) {
				Helpers.mensagemAlertaInformacao("Produto adicionado com sucesso!");
				limpar();
			}

			carregarProdutosNaTabela();

		} catch (NumberFormatException e) {
			Helpers.mensagemAlertaErro("Verifique os campos numericos!");
		}
	}

	public void actualizarProduto() {
		try {
			int id = Integer.parseInt(txtId.getText());
			String nome = txtNome.getText();
			String armazemSelecionado = (String) comboArmazem.getSelectedItem();
			String fornecedorSelecionado = (String) comboFornecedor.getSelectedItem();
			int quantidade = Integer.parseInt(txtQuantidade.getText());
			int minimo = 5;
			double preco = Double.parseDouble(txtPreco.getText());

			Armazem armazem = armazemController.getArmazem().stream()
					.filter(a -> a.getNome().equals(armazemSelecionado)).findFirst().orElse(null);

			Fornecedor fornecedor = fornecedoresController.getFornecedores().stream()
					.filter(f -> f.getNome().equals(fornecedorSelecionado)).findFirst().orElse(null);

			produtosController.actualizar(id, nome, "", fornecedor, quantidade, minimo,
					(armazem != null ? armazem.getId() : 0), preco);

			limpar();

			carregarProdutosNaTabela();
		} catch (NumberFormatException e) {
			Helpers.mensagemAlertaErro("Verifique os campos numéricos!");
		}
	}

	public void removerProduto() {
		try {
			int id = Integer.parseInt(txtId.getText());
			produtosController.remover(id);
			carregarProdutosNaTabela();
		} catch (NumberFormatException e) {
			Helpers.mensagemAlertaErro("Digite um ID válido!");
		}
	}

	public void adicionarQuantidade() {
		try {
			int id = Integer.parseInt(txtId.getText());
			int quantidade = Integer.parseInt(txtQuantidade.getText());
			Produto produto = produtosController.encontrar(id);

			if (produto != null) {
				produto.adicionar(quantidade);
				produtosController.guardarEmFicheiro("produtos.dat");
				carregarProdutosNaTabela();
				Helpers.mensagemAlertaInformacao("Quantidade adicionada com sucesso!");
			} else {
				Helpers.mensagemAlertaErro("Produto nao encontrado!");
			}
		} catch (NumberFormatException e) {
			Helpers.mensagemAlertaErro("Digite valores numericos válidos!");
		}
	}

	public void limpar() {
		txtNome.setText(null);
		txtId.setText(null);
		txtPreco.setText(null);
		txtQuantidade.setText(null);
		txtStockMinimo.setText(null);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnAdicionar) {
			adicionarProduto();
		} else if (event.getSource() == btnActualizar) {
			actualizarProduto();
		} else if (event.getSource() == btnRemover) {
			removerProduto();
		} else if (event.getSource() == btnAdicionarQuantidade) {
			adicionarQuantidade();
		} else if (event.getSource() == btnPesquisar) {
			pesquisarProdutos();
		}
	}

	public void pesquisarProdutos() {
		String termo = txtPesquisarNome.getText().trim().toLowerCase();

		if (termo.isEmpty()) {
			carregarProdutosNaTabela();
			return;
		}

		List<Produto> filtrados = new ArrayList<>();

		for (Produto p : produtosController.getProdutos()) {
			if (p.getNome().toLowerCase().contains(termo)) {
				filtrados.add(p);
			}
		}

		modelo.setRowCount(0);
		for (Produto produto : filtrados) {
			modelo.addRow(new Object[] { produto.getId(), produto.getNome(), produto.getCodigoArmazem(),
					produto.getFornecedor().getNome(), produto.getQuantidade(), produto.getMinimoStock(),
					Helpers.formatarDinheiro(produto.getPreco()) });
		}

		if (filtrados.isEmpty()) {
			Helpers.mensagemAlertaInformacao("Nenhum Produto encontrado.");
		}
	}
}
