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
        item = new ItemConforto(null, null);
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
    
    public String excluir (ItemConforto item) {
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            boolean testeExclusao = manterItem.excluir(item.getCodItem());
            if (testeExclusao) {
                return "sucesso";
            } else {
                return "falha";
            }
        } catch (Exception ex) {
            return "falha";
        }
        
    }
    
    public String inserir () {
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            boolean testeInsercao = manterItem.inserir(item);
            if (testeInsercao) {
                return "sucesso";
            } else {
                return "falha";
            }
        } catch (Exception ex) {
            return "falha";
        }
        
    }

}
