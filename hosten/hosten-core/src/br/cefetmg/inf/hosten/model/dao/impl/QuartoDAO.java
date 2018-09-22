package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IQuartoDAO;
import br.cefetmg.inf.hosten.model.domain.Quarto;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO implements IQuartoDAO {

    private static Connection con;
    private static QuartoDAO instancia;

    private QuartoDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized QuartoDAO getInstance() {
        if (instancia == null) {
            instancia = new QuartoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaQuarto(Quarto quarto) throws SQLException {
        String qry = "INSERT INTO Quarto"
                + "(nroQuarto, codCategoria, idtOcupado)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quarto.getNroQuarto());
        pStmt.setString(2, quarto.getCodCategoria());
        pStmt.setBoolean(3, quarto.isIdtOcupado());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Quarto> buscaQuarto(
            Object dadoBusca,
            String coluna) throws SQLException {
        String qry = "SELECT * FROM Quarto "
                + "WHERE " + coluna + " "
                + "= ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<Quarto> quartoEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            quartoEncontrados
                    .add(new Quarto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getBoolean(3)));
            i++;
        }

        return quartoEncontrados;
    }

    @Override
    public List<Quarto> buscaTodosQuartos() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Quarto";
        ResultSet rs = stmt.executeQuery(qry);

        List<Quarto> quartosEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            quartosEncontrados
                    .add(new Quarto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getBoolean(3)));
            i++;
        }

        return quartosEncontrados;
    }

    @Override
    public boolean atualizaQuarto(Object pK, Quarto quartoAtualizado) 
            throws SQLException {
        String qry = "UPDATE Quarto "
                + "SET nroQuarto = ?, codCategoria = ?, idtOcupado = ? "
                + "WHERE nroQuarto = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quartoAtualizado.getNroQuarto());
        pStmt.setString(2, quartoAtualizado.getCodCategoria());
        pStmt.setBoolean(3, quartoAtualizado.isIdtOcupado());
        if (pK instanceof String) {
            pStmt.setString(4, pK.toString());
        } else {
            pStmt.setInt(4, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaQuarto(Object pK) throws SQLException {
        String qry = "DELETE FROM Quarto "
                + "WHERE nroQuarto = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
    
    @Override
    public int buscaUltimoRegistroRelacionadoAoQuarto(int nroQuarto)
            throws SQLException {
        String qry = "SELECT A.seqHospedagem "
                + "FROM Hospedagem A "
                + "JOIN QuartoHospedagem B ON A.seqHospedagem = B.seqHospedagem "
                + "WHERE B.nroQuarto = ? "
                + "ORDER BY A.datCheckIn DESC "
                + "LIMIT 1";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, nroQuarto);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }
}
