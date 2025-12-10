package mz.com.projecto.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Login extends JFrame implements ActionListener {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSair;
    private JLabel lblMensagem;

    public Login() {

        setTitle("Tela Autenticacao");
        setSize(450, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnSair = new JButton("Sair");
        btnLogin = new JButton("Login");
        lblMensagem = new JLabel("");

        JPanel painel = new JPanel(new GridLayout(4, 2, 5, 5));

        painel.add(new JLabel("E-mail:"));
        painel.add(txtEmail);
        painel.add(new JLabel("Password:"));
        painel.add(txtPassword);
        painel.add(btnSair);
        painel.add(btnLogin);

        add(painel);
        add(lblMensagem);

        btnLogin.addActionListener(this);
        btnSair.addActionListener(this);

    }

    public void autenticar() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (email.equals("administrador@gmail.com") && password.equals("administrador")) {
            new Login().setVisible(false);
            Menu menu = new Menu();
            new Login().setVisible(false);
            menu.setVisible(true);
        } else {
            lblMensagem.setText("E-mail ou palavra passe incorrectos, tente novamente");
            lblMensagem.setForeground(Color.red);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            autenticar();
        }
        if (e.getSource() == btnSair) {
            System.exit(0);
        }
    }

}
