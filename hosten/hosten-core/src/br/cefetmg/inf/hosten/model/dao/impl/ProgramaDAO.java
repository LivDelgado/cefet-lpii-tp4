package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IProgramaDAO;
import br.cefetmg.inf.hosten.model.domain.Programa;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class ProgramaDAO implements IProgramaDAO {

    private static Connection con;
    private static ProgramaDAO instancia;

    private ProgramaDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized ProgramaDAO getInstance() {
        if (instancia == null) {
            instancia = new ProgramaDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaPrograma(Programa programa) throws SQLException {
        String qry = "INSERT INTO Programa"
                + "(codPrograma, desPrograma) "
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, programa.getCodPrograma());
        pStmt.setString(2, programa.getDesPrograma());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Programa> buscaPrograma(
            Object dadoBusca, 
            String coluna) throws SQLException {
        String qry = "SELECT * FROM Programa "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<Programa> programasEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            programasEncontrados
                    .add(new Programa(
                            rs.getString(1),
                            rs.getString(2)));
            i++;
        }

        return programasEncontrados;
    }

    @Override
    public List<Programa> buscaTodosProgramas() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Programa";
        ResultSet rs = stmt.executeQuery(qry);

        List<Programa> programasEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            programasEncontrados
                    .add(new Programa(
                            rs.getString(1),
                            rs.getString(2)));
            i++;
        }

        return programasEncontrados;
    }

    @Override
    public boolean atualizaPrograma(
            Object pK, 
            Programa programaAtualizado) throws SQLException {
        String qry = "UPDATE Programa "
                + "SET codPrograma = ?, desPrograma = ? "
                + "WHERE codPrograma LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, programaAtualizado.getCodPrograma());
        pStmt.setString(2, programaAtualizado.getDesPrograma());
        if (pK instanceof String) {
            pStmt.setString(3, pK.toString());
        } else {
            pStmt.setInt(3, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaPrograma(Object pK) throws SQLException {
        String qry = "DELETE FROM Programa "
                + "WHERE codPrograma LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
