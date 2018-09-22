package br.cefetmg.inf.hosten.model.service;

import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;

public interface IManterUsuario {
    
    public boolean inserir(Usuario usuario) throws NegocioException, 
            SQLException;
    
    public List<Usuario> listar(Object dadoBusca, String coluna) 
            throws NegocioException, SQLException;
    public List<Usuario> listarTodos() throws NegocioException, SQLException;
    
    public boolean alterar(String codRegistro, Usuario usuario) 
            throws NegocioException, SQLException;
    
    public boolean excluir(String codRegistro) throws NegocioException, 
            SQLException;
    
    public Usuario usuarioLogin(String email, String senha) 
            throws NegocioException, SQLException;
}
