package br.cefetmg.inf.hosten.controller.cargos;

import br.cefetmg.inf.hosten.model.domain.Programa;
import br.cefetmg.inf.hosten.model.service.IManterCargo;
import br.cefetmg.inf.hosten.proxy.ManterCargoProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@ViewScoped
@Named("programaMB")
public class ProgramaMB implements Serializable {
    
    private List<Programa> listaProgramas;
    private Programa programa;
    private String codProgramaAlterar;

    public ProgramaMB() {
        programa = new Programa(null, null);
        IManterCargo manterCargo = new ManterCargoProxy();
        try {
            listaProgramas = manterCargo.listarTodosProgramas();
        } catch (NegocioException | SQLException ex) {
            //
        }
    }

    public List<Programa> getListaProgramas() {
        return listaProgramas;
    }

    public void setListaProgramas(List<Programa> listaProgramas) {
        this.listaProgramas = listaProgramas;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
}
