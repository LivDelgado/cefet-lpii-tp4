package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface IUsuarioDAO {

    boolean adicionaUsuario(Usuario usuario)
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException;

    List<Usuario> buscaUsuario(Object dadoBusca, String coluna)
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException;

    List<Usuario> buscaTodosUsuarios()
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException;

    boolean atualizaUsuario(Object pK, Usuario usuarioAtualizado)
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException;

    boolean deletaUsuario(Object pK) throws SQLException;
    
    Usuario usuarioLogin(String email, String senha) 
            throws SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException;
}
