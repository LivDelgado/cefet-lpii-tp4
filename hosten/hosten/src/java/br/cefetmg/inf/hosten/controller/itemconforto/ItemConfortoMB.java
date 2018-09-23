package br.cefetmg.inf.hosten.controller.itemconforto;

import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterItemConforto;
import br.cefetmg.inf.hosten.proxy.ManterItemConfortoProxy;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "itemMB")
@ViewScoped
public class ItemConfortoMB implements Serializable {
    
    private List<ItemConforto> listaItens;
    private ItemConforto item;

    public ItemConfortoMB() {
        item = null;
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            listaItens = manterItem.listarTodos();
        } catch (Exception ex) {
            //
            //
            //
        }
    }

    public ItemConforto getItem() {
        return item;
    }

    public void setItem(ItemConforto item) {
        this.item = item;
    }
    
    public List<ItemConforto> getListaItens () {
        return listaItens;
    }
    
    public void editar () {
        System.out.println("editar item: " + item.getDesItem());
    }
    
    public void deletar () {
        System.out.println("editar item: " + item.getDesItem());
    }
}
