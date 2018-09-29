package br.cefetmg.inf.hosten.controller.itemconforto;

import br.cefetmg.inf.hosten.controller.context.ContextUtils;
import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterItemConforto;
import br.cefetmg.inf.hosten.proxy.ManterItemConfortoProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;

@ViewScoped
@Named("itemMB")
public class ItemConfortoMB implements Serializable {
    
    private List<ItemConforto> listaItens;
    private ItemConforto item;
    private String codItemAlterar;

    public ItemConfortoMB() {
        item = new ItemConforto(null, null);
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            listaItens = manterItem.listarTodos();
        } catch (NegocioException | SQLException ex) {
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
    
    public void onRowInit(RowEditEvent event) {
        codItemAlterar = (String) event.getComponent().getAttributes().get("itemEditar");           
    }
    
    public void onRowEdit(RowEditEvent event) throws IOException {
        item = (ItemConforto) event.getObject();
        
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            boolean testeAlteracao = manterItem.alterar(codItemAlterar, item);
            if (testeAlteracao) {
                ContextUtils.mostrarMensagem("Alteração efetuada", "Registro alterado com sucesso!", true);
            } else {
                ContextUtils.mostrarMensagem("Falha na alteração", "Falha ao alterar o registro!", true);
            }
        } catch (NegocioException | SQLException ex) {
            ContextUtils.mostrarMensagem("Falha na alteração", ex.getMessage(), true);
            ContextUtils.redireciona(null);
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        ContextUtils.mostrarMensagem("Edição Cancelada", ((ItemConforto) event.getObject()).getCodItem(), false);
    }
        
    public String excluir (ItemConforto item) {
        this.item = item;
        
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        
        try {
            boolean testeExclusao = manterItem.excluir(item.getCodItem());
            if (testeExclusao) {
                ContextUtils.mostrarMensagem("Exclusão efetuada", "Registro excluído com sucesso!", true);
                return "sucesso";
            } else {
                ContextUtils.mostrarMensagem("Falha na exclusão", "Falha ao excluir o registro!", true);
                return "falha";
            }
        } catch (NegocioException | SQLException ex) {
            ContextUtils.mostrarMensagem("Falha na exclusão", ex.getMessage(), true);
            return "falha";
        }    
    }
            
    public String inserir () {
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        
        try {
            boolean testeInsercao = manterItem.inserir(item);
            if (testeInsercao) {
                ContextUtils.mostrarMensagem("Inserção efetuada", "Registro inserido com sucesso!", true);
                return "sucesso";
            } else {
                ContextUtils.mostrarMensagem("Falha na inserção", "Falha ao inserir o registro!", true);
                return "falha";
            }
        } catch (NegocioException | SQLException ex) {
            ContextUtils.mostrarMensagem("Falha na inserção", ex.getMessage(), true);
            return "falha";
        }  
    }
}
