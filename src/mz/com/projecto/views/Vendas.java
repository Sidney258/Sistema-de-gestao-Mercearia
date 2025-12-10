package mz.com.projecto.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import mz.com.projecto.classes.*;
import mz.com.projecto.controladores.*;
import mz.com.projecto.helpers.*;

public class Vendas extends JFrame implements ActionListener {

    private JLabel lblCliente, lblProdutoId, lblpreco, lblQuantidade, lblValor, lblIva, lblDataVenda;
    private JTextField txtClienteId, txtProdutoId, txtPreco, txtQuantidade, txtTotalVenda, txtIva, txtDataVenda;
    private JButton btnAdicionarItem, btnRemoverItem, btnFinalizarVenda, btnNovo;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JScrollPane scroll;

    ClientControllers client = new ClientControllers();
    ProdutoItemController item = new ProdutoItemController();
    ProdutosControllers prod = new ProdutosControllers();
    private Venda vendaAtual;
    private final String CAMINHO_PRODUTOS = "produtos.dat";
    private final String CAMINHO_VENDAS = "vendas.dat";
    private final String CAMINHO_PRODUTOS_COMPRADOS = "produtosComprados.dat";
    private List<Venda> historico = new ArrayList<>();

    public Vendas() {
        setTitle("Vendas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        carregarVendasDoFicheiro(CAMINHO_VENDAS);

        lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(50, 30, 80, 25);
        add(lblCliente);

        txtClienteId = new JTextField();
        txtClienteId.setBounds(130, 30, 200, 25);
        add(txtClienteId);

        lblDataVenda = new JLabel("Data:");
        lblDataVenda.setBounds(400, 30, 80, 25);
        add(lblDataVenda);

        txtDataVenda = new JTextField();
        txtDataVenda.setBounds(460, 30, 150, 25);
        add(txtDataVenda);

        lblProdutoId = new JLabel("Produto:");
        lblProdutoId.setBounds(50, 70, 80, 25);
        add(lblProdutoId);

        txtProdutoId = new JTextField();
        txtProdutoId.setBounds(130, 70, 200, 25);
        add(txtProdutoId);

        lblpreco = new JLabel("Preço:");
        lblpreco.setBounds(350, 70, 50, 25);
        add(lblpreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(400, 70, 100, 25);
        txtPreco.setEditable(false);
        add(txtPreco);

        lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(520, 70, 80, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(610, 70, 100, 25);
        add(txtQuantidade);

        btnAdicionarItem = new JButton("Adicionar Item", new ImageIcon("imagens/Kyo-Tux-Delikate-Add.16.png"));
        btnAdicionarItem.setBounds(50, 110, 150, 30);
        add(btnAdicionarItem);

        btnRemoverItem = new JButton("Remover Item", new ImageIcon("imagens/Ampeross-Qetto-2-Trash.16.png"));
        btnRemoverItem.setBounds(210, 110, 150, 30);
        add(btnRemoverItem);

        btnNovo = new JButton("Nova Venda", new ImageIcon("imagens/Custom-Icon-Design-Flatastic-1-Edit.16.png"));
        btnNovo.setBounds(370, 110, 150, 30);
        add(btnNovo);

        btnFinalizarVenda = new JButton("Finalizar Venda", new ImageIcon("imagens/Custom-Icon-Design-Flatastic-5-Save.16.png"));
        btnFinalizarVenda.setBounds(530, 110, 180, 30);
        add(btnFinalizarVenda);

        modelo = new DefaultTableModel(new String[]{"ID", "Nome", "Quantidade", "Subtotal", "IVA"}, 0);
        tabela = new JTable(modelo);
        scroll = new JScrollPane(tabela);
        scroll.setBounds(50, 160, 700, 250);
        add(scroll);

        lblIva = new JLabel("IVA (%):");
        lblIva.setBounds(400, 430, 80, 25);
        add(lblIva);

        txtIva = new JTextField("17");
        txtIva.setBounds(460, 430, 60, 25);
        txtIva.setEditable(false);
        add(txtIva);

        lblValor = new JLabel("Total Venda:");
        lblValor.setBounds(540, 430, 100, 25);
        add(lblValor);

        txtTotalVenda = new JTextField();
        txtTotalVenda.setBounds(630, 430, 120, 25);
        txtTotalVenda.setEditable(false);
        add(txtTotalVenda);

        txtDataVenda.setText(Helpers.formatarData(new Date()));
        txtDataVenda.setEditable(false);

        btnAdicionarItem.addActionListener(this);
        btnRemoverItem.addActionListener(this);
        btnFinalizarVenda.addActionListener(this);
        btnNovo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionarItem) {
            adicionarItem();
        } else if (e.getSource() == btnRemoverItem) {
        } else if (e.getSource() == btnFinalizarVenda) {
            finalizarVenda();
        } else if (e.getSource() == btnNovo) {
            novaVenda();
        }
    }

    public void finalizarVenda() {
        if (vendaAtual == null || vendaAtual.getCarrinhoCompras().isEmpty()) {
            Helpers.mensagemAlertaErro("Nao ha itens no carrinho para finalizar a venda.");
            return;
        }

        historico.add(vendaAtual);
        guardarVendasNoFicheiro(CAMINHO_VENDAS);
        Helpers.mensagemAlertaInformacao("Venda realizada com sucesso!");

        novaVenda();
        vendaAtual = null;
    }

    public void adicionarItem() {
    	try {
	        if (verificarCampos()) {
	            Helpers.mensagemAlertaErro("Preencha todos os campos!");
	        } else {
	            int clienteId = Integer.parseInt(txtClienteId.getText());
	            Cliente cliente = client.encontrar(clienteId);
	
	            if (cliente == null) {
	                Helpers.mensagemAlertaErro("Cliente não encontrado!");
	                return;
	            }
	
	            if (vendaAtual == null) {
	                vendaAtual = new Venda(new Date(), cliente);
	            }
	
	            int produtoId = Integer.parseInt(txtProdutoId.getText());
	            int quantidade = Integer.parseInt(txtQuantidade.getText());
	            Produto produto = prod.encontrar(produtoId);
	
	            if (produto == null) {
	                Helpers.mensagemAlertaErro("Produto não encontrado!");
	                return;
	            }
	
	            if (produto.getQuantidade() < quantidade) {
	                Helpers.mensagemAlertaErro("Quantidade indisponível em stock!");
	                return;
	            }
	
	            ProdutosControllers prodControllers = new ProdutosControllers();
	            Produto produtoEstoque = prodControllers.encontrar(produtoId);
	
	            if (produtoEstoque != null) {
	                produtoEstoque.diminuirQuantidade(quantidade);
	                prodControllers.guardarEmFicheiro(CAMINHO_PRODUTOS);
	            }
	
	            ProdutoItem itemVenda = new ProdutoItem(produtoId, produto, quantidade);
	            vendaAtual.adicionarProdutoAoCarrinho(itemVenda);
	
	            ProdutoCompradoControllers compradoController = new ProdutoCompradoControllers();
	            compradoController.carregarDoFicheiro(CAMINHO_PRODUTOS_COMPRADOS);
	            compradoController.adicionar(produto.getId(), produto.getNome(), quantidade);
	            compradoController.guardarEmFicheiro(CAMINHO_PRODUTOS_COMPRADOS);
	
	            modelo.setRowCount(0);
	            for (ProdutoItem item : vendaAtual.getCarrinhoCompras()) {
	            	txtPreco.setText(Helpers.formatarDinheiro(item.getProdutoItem().getPreco()));
	                modelo.addRow(new Object[]{
	                    item.getId(),
	                    item.getProdutoItem().getNome(),
	                    item.getQuantidade(),
	                    Helpers.formatarDinheiro(item.subtotal()),
	                    item.iva()
	                });
	            }
	
	            txtTotalVenda.setText(Helpers.formatarDinheiro(vendaAtual.total()));
	        }
    	} catch (NumberFormatException e) {
    		Helpers.mensagemAlertaInformacao("digite apenas numeros inteiros");
    	}
    }

    private void novaVenda() {
        txtProdutoId.setText(null);
        txtClienteId.setText(null);
        txtPreco.setText(null);
        txtQuantidade.setText(null);
        txtTotalVenda.setText(null);
        modelo.setRowCount(0);
    }

    public void guardarVendasNoFicheiro(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(historico);
        } catch (IOException e) {
            Helpers.mensagemAlertaErro("Erro ao guardar vendas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarVendasDoFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            historico = (List<Venda>) ois.readObject();
        } catch (FileNotFoundException e) {
            historico = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaErro("Erro ao carregar historico de vendas: " + e.getMessage());
        }
    }

    public boolean verificarCampos() {
        String cliente = txtClienteId.getText();
        String produto = txtProdutoId.getText();
        String quantidade = txtQuantidade.getText();
        return produto.isEmpty() || quantidade.isEmpty() || cliente.isEmpty();
    }

    public List<Venda> getHistoricoVendas() {
        return historico;
    }
}
