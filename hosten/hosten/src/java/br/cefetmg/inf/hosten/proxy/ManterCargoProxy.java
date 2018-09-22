package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.Cargo;
import br.cefetmg.inf.hosten.model.domain.Programa;
import br.cefetmg.inf.hosten.model.service.IManterCargo;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManterCargoProxy implements IManterCargo {

    @Override
    public boolean inserir(Cargo cargo, List<String> listaProgramas)
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("Inserir");
        lista.add(cargo);
        lista.add(listaProgramas);

        try {
            return (boolean) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Cargo> listar(Object dadoBusca, String coluna) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("Listar");
        lista.add(dadoBusca);
        lista.add(coluna);

        try {
            return (List<Cargo>) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Cargo> listarTodos() throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("ListarTodos");

        try {
            return (List<Cargo>) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Programa> listarProgramasRelacionados(String codCargo)
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("ListarProgramasRelacionados");
        lista.add(codCargo);

        try {
            return (List<Programa>) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Programa> listarTodosProgramas()
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("ListarTodosProgramas");

        try {
            return (List<Programa>) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean alterar(String codRegistro,
            Cargo cargo,
            List<String> listaProgramas) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("Alterar");
        lista.add(codRegistro);
        lista.add(cargo);
        lista.add(listaProgramas);

        try {
            return (boolean) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean excluir(String codRegistro) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Cargo");
        lista.add("Excluir");
        lista.add(codRegistro);

        try {
            return (boolean) operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    public Object operacaoRegistro(ArrayList lista) throws Exception {
        try {
            FutureTask retornoCallableClient = new FutureTask(new CallableClient(lista));
            Thread t = new Thread(retornoCallableClient);
            t.start();

            ArrayList listaRecebida = ((ArrayList) retornoCallableClient.get());

            String tipoObjeto = (String) listaRecebida.get(0);
            switch (tipoObjeto) {
                case "Boolean":
                    return (boolean) listaRecebida.get(1);
                case "List<Cargo>":
                    return (List<Cargo>) listaRecebida.get(1);
                case "List<Programa>":
                    return (List<Programa>) listaRecebida.get(1);
                case "Exception":
                    throw (Exception) listaRecebida.get(1);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return null;
    }
}
