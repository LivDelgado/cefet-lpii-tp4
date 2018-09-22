package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.ICategoriaQuartoDAO;
import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class CategoriaQuartoDAO implements ICategoriaQuartoDAO{

    private static Connection con;
    private static CategoriaQuartoDAO instancia;

    private CategoriaQuartoDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized CategoriaQuartoDAO getInstance() {
        if (instancia == null) {
            instancia = new CategoriaQuartoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaCategoriaQuarto(CategoriaQuarto categoriaQuarto)
            throws SQLException {
        String qry = "INSERT INTO Categoria"
                + "(codCategoria, nomCategoria, vlrDiaria)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, categoriaQuarto.getCodCategoria());
        pStmt.setString(2, categoriaQuarto.getNomCategoria());
        pStmt.setDouble(3, categoriaQuarto.getVlrDiaria());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<CategoriaQuarto> buscaCategoriaQuarto(
            Object dadoBusca, 
            String coluna) throws SQLException {
        String qry = "SELECT * FROM Categoria "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<CategoriaQuarto> categoriaQuartosEncontrados = new ArrayList<>();
        
        int i = 0;
        while (rs.next()) {
            categoriaQuartosEncontrados
                    .add(new CategoriaQuarto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getDouble(3)));
            i++;
        }

        return categoriaQuartosEncontrados;
    }

    @Override
    public List<CategoriaQuarto> buscaTodosCategoriaQuartos() 
            throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Categoria";
        ResultSet rs = stmt.executeQuery(qry);

        List<CategoriaQuarto> categoriaQuartosEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            categoriaQuartosEncontrados
                    .add(new CategoriaQuarto(
                            rs.getString(1), 
                            rs.getString(2),
                            rs.getDouble(3)));
            i++;
        }

        return categoriaQuartosEncontrados;
    }

    @Override
    public boolean atualizaCategoriaQuarto(
            Object pK, 
            CategoriaQuarto categoriaQuartoAtualizado) throws SQLException {
        String qry = "UPDATE Categoria "
                + "SET codCategoria = ?, nomCategoria = ?, vlrDiaria = ? "
                + "WHERE codCategoria = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, categoriaQuartoAtualizado.getCodCategoria());
        pStmt.setString(2, categoriaQuartoAtualizado.getNomCategoria());
        pStmt.setDouble(3, categoriaQuartoAtualizado.getVlrDiaria());
        if (pK instanceof String) {
            pStmt.setString(4, pK.toString());
        } else {
            pStmt.setInt(4, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaCategoriaQuarto(Object pK) throws SQLException {
        String qry = "DELETE FROM Categoria "
                + "WHERE codCategoria = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
