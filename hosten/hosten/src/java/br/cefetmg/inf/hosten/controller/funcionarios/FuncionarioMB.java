package br.cefetmg.inf.hosten.controller.funcionarios;

import br.cefetmg.inf.hosten.controller.context.ContextUtils;
import br.cefetmg.inf.hosten.model.domain.Cargo;
import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterCargo;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;
import br.cefetmg.inf.hosten.proxy.ManterCargoProxy;
import br.cefetmg.inf.hosten.proxy.ManterUsuarioProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;

@ViewScoped
@Named("funcionarioMB")
public class FuncionarioMB implements Serializable {

    private Usuario funcionario;
    private List<Usuario> listaFuncionarios;
    private Cargo cargoFuncionario;

    private Cargo cargoSelecionado;

    private String codFuncionarioAlterar;

    public FuncionarioMB() {
        funcionario = new Usuario(null, null, null, null, null);
        IManterUsuario manterFuncionario = new ManterUsuarioProxy();
        try {
            listaFuncionarios = manterFuncionario.listarTodos();
        } catch (NegocioException | SQLException e) {
            //
        }
    }

    public Cargo getCargoFuncionario(Usuario funcionario) {
        IManterCargo manterCargo = new ManterCargoProxy();
        try {
            cargoFuncionario = manterCargo.listar(funcionario.getCodCargo(), "codCargo").get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            //
        }
        return cargoFuncionario;
    }

    public void setCargoFuncionario(Cargo cargoFuncionario) {
        this.cargoFuncionario = cargoFuncionario;
    }

    public List<Usuario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<Usuario> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }


    public void onRowInit(RowEditEvent event) {
        codFuncionarioAlterar = (String) event.getComponent().getAttributes().get("funcionarioEditar");
    }
    
    public void onRowEdit(RowEditEvent event) throws IOException {
        funcionario = (Usuario) event.getObject();
        funcionario.setCodCargo(cargoSelecionado.getCodCargo());

        IManterUsuario manterFuncionario = new ManterUsuarioProxy();
        try {
            boolean testeExclusao = manterFuncionario.alterar(codFuncionarioAlterar, funcionario);
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
        ContextUtils.mostrarMensagem("Edição Cancelada", ((Usuario) event.getObject()).getCodUsuario(), false);
    }

    public String excluir(Usuario funcionario) {
        this.funcionario = funcionario;

        IManterUsuario manterFuncionario = new ManterUsuarioProxy();
        try {
            boolean testeExclusao = manterFuncionario.excluir(funcionario.getCodUsuario());
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
        IManterUsuario manterFuncionario = new ManterUsuarioProxy();
        funcionario.setCodCargo(cargoSelecionado.getCodCargo());

        try {
            boolean testeInsercao = manterFuncionario.inserir(funcionario);

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

    public Cargo getCargoSelecionado() {
        return cargoSelecionado;
    }

    public void setCargoSelecionado(Cargo cargoSelecionado) {
        this.cargoSelecionado = cargoSelecionado;
    }
}
