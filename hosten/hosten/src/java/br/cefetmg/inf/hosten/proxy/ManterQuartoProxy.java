package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.Quarto;
import br.cefetmg.inf.hosten.model.service.IManterQuarto;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;


public class ManterQuartoProxy implements IManterQuarto {

    public ManterQuartoProxy() {
    }

    @Override
    public boolean inserir(Quarto quarto) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Quarto");
        lista.add("Inserir");
        lista.add(quarto);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Quarto> listar(Object dadoBusca, String coluna) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Quarto");
        lista.add("Listar");
        lista.add(dadoBusca);
        lista.add(coluna);
        
        try {
            return (List<Quarto>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Quarto> listarTodos() throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Quarto");
        lista.add("ListarTodos");
        
        try {
            return (List<Quarto>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean alterar(String codRegistro, Quarto quarto) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Quarto");
        lista.add("Alterar");
        lista.add(codRegistro);
        lista.add(quarto);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean excluir(String codRegistro) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Quarto");
        lista.add("Excluir");
        lista.add(codRegistro);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @Override
    public int buscaUltimoRegistroRelacionadoAoQuarto(int nroQuarto)
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Quarto");
        lista.add("BuscarSeqHospedagem");
        lista.add(nroQuarto);
        
        try {
            return (int)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    public Object operacaoRegistro (ArrayList lista) throws Exception {
        try {
            FutureTask retornoCallableClient = new FutureTask(new CallableClient(lista));
            Thread t = new Thread(retornoCallableClient);
            t.start();
            
            ArrayList listaRecebida = ((ArrayList)retornoCallableClient.get());
            
            String tipoObjeto = (String)listaRecebida.get(0);
            if (tipoObjeto.equals("Boolean")) {
                return (boolean)listaRecebida.get(1);
            } else if (tipoObjeto.equals("Inteiro")) {
                return (int)listaRecebida.get(1);
            } else if (tipoObjeto.equals("List<Quarto>")) {
                return (List<Quarto>)listaRecebida.get(1);
            } else if (tipoObjeto.equals("Exception")) {
                throw (Exception)listaRecebida.get(1);
            }
        }   catch (Exception ex) {
            throw ex;
        }
        return null;
    }

}
