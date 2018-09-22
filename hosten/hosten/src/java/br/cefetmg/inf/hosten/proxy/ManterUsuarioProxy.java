package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;


public class ManterUsuarioProxy implements IManterUsuario {

    @Override
    public boolean inserir(Usuario usuario) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Usuario");
        lista.add("Inserir");
        lista.add(usuario);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Usuario> listar(Object dadoBusca, String coluna) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Usuario");
        lista.add("Listar");
        lista.add(dadoBusca);
        lista.add(coluna);
        
        try {
            return (List<Usuario>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Usuario");
        lista.add("ListarTodos");
        
        try {
            return (List<Usuario>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean alterar(String codRegistro, Usuario usuario) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Usuario");
        lista.add("Alterar");
        lista.add(codRegistro);
        lista.add(usuario);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean excluir(String codRegistro) throws NegocioException, SQLException {
        ArrayList lista = new ArrayList();
        lista.add("Usuario");
        lista.add("Excluir");
        lista.add(codRegistro);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @Override
    public Usuario usuarioLogin(String email, String senha) {
        ArrayList lista = new ArrayList();
        lista.add("Usuario");
        lista.add("Login");
        lista.add(email);
        lista.add(senha);
        
        try {
            return (Usuario)operacaoRegistro(lista);
        } catch (Exception ex) {
            return null;
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
                case "Usuario":
                    return (Usuario)listaRecebida.get(1);
                case "List<Usuario>":
                    return (List<Usuario>)listaRecebida.get(1);
                case "Exception":
                    throw (Exception)listaRecebida.get(1);
            }
        }   catch (Exception ex) {
            throw ex;
        }
        return null;
    }
}
