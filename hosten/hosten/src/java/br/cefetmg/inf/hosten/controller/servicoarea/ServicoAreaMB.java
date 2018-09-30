package br.cefetmg.inf.hosten.controller.servicoarea;

import br.cefetmg.inf.hosten.controller.context.ContextUtils;
import br.cefetmg.inf.hosten.model.domain.ServicoArea;
import br.cefetmg.inf.hosten.model.service.IManterServicoArea;
import br.cefetmg.inf.hosten.proxy.ManterServicoAreaProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;

@ViewScoped
@Named("servicoAreaMB")
public class ServicoAreaMB implements Serializable {

    private List<ServicoArea> listaServicoAreas;
    private ServicoArea servicoArea;
    private String codServicoAreaAlterar;

    public ServicoAreaMB() {
        servicoArea = new ServicoArea(null, null);
        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();
        try {
            listaServicoAreas = manterServicoArea.listarTodos();
        } catch (NegocioException | SQLException ex) {
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
        servicoArea = (ServicoArea) event.getObject();

        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();

        try {
            System.out.println("codServicoAreaAlterar => " + codServicoAreaAlterar);
            System.out.println("servicoAreaaa => " + servicoArea.getNomServicoArea() + " / " + servicoArea.getCodServicoArea());
            boolean testeAlteracao = manterServicoArea.alterar(codServicoAreaAlterar, servicoArea);
            if (testeAlteracao) {
                ContextUtils.mostrarMensagem("Alteração efetuada", "Registro alterado com sucesso!", true);
            } else {
                ContextUtils.mostrarMensagem("Falha na alteração", "Falha ao alterar o registro!", true);
            }
        } catch (NegocioException | SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            ContextUtils.mostrarMensagem("Falha na alteração", ex.getMessage(), true);
            ContextUtils.redireciona(null);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        ContextUtils.mostrarMensagem("Edição Cancelada", ((ServicoArea) event.getObject()).getCodServicoArea(), false);
    }

    public String excluir(ServicoArea servicoArea) {
        this.servicoArea = servicoArea;

        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();

        try {
            System.out.println("servicoArea => " + servicoArea.getCodServicoArea());
            boolean testeExclusao = manterServicoArea.excluir(servicoArea.getCodServicoArea());
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

    public String inserir() {
        IManterServicoArea manterServicoArea = new ManterServicoAreaProxy();

        try {
            boolean testeInsercao = manterServicoArea.inserir(servicoArea);
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
