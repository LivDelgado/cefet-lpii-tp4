package br.cefetmg.inf.hosten.controller.cargos;

import br.cefetmg.inf.hosten.controller.context.ContextUtils;
import br.cefetmg.inf.hosten.model.domain.Cargo;
import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.domain.Programa;
import br.cefetmg.inf.hosten.model.service.IManterCargo;
import br.cefetmg.inf.hosten.proxy.ManterCargoProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;

@ViewScoped
@Named("cargoMB")
public class CargoMB implements Serializable {

    private List<Cargo> listaCargos;
    private Cargo cargo;

    private Programa[] programasSelecionados;

    private List<Programa> programasRelacionados;
    private String codCargoAlterar;

    public CargoMB() {
        cargo = new Cargo(null, null, false);
        IManterCargo manterCargo = new ManterCargoProxy();
        try {
            listaCargos = manterCargo.listarTodos();
        } catch (NegocioException | SQLException e) {
            //
        }
    }

    public List<Cargo> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Cargo> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }


    public void onRowInit(RowEditEvent event) {
        codCargoAlterar = (String) event.getComponent().getAttributes().get("cargoEditar");
    }
    
    public void onRowEdit(RowEditEvent event) throws IOException {
        cargo = (Cargo) event.getObject();

        List<Programa> listaProgramas = new ArrayList();
        listaProgramas.addAll(Arrays.asList(programasSelecionados));
        
        List<String> listaProgramasString = new ArrayList();
        for(Programa prog : listaProgramas) {
            listaProgramasString.add(prog.getCodPrograma());
        }

        IManterCargo manterCargo = new ManterCargoProxy();
        try {
            boolean testeExclusao = manterCargo.alterar(codCargoAlterar, cargo, listaProgramasString);
            if (testeExclusao) {
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
        ContextUtils.mostrarMensagem("Edição Cancelada", ((Cargo) event.getObject()).getCodCargo(), false);
    }

    public String excluir(Cargo cargo) {
        this.cargo = cargo;

        IManterCargo manterCargo = new ManterCargoProxy();
        try {
            boolean testeExclusao = manterCargo.excluir(cargo.getCodCargo());
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
        IManterCargo manterCargo = new ManterCargoProxy();

        List<Programa> listaProgramas = new ArrayList();
        listaProgramas.addAll(Arrays.asList(programasSelecionados));

        List<String> listaProgramasString = new ArrayList();
        for(Programa prog : listaProgramas) {
            listaProgramasString.add(prog.getCodPrograma());
        }

        try {
            boolean testeInsercao = manterCargo.inserir(cargo, listaProgramasString);

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

    public Programa[] getProgramasSelecionados() {
        return programasSelecionados;
    }

    public void setProgramasSelecionados(Programa[] programasSelecionados) {
        this.programasSelecionados = programasSelecionados;
    }

    public List<Programa> getProgramasRelacionados() {
        return programasRelacionados;
    }

    public void setProgramasRelacionados(List<Programa> programasRelacionados) {
        this.programasRelacionados = programasRelacionados;
    }


    public List<Programa> getProgramasRelacionados(Cargo cargo) {
        IManterCargo manterCargo = new ManterCargoProxy();

        try {
            programasRelacionados = manterCargo.listarProgramasRelacionados(cargo.getCodCargo());
            if (programasRelacionados != null) {
                System.out.println("tamanho da lista de programas relacionados ao cargo " + cargo.getNomCargo() + ": " + programasRelacionados.size());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            //
            //
        }
        return programasRelacionados;
    }

}
