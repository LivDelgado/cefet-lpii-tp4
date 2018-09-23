package br.cefetmg.inf.hosten.controller.itemconforto;

import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterItemConforto;
import br.cefetmg.inf.hosten.proxy.ManterItemConfortoProxy;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "itemMB")
@Dependent
public class ItemConfortoMB {
    
    private List<ItemConforto> listaItens;
    private ItemConforto item;

    public ItemConfortoMB() {
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
    
}
