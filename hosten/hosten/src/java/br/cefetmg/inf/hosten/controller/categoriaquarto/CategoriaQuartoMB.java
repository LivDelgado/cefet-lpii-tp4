package br.cefetmg.inf.hosten.controller.categoriaquarto;

import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterCategoriaQuarto;
import br.cefetmg.inf.hosten.proxy.ManterCategoriaQuartoProxy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "categoriaMB")
@ViewScoped
public class CategoriaQuartoMB implements Serializable {

    private List<CategoriaQuarto> listaCategorias;
    private CategoriaQuarto categoria;
    
    private ItemConforto [] itensSelecionadosArray;

    public CategoriaQuartoMB() {
        categoria = new CategoriaQuarto(null, null, null);
        IManterCategoriaQuarto manterCategoria = new ManterCategoriaQuartoProxy();
        try {
            listaCategorias = manterCategoria.listarTodos();
        } catch (Exception ex) {
            //
            //
            //
        }
    }

    public List<CategoriaQuarto> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<CategoriaQuarto> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public CategoriaQuarto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaQuarto categoria) {
        this.categoria = categoria;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        String codCategoriaAlterar = (String) event.getComponent().getAttributes().get("categoriaEditar");

        categoria = (CategoriaQuarto) event.getObject();

        List <ItemConforto> listaItens = new ArrayList();
        for (ItemConforto item : itensSelecionadosArray) {
            listaItens.add(item);
        }
        
        IManterCategoriaQuarto manterCategoria = new ManterCategoriaQuartoProxy();
        try {
            boolean testeExclusao = manterCategoria.alterar(codCategoriaAlterar, categoria, listaItens);
            if (testeExclusao) {
                context.addMessage(null, new FacesMessage("Registro alterado com sucesso!"));
                return;
            } else {
                context.addMessage(null, new FacesMessage("Falha ao alterar o registro!"));
                return;
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            return;
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edição Cancelada", ((CategoriaQuarto) event.getObject()).getCodCategoria());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String excluir(CategoriaQuarto categoria) {
        this.categoria = categoria;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        IManterCategoriaQuarto manterCategoria = new ManterCategoriaQuartoProxy();

        try {
            boolean testeExclusao = manterCategoria.excluir(categoria.getCodCategoria());
            if (testeExclusao) {
                context.addMessage(null, new FacesMessage("Registro excluído com sucesso!"));
                return "sucesso";
            } else {
                context.addMessage(null, new FacesMessage("Falha ao excluir o registro!"));
                return "falha";
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            return "falha";
        }

    }

    public String inserir() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        IManterCategoriaQuarto manterCategoria = new ManterCategoriaQuartoProxy();

        List <ItemConforto> listaItens = new ArrayList();
        for (ItemConforto item : itensSelecionadosArray) {
            listaItens.add(item);
        }
        
        try {
            boolean testeInsercao = manterCategoria.inserir(categoria, listaItens);
            
            if (testeInsercao) {
                context.addMessage(null, new FacesMessage("Registro inserido com sucesso!"));
                return "sucesso";
            } else {
                context.addMessage(null, new FacesMessage("Falha ao inserir o registro!"));
                return "falha";
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            return "falha";
        }
    }

    public ItemConforto[] getItensSelecionadosArray() {
        return itensSelecionadosArray;
    }

    public void setItensSelecionadosArray(ItemConforto[] itensSelecionadosArray) {
        this.itensSelecionadosArray = itensSelecionadosArray;
    }
    
}
