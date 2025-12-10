package mz.com.projecto.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Menu extends JFrame implements ActionListener {

    private JMenu jmClientes, jmProdutos, jmSair, jmVendas, jmFornecedores;
    private JMenuBar barraMenu;
    private JMenuItem itemClientes, itemProdutos, itemSair, itemVendas, itemHistoricoVendas, itemFornecedores, itemArmazem,
            itemStockBaixo, itemProdutosMaisVendidos;
    private ImageIcon imagemFundo;
    private JLabel lblImagem, lblHora;
    private Timer timer;

    public Menu() {
        setTitle("Menu principal");
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        jmClientes = new JMenu("Clientes");
        jmClientes.setIcon(new ImageIcon("imagens/Custom-Icon-Design-Pretty-Office-3-Accept-Male-User.32.PNG"));
        jmProdutos = new JMenu("Produtos");
        jmProdutos.setIcon(new ImageIcon("imagens/Custom-Icon-Design-Pretty-Office-3-Product.32.PNG"));
        jmFornecedores = new JMenu("Fornecedores");
        jmFornecedores.setIcon(new ImageIcon("imagens/Custom-Icon-Design-Flatastic-2-Truck.32.png"));
        jmSair = new JMenu("Sair");
        jmSair.setIcon(new ImageIcon("imagens/Custom-Icon-Design-Pretty-Office-11-Logout.32.PNG"));
        jmVendas = new JMenu("Vendas");
        jmVendas.setIcon(new ImageIcon("imagens/Custom-Icon-Design-Flatastic-5-Sales-by-payment-method.32.PNG"));
        barraMenu = new JMenuBar();

        itemClientes = new JMenuItem("Gestao Clientes");
        jmClientes.add(itemClientes);
        itemProdutos = new JMenuItem("Gestao Produtos");
        jmProdutos.add(itemProdutos);
        itemArmazem = new JMenuItem("Gestao Armazem",
                new ImageIcon("resources/Custom-Icon-Design-Pretty-Office-3-Product.32.PNG"));
        itemStockBaixo = new JMenuItem("Produtos com stock baixo");
        jmProdutos.add(itemStockBaixo);
        jmProdutos.add(itemArmazem);
        itemProdutosMaisVendidos = new JMenuItem("5 produtos mais Vendidos");
        itemVendas = new JMenuItem("Realizar Venda");
        jmVendas.add(itemProdutosMaisVendidos);
        jmVendas.add(itemVendas);
        itemFornecedores = new JMenuItem("Gestao Fornecedores");
        jmFornecedores.add(itemFornecedores);
        itemHistoricoVendas = new JMenuItem("Historico de Vendas");
        jmVendas.add(itemHistoricoVendas);
        itemSair = new JMenuItem("Sair do Sistema");
        jmSair.add(itemSair);

        imagemFundo = new ImageIcon("imagens/pexels-ninthgrid-2149521550-30688912.jpg");
        lblImagem = new JLabel();
        lblImagem.setLayout(new BorderLayout());
        atualizarImagemDeFundo();

        JPanel painelHora = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelHora.setOpaque(false);
        lblHora = new JLabel();
        lblHora.setFont(new Font("Arial", Font.BOLD, 14));
        lblHora.setForeground(Color.WHITE);
        painelHora.add(lblHora);
        lblImagem.add(painelHora, BorderLayout.SOUTH);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarHora();
            }
        });
        timer.start();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarImagemDeFundo();
            }
        });

        barraMenu.add(jmClientes);
        barraMenu.add(jmProdutos);
        barraMenu.add(jmFornecedores);
        barraMenu.add(jmVendas);
        barraMenu.add(jmSair);
        setJMenuBar(barraMenu);

        add(lblImagem, BorderLayout.CENTER);

        itemSair.addActionListener(this);
        itemClientes.addActionListener(this);
        itemProdutos.addActionListener(this);
        itemArmazem.addActionListener(this);
        itemFornecedores.addActionListener(this);
        itemVendas.addActionListener(this);
        itemHistoricoVendas.addActionListener(this);
        itemProdutosMaisVendidos.addActionListener(this);
        itemStockBaixo.addActionListener(this);
    }

    private void atualizarImagemDeFundo() {
        Image img = imagemFundo.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        lblImagem.setIcon(new ImageIcon(img));
    }

    private void atualizarHora() {
        String horaAtual = new SimpleDateFormat("HH:mm:ss").format(new Date());
        lblHora.setText("Hora atual: " + horaAtual);
        lblHora.setForeground(Color.black);
    }

    private void sair() {
        if (JOptionPane.showConfirmDialog(null, "Tem a certeza que deseja terminar o programa? ", "Aviso",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == itemSair) {
            sair();
        }

        if (event.getSource() == itemClientes) {
            new Clientes().setVisible(true);
        }
        if (event.getSource() == itemProdutos) {
            new Produtos().setVisible(true);
        }
        if (event.getSource() == itemArmazem) {
            new Armazem().setVisible(true);
        }
        if (event.getSource() == itemFornecedores) {
            new Fornecedores().setVisible(true);
        }
        if (event.getSource() == itemVendas) {
            new Vendas().setVisible(true);
        }
        if (event.getSource() == itemHistoricoVendas) {
            new HistoricoVendas().setVisible(true);
        }
        if (event.getSource() == itemProdutosMaisVendidos) {
            new HistoricoCompras().setVisible(true);
        }
        if (event.getSource() == itemStockBaixo) {
            new ProdutoStockBaixo().setVisible(true);
        }
    }

}
