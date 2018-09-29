package br.cefetmg.inf.hosten.controller.servicoarea;

import br.cefetmg.inf.hosten.model.domain.ServicoArea;
import br.cefetmg.inf.hosten.model.service.IManterServicoArea;
import br.cefetmg.inf.hosten.proxy.ManterServicoAreaProxy;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "servicoAreaMB")
@ViewScoped
public class ServicoAreaMB implements Serializable {
    
    private List<ServicoArea> listaServicoAreas;
    private ServicoArea servicoArea;
    private String codServicoAreaAlterar;
    
    public ServicoAreaMB() {
        servicoArea = new ServicoArea(null, null);
        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();
        try {
            listaServicoAreas = manterServicoArea.listarTodos();
        } catch (Exception ex) {
            //
        }
    }
    
    public ServicoArea getServicoArea() {
        return servicoArea;
    }

    public void setServicoArea(ServicoArea servicoArea) {
        this.servicoArea = servicoArea;
    }
    
    public List<ServicoArea> getListaServicoAreas() {
        return listaServicoAreas;
    }
    
    public void onRowInit(RowEditEvent event) {
        codServicoAreaAlterar = (String) event.getComponent().getAttributes().get("servicoAreaEditar");           
    }
    
    public void onRowEdit(RowEditEvent event) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        
        servicoArea = (ServicoArea) event.getObject();
        
        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();
        
        try {
            boolean testeAlteracao = manterServicoArea.alterar(codServicoAreaAlterar, servicoArea);
            if (testeAlteracao) {
                context.addMessage(null, new FacesMessage("Registro alterado com sucesso!"));
                return;
            } else {
                context.addMessage(null, new FacesMessage("Falha ao alterar o registro!"));
                return;
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            FacesContext.getCurrentInstance().getExternalContext().redirect("servico-area.jsf");
            return;
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edição Cancelada", ((ServicoArea) event.getObject()).getCodServicoArea());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String excluir(ServicoArea servicoArea) {
        this.servicoArea = servicoArea;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();
        
        try {
            boolean testeExclusao = manterServicoArea.excluir(servicoArea.getCodServicoArea());
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

        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();
        
        try {
            boolean testeInsercao = manterServicoArea.inserir(servicoArea);
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

}
