package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IServicoAreaDAO;
import br.cefetmg.inf.hosten.model.domain.ServicoArea;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicoAreaDAO implements IServicoAreaDAO{

    private static Connection con;
    private static ServicoAreaDAO instancia;

    private ServicoAreaDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized ServicoAreaDAO getInstance() {
        if (instancia == null) {
            instancia = new ServicoAreaDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaServicoArea(ServicoArea servicoArea) 
            throws SQLException {
        String qry = "INSERT INTO ServicoArea"
                + "(codServicoArea, nomServicoArea)"
                + " VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoArea.getCodServicoArea());
        pStmt.setString(2, servicoArea.getNomServicoArea());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<ServicoArea> buscaServicoArea(Object dadoBusca, String coluna) 
            throws SQLException {
        String qry = "SELECT * FROM ServicoArea "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<ServicoArea> servicoAreaEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            servicoAreaEncontrados
                    .add(new ServicoArea(
                            rs.getString(1),
                            rs.getString(2)));
            i++;
        }

        return servicoAreaEncontrados;
    }

    @Override
    public List<ServicoArea> buscaTodosServicoAreas() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM ServicoArea";
        ResultSet rs = stmt.executeQuery(qry);

        List<ServicoArea> servicoAreasEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            servicoAreasEncontrados
                    .add(new ServicoArea(
                            rs.getString(1),
                            rs.getString(2)));
            i++;
        }

        return servicoAreasEncontrados;
    }

    @Override
    public boolean atualizaServicoArea(
            Object pK, 
            ServicoArea servicoAreaAtualizado) throws SQLException {
        String qry = "UPDATE ServicoArea "
                + "SET codServicoArea = ?, nomServicoArea = ? "
                + "WHERE codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoAreaAtualizado.getCodServicoArea());
        pStmt.setString(2, servicoAreaAtualizado.getNomServicoArea());
        if (pK instanceof String) {
            pStmt.setString(3, pK.toString());
        } else {
            pStmt.setInt(3, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaServicoArea(Object pK) throws SQLException {
        String qry = "DELETE FROM ServicoArea "
                + "WHERE codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
