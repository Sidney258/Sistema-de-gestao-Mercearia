package mz.com.projecto.controladores;

import java.util.ArrayList;
import mz.com.projecto.classes.ProdutoItem;

public class ProdutoItemController {
    
    private ArrayList<ProdutoItem> produtoItem = new ArrayList<>();
    
    public ProdutoItemController() {
        
    }
    
    public ProdutoItem encontrar(int id) {
        for (int i = 0; i < produtoItem.size(); i++) {
            if (produtoItem.get(i).getId() == id) {
                return produtoItem.get(i);
            }
        }
        return null;
    }
    
}
