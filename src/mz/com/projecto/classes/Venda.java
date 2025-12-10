package mz.com.projecto.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import mz.com.projecto.helpers.Helpers;

public class Venda implements Serializable {

    private static final double IMPOSTO_SOBRE_VALOR_ACRESCENTADO = 0.17;
    private Date dataVenda;
    private Cliente cliente;
    private ArrayList<ProdutoItem> carrinhoCompras = new ArrayList<>();

    public Venda(Date dataVenda, Cliente cliente) {
        this.dataVenda = dataVenda;
        this.cliente = cliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<ProdutoItem> getCarrinhoCompras() {
        return carrinhoCompras;
    }

    public double subtotal() {
        double total = 0.00;
        for (int i = 0; i < carrinhoCompras.size(); i++) {
            total += carrinhoCompras.get(i).subtotal();
        }
        return total;
    }
    
    public double valorIva() {
        return subtotal() * IMPOSTO_SOBRE_VALOR_ACRESCENTADO;
    }
    
    public double total() {
        return subtotal() + valorIva();
    }
    
    public void adicionarProdutoAoCarrinho(ProdutoItem itemCompra) {
        boolean novo = true;
        for (ProdutoItem produto : carrinhoCompras) {
            if (produto.getId() == itemCompra.getId()) {
                novo = false;
                produto.adicionar(itemCompra.getQuantidade());
                Helpers.mensagemAlertaInformacao("acabou de adicionar mais " + produto.getQuantidade() + " de " + produto.getProdutoItem().getNome());
            }
        }
        if (novo) {
            carrinhoCompras.add(itemCompra);
        }
    }
}
