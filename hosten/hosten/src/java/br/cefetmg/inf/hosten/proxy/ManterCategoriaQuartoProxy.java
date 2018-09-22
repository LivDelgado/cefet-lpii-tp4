package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterCategoriaQuarto;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;


public class ManterCategoriaQuartoProxy implements IManterCategoriaQuarto {

    public ManterCategoriaQuartoProxy() {
    }

    @Override
    public boolean inserir(CategoriaQuarto categoriaQuarto, 
            List<ItemConforto> itensCategoria) 
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("CategoriaQuarto");
        lista.add("Inserir");
        lista.add(categoriaQuarto);
        lista.add(itensCategoria);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<CategoriaQuarto> listar(Object dadoBusca, String coluna) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("CategoriaQuarto");
        lista.add("Listar");
        lista.add(dadoBusca);
        lista.add(coluna);
        
        try {
            return (List<CategoriaQuarto>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<CategoriaQuarto> listarTodos() throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("CategoriaQuarto");
        lista.add("ListarTodos");
        
        try {
            return (List<CategoriaQuarto>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @Override
    public List<ItemConforto> listarItensRelacionados(String codCategoria) 
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("CategoriaQuarto");
        lista.add("ListarItensRelacionados");
        lista.add(codCategoria);
        
        try {
            return (List<ItemConforto>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean alterar(String codRegistro, 
            CategoriaQuarto categoriaQuarto,
            List<ItemConforto> itensCategoria) 
            throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("CategoriaQuarto");
        lista.add("Alterar");
        lista.add(codRegistro);
        lista.add(categoriaQuarto);
        lista.add(itensCategoria);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean excluir(String codRegistro) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("CategoriaQuarto");
        lista.add("Excluir");
        lista.add(codRegistro);
        
        try {
            return (boolean)operacaoRegistro(lista);
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
            switch (tipoObjeto) {
                case "Boolean":
                    return (boolean)listaRecebida.get(1);
                case "List<CategoriaQuarto>":
                    return (List<CategoriaQuarto>)listaRecebida.get(1);
                case "List<ItemConforto>":
                    return (List<ItemConforto>)listaRecebida.get(1);
                case "Exception":
                    throw (Exception)listaRecebida.get(1);
            }
        }   catch (Exception ex) {
            throw ex;
        }
        return null;
    }
}
