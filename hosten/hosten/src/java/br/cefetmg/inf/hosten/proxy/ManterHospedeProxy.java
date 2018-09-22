package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.Hospede;
import br.cefetmg.inf.hosten.model.service.IManterHospede;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;


public class ManterHospedeProxy implements IManterHospede {

    @Override
    public boolean inserir(Hospede hospede) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Hospede");
        lista.add("Inserir");
        lista.add(hospede);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Hospede> listar(Object dadoBusca, String coluna) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Hospede");
        lista.add("Listar");
        lista.add(dadoBusca);
        lista.add(coluna);
        
        try {
            return (List<Hospede>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Hospede> listarTodos() throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Hospede");
        lista.add("ListarTodos");
        
        try {
            return (List<Hospede>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean alterar(String codRegistro, Hospede hospede) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Hospede");
        lista.add("Alterar");
        lista.add(codRegistro);
        lista.add(hospede);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    /*
    @Override
    public boolean excluir(String codRegistro) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Hospede");
        lista.add("Excluir");
        lista.add(codRegistro);
        
        return (boolean)operacaoRegistro(lista);
    }
    */
    
    public Object operacaoRegistro (ArrayList lista) throws Exception {
        try {
            FutureTask retornoCallableClient = new FutureTask(new CallableClient(lista));
            Thread t = new Thread(retornoCallableClient);
            t.start();
            
            ArrayList listaRecebida = ((ArrayList)retornoCallableClient.get());
            
            String tipoObjeto = (String)listaRecebida.get(0);
            if (tipoObjeto.equals("Boolean")) {
                return (boolean)listaRecebida.get(1);
            } else if (tipoObjeto.equals("List<Hospede>")) {
                return (List<Hospede>)listaRecebida.get(1);
            } else if (tipoObjeto.equals("Exception")) {
                throw (Exception)listaRecebida.get(1);
            }
        }   catch (Exception ex) {
            throw ex;
        }
        return null;
    }
}
