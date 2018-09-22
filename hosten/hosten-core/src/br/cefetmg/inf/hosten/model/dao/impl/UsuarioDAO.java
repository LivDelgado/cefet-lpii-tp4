package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IUsuarioDAO;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.impl.ManterUsuario;
import br.cefetmg.inf.util.SenhaUtils;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements IUsuarioDAO {

    private static Connection con;
    private static UsuarioDAO instancia;

    public UsuarioDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized UsuarioDAO getInstance() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaUsuario(Usuario usuario)
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        String qry = "INSERT INTO Usuario"
                + "(codUsuario, nomUsuario, codCargo, desSenha, desEmail)"
                + " VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, usuario.getCodUsuario());
        pStmt.setString(2, usuario.getNomUsuario());
        pStmt.setString(3, usuario.getCodCargo());
        pStmt.setString(4, SenhaUtils
                .stringParaSHA256(usuario.getDesSenha()));
        pStmt.setString(5, usuario.getDesEmail());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Usuario> buscaUsuario(Object dadoBusca, String coluna)
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        int i = 0;

        String qry = "SELECT * FROM Usuario "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<Usuario> usuarioEncontrados = new ArrayList<>();

        while (rs.next()) {
            usuarioEncontrados
                    .add(new Usuario(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)));
            i++;
        }

        return usuarioEncontrados;
    }

    @Override
    public List<Usuario> buscaTodosUsuarios()
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Usuario";
        ResultSet rs = stmt.executeQuery(qry);

        List<Usuario> usuariosEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            usuariosEncontrados.add(new Usuario(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)));
            i++;
        }

        return usuariosEncontrados;
    }

    @Override
    public boolean atualizaUsuario(Object pK, Usuario usuarioAtualizado)
            throws SQLException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        String qry = "UPDATE Usuario "
                + "SET codUsuario = ?, nomUsuario = ?, codCargo = ?, "
                + "desSenha= ?, desEmail = ? "
                + "WHERE codUsuario = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, usuarioAtualizado.getCodUsuario());
        pStmt.setString(2, usuarioAtualizado.getNomUsuario());
        pStmt.setString(3, usuarioAtualizado.getCodCargo());
        pStmt.setString(4, usuarioAtualizado.getDesSenha());
        pStmt.setString(5, usuarioAtualizado.getDesEmail());
        if (pK instanceof String) {
            pStmt.setString(6, pK.toString());
        } else {
            pStmt.setInt(6, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaUsuario(Object pK) throws SQLException {
        String qry = "DELETE FROM Usuario "
                + "WHERE codUsuario = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public Usuario usuarioLogin(String email, String senha)
            throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        String qry = "SELECT desSenha "
                + "FROM Usuario "
                + "WHERE desEmail = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, email);
        ResultSet rs = pStmt.executeQuery();

        rs.next();
        String senhaEncontrada = rs.getString(1);

        if (SenhaUtils.verificaSenha(senha, senhaEncontrada)) {
            List<Usuario> usuariosEncontrado
                    = buscaUsuario(email, "desEmail");
            return usuariosEncontrado.get(0);
        }
        // Retorna null caso o email não bata com a senha
        return null;
    }
}
