package mz.com.projecto.controladores;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.com.projecto.classes.Cliente;
import mz.com.projecto.helpers.Helpers;

public class ClientControllers {

    private List<Cliente> clientes = new ArrayList<>();

    public ClientControllers() {

    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public boolean adicionar(int id, String nome, String email, String telefone, Date dataRegisto) {
        carregarClientesDoFicheiro("clientes.dat");
        if (encontrar(id) == null) {
            clientes.add(new Cliente(id, nome, email, telefone, dataRegisto));
            guardarClientesEmFicheiro("clientes");
            return true;
        }
        return false;
    }

    public Cliente encontrar(int id) {
        carregarClientesDoFicheiro("clientes.dat");
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public void remover(int id) {
        carregarClientesDoFicheiro("clientes.dat");
        if (encontrar(id) != null) {
            Cliente cliente = encontrar(id);
            if (clientes.remove(cliente)) {
                Helpers.mensagemAlertaInformacao("Cliente removido com sucesso");
            }
        } else {
            Helpers.mensagemAlertaInformacao("O id digitado nao faz parte da nossa lista de clientes");
        }
    }

    public void actualizar(int id, String nome, String email, String telefone) {
        carregarClientesDoFicheiro("clientes.dat");
        if (encontrar(id) != null) {
            Cliente cliente = encontrar(id);
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);

            Helpers.mensagemAlertaInformacao("Dados actualizados correctamente");
        } else {
            Helpers.mensagemAlertaErro("Erro ao actualizar dados do cliente");
        }
    }

    public void guardarClientesEmFicheiro(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(clientes);
//            Helpers.mensagemAlertaInformacao("Clientes guardados com sucesso em " + caminho);
        } catch (IOException e) {
            Helpers.mensagemAlertaErro("Erro ao guardar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarClientesDoFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            clientes = (List<Cliente>) ois.readObject();
//            Helpers.mensagemAlertaInformacao("Clientes carregados com sucesso de " + caminho);
        } catch (FileNotFoundException e) {
            Helpers.mensagemAlertaInformacao("Nenhum ficheiro encontrado, iniciando lista vazia.");
        } catch (IOException | ClassNotFoundException e) {
            Helpers.mensagemAlertaErro("Erro ao carregar clientes: " + e.getMessage());
        }
    }
}
