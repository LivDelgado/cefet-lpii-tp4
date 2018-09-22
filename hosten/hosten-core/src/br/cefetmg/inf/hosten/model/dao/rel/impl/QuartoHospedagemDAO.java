package br.cefetmg.inf.hosten.model.dao.rel.impl;

import br.cefetmg.inf.hosten.model.domain.rel.QuartoHospedagem;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.IQuartoHospedagemDAO;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoEstado;
import java.sql.Statement;

public class QuartoHospedagemDAO implements IQuartoHospedagemDAO {

    private static QuartoHospedagemDAO instancia;
    private static Connection con;

    private QuartoHospedagemDAO() {
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized QuartoHospedagemDAO getInstance() {
        if (instancia == null) {
            instancia = new QuartoHospedagemDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(QuartoHospedagem quartoHospedagem) throws SQLException {
        String qry = "INSERT INTO "
                + "QuartoHospedagem("
                + "seqHospedagem, "
                + "nroQuarto, "
                + "nroAdultos, nroCriancas, "
                + "vlrDiaria) "
                + "VALUES(?,?,?,?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quartoHospedagem.getSeqHospedagem());
        pStmt.setInt(2, quartoHospedagem.getNroQuarto());
        pStmt.setInt(3, quartoHospedagem.getNroAdultos());
        pStmt.setInt(4, quartoHospedagem.getNroCriancas());
        pStmt.setDouble(5, quartoHospedagem.getVlrDiaria());
        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<QuartoHospedagem> busca(Object dadoBusca, String coluna)
            throws SQLException {
        String qry = "SELECT * "
                + "FROM QuartoHospedagem "
                + "WHERE " + coluna + " = ?";

        PreparedStatement pStmt = con.prepareStatement(qry);
        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<QuartoHospedagem> quartoHospedagemEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            quartoHospedagemEncontrados
                    .add(new QuartoHospedagem(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getDouble(5)));
            i++;
        }
        return quartoHospedagemEncontrados;
    }

    @Override
    public List<QuartoEstado> buscaTodos() throws SQLException {
        String qry
                = "SELECT A.seqHospedagem, B.nroQuarto, A.nroAdultos, A.nroCriancas, "
                + "A.vlrDiaria, B.idtOcupado, C.datCheckOut " 
                + "FROM Quarto B " 
                + "LEFT JOIN QuartoHospedagem A ON A.nroQuarto = B.nroQuarto " 
                + "LEFT JOIN Hospedagem C ON A.seqHospedagem = C.seqHospedagem";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(qry);

        List<QuartoEstado> quartoEstadoEncontrados = new ArrayList<>();

        while (rs.next()) {
            quartoEstadoEncontrados
                    .add(new QuartoEstado(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getDouble(5),
                            rs.getBoolean(6),
                            rs.getTimestamp(7)));
        }
        return quartoEstadoEncontrados;
    }

    @Override
    public boolean deletaPorPk(int seqHospedagem, int nroQuarto) throws SQLException {
        String qry = "DELETE FROM QuartoHospedagem "
                + "WHERE seqHospedagem = ? AND "
                + "nroQuarto = ? ";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleta(QuartoHospedagem quartoHospedagem) throws SQLException {
        String qry = "DELETE FROM QuartoHospedagem "
                + "WHERE seqHospedagem = ? AND "
                + "nroQuarto = ? ";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quartoHospedagem.getSeqHospedagem());
        pStmt.setInt(2, quartoHospedagem.getNroQuarto());
        return pStmt.executeUpdate() > 0;
    }

}
