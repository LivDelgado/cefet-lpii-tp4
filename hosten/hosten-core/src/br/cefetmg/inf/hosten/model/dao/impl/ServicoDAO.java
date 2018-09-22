package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IServicoDAO;
import br.cefetmg.inf.hosten.model.domain.Servico;
import br.cefetmg.inf.util.bd.BdUtils;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO implements IServicoDAO {

    private Connection con;
    private static ServicoDAO instancia;

    private ServicoDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized ServicoDAO getInstance() {
        if (instancia == null) {
            instancia = new ServicoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaServico(Servico servico) throws SQLException {
        String qry = "INSERT INTO Servico"
                + "(desServico, vlrUnit, codServicoArea)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servico.getDesServico());
        pStmt.setDouble(2, servico.getVlrUnit());
        pStmt.setString(3, servico.getCodServicoArea());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Servico> buscaServico(Object dadoBusca, String coluna) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Servico "
                + "WHERE " + coluna + " "
                + "= ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<Servico> servicoEncontrados = new ArrayList<>();

        rs.beforeFirst();
        while (rs.next()) {
            servicoEncontrados
                    .add(new Servico(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getString(4)));
            i++;
        }

        return servicoEncontrados;
    }

    @Override
    public List<Servico> buscaTodosServicos() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Servico";
        ResultSet rs = stmt.executeQuery(qry);

        List<Servico> servicosEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            servicosEncontrados
                    .add(new Servico(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getString(4)));
            i++;
        }

        return servicosEncontrados;
    }

    @Override
    public boolean atualizaServicoPorPk(Object pK, Servico servicoAtualizado) 
            throws SQLException {
        String qry = "UPDATE Servico "
                + "SET desServico = ?, vlrUnit = ?, codServicoArea = ? "
                + "WHERE seqServico = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoAtualizado.getDesServico());
        pStmt.setDouble(2, servicoAtualizado.getVlrUnit());
        pStmt.setString(3, servicoAtualizado.getCodServicoArea());
        pStmt.setInt(4, Integer.parseInt(pK.toString()));

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean atualizaServico(
            Servico servicoAntigo, 
            Servico servicoAtualizado) 
            throws SQLException {
        String qry = "UPDATE Servico "
                + "SET desServico = ?, vlrUnit = ?, codServicoArea = ? "
                + "WHERE desServico = ? AND vlrUnit = ? AND codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoAtualizado.getDesServico());
        pStmt.setDouble(2, servicoAtualizado.getVlrUnit());
        pStmt.setString(3, servicoAtualizado.getCodServicoArea());
        pStmt.setString(4, servicoAntigo.getDesServico());
        pStmt.setDouble(5, servicoAntigo.getVlrUnit());
        pStmt.setString(6, servicoAntigo.getCodServicoArea());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean atualizaServicoPorAtributos(
            String desServicoAntigo, 
            String codServicoAreaAntigo,
            Servico servicoAtualizado) 
            throws SQLException {
        String qry = "SELECT * FROM Servico "
                + "WHERE desServico = ? AND codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(
                qry, 
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, desServicoAntigo);
        pStmt.setString(2, codServicoAreaAntigo);
        ResultSet rs = pStmt.executeQuery();

        if (BdUtils.contaLinhasResultSet(rs) == 1) {
            qry = "UPDATE Servico "
                    + "SET desServico = ?, vlrUnit = ?, codServicoArea = ? "
                    + "WHERE desServico = ? AND codServicoArea = ?";
            pStmt = con.prepareStatement(qry);
            pStmt.setString(1, servicoAtualizado.getDesServico());
            pStmt.setDouble(2, servicoAtualizado.getVlrUnit());
            pStmt.setString(3, servicoAtualizado.getCodServicoArea());
            pStmt.setString(4, desServicoAntigo);
            pStmt.setString(5, codServicoAreaAntigo);

            return pStmt.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean deletaServicoPorPk(Object pK) throws SQLException {
        String qry = "DELETE FROM Servico "
                + "WHERE seqServico = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaServico(Servico servicoAntigo) throws SQLException {
        String qry = "DELETE FROM Servico "
                + "WHERE desServico = ? AND vlrUnit = ? AND codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoAntigo.getDesServico());
        pStmt.setDouble(2, servicoAntigo.getVlrUnit());
        pStmt.setString(3, servicoAntigo.getCodServicoArea());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaServicoPorAtributos(
            String desServicoAntigo, 
            String codServicoAreaAntigo) throws SQLException {
        String qry = "SELECT * FROM Servico "
                + "WHERE desServico = ? AND codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, desServicoAntigo);
        pStmt.setString(2, codServicoAreaAntigo);
        ResultSet rs = pStmt.executeQuery();

        if (BdUtils.contaLinhasResultSet(rs) == 1) {
            qry = "DELETE FROM Servico "
                    + "WHERE desServico = ? AND codServicoArea = ?";
            pStmt = con.prepareStatement(qry);
            pStmt.setString(1, desServicoAntigo);
            pStmt.setString(2, codServicoAreaAntigo);

            return pStmt.executeUpdate() > 0;
        }
        return false;
    }
}
