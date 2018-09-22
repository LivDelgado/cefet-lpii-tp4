package br.cefetmg.inf.hosten.model.dao.rel.impl;

import br.cefetmg.inf.hosten.model.domain.rel.QuartoConsumo;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.IQuartoConsumoDAO;

public class QuartoConsumoDAO implements IQuartoConsumoDAO {

    private static QuartoConsumoDAO instancia;
    private static Connection con;

    private QuartoConsumoDAO() {
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized QuartoConsumoDAO getInstance() {
        if (instancia == null) {
            instancia = new QuartoConsumoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(QuartoConsumo quartoConsumo) throws SQLException {
        String qry = "INSERT INTO "
                + "QuartoConsumo(seqHospedagem, nroQuarto, datConsumo,"
                + "qtdConsumo, seqServico, codUsuarioRegistro) "
                + "VALUES(?,?,?,?,?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quartoConsumo.getSeqHospedagem());
        pStmt.setInt(2, quartoConsumo.getNroQuarto());
        pStmt.setTimestamp(3, quartoConsumo.getDatConsumo());
        pStmt.setInt(4, quartoConsumo.getQtdConsumo());
        pStmt.setInt(5, quartoConsumo.getSeqServico());
        pStmt.setString(6, quartoConsumo.getCodUsuarioRegistro());
        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<QuartoConsumo> busca(Object dadoBusca, String coluna)
            throws SQLException {
        String qry = "SELECT * "
                + "FROM QuartoConsumo "
                + "WHERE " + coluna + " = ?";

        PreparedStatement pStmt = con.prepareStatement(qry);
        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<QuartoConsumo> quartoConsumoEncontrados = new ArrayList<>();

        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            quartoConsumoEncontrados
                    .add(new QuartoConsumo(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getTimestamp(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getString(6)));
            i++;
        }
        return quartoConsumoEncontrados;
    }

    @Override
    public boolean deletaPorPk(int seqHospedagem, int nroQuarto,
            Timestamp datConsumo) throws SQLException {
        String qry = "DELETE FROM QuartoConsumo "
                + "WHERE seqHospedagem = ? AND nroQuarto = ? AND datConsumo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        pStmt.setTimestamp(3, datConsumo);
        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleta(QuartoConsumo quartoConsumo) throws SQLException {
        String qry = "DELETE FROM QuartoConsumo "
                + "WHERE seqHospedagem = ? AND nroQuarto = ? AND datConsumo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quartoConsumo.getSeqHospedagem());
        pStmt.setInt(2, quartoConsumo.getNroQuarto());
        pStmt.setTimestamp(3, quartoConsumo.getDatConsumo());
        return pStmt.executeUpdate() > 0;
    }
}
