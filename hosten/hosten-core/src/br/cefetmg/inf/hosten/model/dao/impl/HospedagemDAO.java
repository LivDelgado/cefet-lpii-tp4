package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IHospedagemDAO;
import br.cefetmg.inf.hosten.model.domain.Hospedagem;
import br.cefetmg.inf.util.bd.BdUtils;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class HospedagemDAO implements IHospedagemDAO{

    private static Connection con;
    private static HospedagemDAO instancia;

    private HospedagemDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized HospedagemDAO getInstance() {
        if (instancia == null) {
            instancia = new HospedagemDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaHospedagem(
            Hospedagem hospedagem) throws SQLException {
        String qry = "INSERT INTO Hospedagem"
                + "(datCheckIn, datCheckOut, vlrPago, codCPF)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagem.getDatCheckOut());
        pStmt.setDouble(3, hospedagem.getVlrPago());
        pStmt.setString(4, hospedagem.getCodCPF());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Hospedagem> buscaHospedagem(Object dadoBusca, String coluna)
            throws SQLException {
        String qry = "SELECT * FROM Hospedagem "
                + "WHERE " + coluna + " "
                + "= ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }
        ResultSet rs = pStmt.executeQuery();

        List<Hospedagem> hospedagemEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            hospedagemEncontrados
                    .add(new Hospedagem(
                            rs.getInt(1),
                            rs.getTimestamp(2),
                            rs.getTimestamp(3),
                            rs.getDouble(4),
                            rs.getString(5)));
            i++;
        }

        return hospedagemEncontrados;
    }

    @Override
    public List<Hospedagem> buscaTodosHospedagems() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Hospedagem";
        ResultSet rs = stmt.executeQuery(qry);

        List<Hospedagem> hospedagemsEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            hospedagemsEncontrados
                    .add(new Hospedagem(
                            rs.getInt(1),
                            rs.getTimestamp(2),
                            rs.getTimestamp(3),
                            rs.getDouble(4),
                            rs.getString(5)));
            i++;
        }

        return hospedagemsEncontrados;
    }

    public List<Hospedagem> buscaHospedagem(
            Hospedagem hospedagem) throws SQLException {
        String qry = "SELECT * FROM Hospedagem WHERE "
                + "datCheckIn=? AND datCheckOut=? AND vlrPago=? AND codCPF=?";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagem.getDatCheckOut());
        pStmt.setDouble(3, hospedagem.getVlrPago());
        pStmt.setString(4, hospedagem.getCodCPF());

        ResultSet rs = pStmt.executeQuery();
        List<Hospedagem> hospedagemsEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            hospedagemsEncontrados
                    .add(new Hospedagem(
                            rs.getInt(1),
                            rs.getTimestamp(2),
                            rs.getTimestamp(3),
                            rs.getDouble(4),
                            rs.getString(5)));
            i++;
        }

        return hospedagemsEncontrados;
    }

    @Override
    public boolean atualizaHospedagemPorPk(
            Object pK, 
            Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "UPDATE Hospedagem "
                + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, "
                + "codCPF = ? "
                + "WHERE seqHospedagem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
        pStmt.setString(4, hospedagemAtualizado.getCodCPF());
        if (pK instanceof String) {
            pStmt.setString(5, pK.toString());
        } else {
            pStmt.setInt(5, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean atualizaHospedagem(Hospedagem hospedagemAntiga,
            Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "UPDATE Hospedagem "
                + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ? "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
        pStmt.setString(4, hospedagemAtualizado.getCodCPF());
        pStmt.setTimestamp(5, hospedagemAntiga.getDatCheckIn());
        pStmt.setTimestamp(6, hospedagemAntiga.getDatCheckOut());
        pStmt.setDouble(7, hospedagemAntiga.getVlrPago());
        pStmt.setString(8, hospedagemAntiga.getCodCPF());

        return pStmt.executeUpdate() > 0;
    }

    public boolean atualiza(Timestamp datCheckInAntiga, 
            Timestamp datCheckOutAntiga,
            Double vlrPagoAntigo, String codCPFAntigo,
            Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "SELECT * FROM Hospedagem "
                + "WHERE datCheckIn = ? AND datCheckOut = ? "
                + "AND vlrPago = ? AND codCPF = ?";

        PreparedStatement pStmt = con.prepareStatement(qry, 
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setTimestamp(1, datCheckInAntiga);
        pStmt.setTimestamp(2, datCheckOutAntiga);
        pStmt.setDouble(3, vlrPagoAntigo);
        pStmt.setString(4, codCPFAntigo);

        ResultSet rs = pStmt.executeQuery();

        if (BdUtils.contaLinhasResultSet(rs) == 1) {
            qry = "UPDATE Hospedagem "
                    + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ? "
                    + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? "
                    + "AND codCPF = ?";
            pStmt = con.prepareStatement(qry);
            pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
            pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
            pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
            pStmt.setString(4, hospedagemAtualizado.getCodCPF());
            pStmt.setTimestamp(5, datCheckInAntiga);
            pStmt.setTimestamp(6, datCheckOutAntiga);
            pStmt.setDouble(7, vlrPagoAntigo);
            pStmt.setString(8, codCPFAntigo);

            return pStmt.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean deletaHospedagem(Object pK) throws SQLException {
        String qry = "DELETE FROM Hospedagem "
                + "WHERE seqHospedagem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    public boolean deleta(Hospedagem hospedagemAntiga) throws SQLException {
        String qry = "DELETE FROM Hospedagem "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAntiga.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAntiga.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAntiga.getVlrPago());
        pStmt.setString(4, hospedagemAntiga.getCodCPF());

        return pStmt.executeUpdate() > 0;
    }

    public boolean deleta(Timestamp datCheckInAntiga, Timestamp datCheckOutAntiga,
            Double vlrPagoAntigo, String codCPFAntigo) throws SQLException {
        String qry = "SELECT * FROM Hospedagem "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";

        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setTimestamp(1, datCheckInAntiga);
        pStmt.setTimestamp(2, datCheckOutAntiga);
        pStmt.setDouble(3, vlrPagoAntigo);
        pStmt.setString(4, codCPFAntigo);

        ResultSet rs = pStmt.executeQuery();

        if (BdUtils.contaLinhasResultSet(rs) == 1) {
            qry = "DELETE FROM Hospedagem "
                    + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? "
                    + "AND codCPF = ?";
            pStmt = con.prepareStatement(qry);
            pStmt.setTimestamp(1, datCheckInAntiga);
            pStmt.setTimestamp(2, datCheckOutAntiga);
            pStmt.setDouble(3, vlrPagoAntigo);
            pStmt.setString(4, codCPFAntigo);

            return pStmt.executeUpdate() > 0;
        }
        return false;
    }
    
    public List<Hospedagem> busca(Hospedagem hospedagem) throws SQLException {
        String qry = "SELECT * FROM Hospedagem WHERE "
                + "datCheckIn=? AND datCheckOut=? AND vlrPago=? AND codCPF=?";

        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setTimestamp(1, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagem.getDatCheckOut());
        pStmt.setDouble(3, hospedagem.getVlrPago());
        pStmt.setString(4, hospedagem.getCodCPF());

        ResultSet rs = pStmt.executeQuery();
        List<Hospedagem> hospedagemsEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            hospedagemsEncontrados
                    .add(new Hospedagem(
                            rs.getInt(1),
                            rs.getTimestamp(2),
                            rs.getTimestamp(3),
                            rs.getDouble(4),
                            rs.getString(5)));
            i++;
        }

        return hospedagemsEncontrados;
    }

}
