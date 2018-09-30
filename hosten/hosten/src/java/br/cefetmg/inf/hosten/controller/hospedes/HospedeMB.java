package br.cefetmg.inf.hosten.controller.hospedes;

import br.cefetmg.inf.hosten.controller.context.ContextUtils;
import br.cefetmg.inf.hosten.model.domain.Hospede;
import br.cefetmg.inf.hosten.model.service.IManterHospede;
import br.cefetmg.inf.hosten.proxy.ManterHospedeProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

@ViewScoped
@Named("hospedeMB")
public class HospedeMB implements Serializable {
    
    private List<Hospede> listaHospedes;
    private Hospede hospede;
    private String codHospedeAlterar;
    
    public HospedeMB() {
        hospede = new Hospede(null, null, null, null);
        IManterHospede manterHospede = new ManterHospedeProxy();
        try {
            listaHospedes = manterHospede.listarTodos();
        } catch (NegocioException | SQLException ex) {
            //
        }
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }
    
    public List<Hospede> getListaHospedes() {
        return listaHospedes;
    }
    
    public void onRowInit(RowEditEvent event) {
        codHospedeAlterar = (String) event.getComponent().getAttributes().get("hospedeEditar");           
    }
    
    public void onRowEdit(RowEditEvent event) throws IOException {
        hospede = (Hospede) event.getObject();
        
        IManterHospede manterHospede = new ManterHospedeProxy();
        try {
            boolean testeAlteracao = manterHospede.alterar(codHospedeAlterar, hospede);
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
        ContextUtils.mostrarMensagem("Edição Cancelada", ((Hospede) event.getObject()).getCodCPF(), false);
    }
        
    public String inserir() {
        IManterHospede manterHospede = new ManterHospedeProxy();
        
        try {
            boolean testeInsercao = manterHospede.inserir(hospede);
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
