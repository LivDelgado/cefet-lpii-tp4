package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IHospedeDAO;
import br.cefetmg.inf.hosten.model.domain.Hospede;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class HospedeDAO implements IHospedeDAO{

    private static Connection con;
    private static HospedeDAO instancia;

    private HospedeDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized HospedeDAO getInstance() {
        if (instancia == null) {
            instancia = new HospedeDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaHospede(Hospede hospede) throws SQLException {
        String qry = "INSERT INTO Hospede"
                + "(codCPF, nomHospede, desTelefone, desEmail)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, hospede.getCodCPF());
        pStmt.setString(2, hospede.getNomHospede());
        pStmt.setString(3, hospede.getDesTelefone());
        pStmt.setString(4, hospede.getDesEmail());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Hospede> buscaHospede(
            Object dadoBusca, 
            String coluna) throws SQLException {
        String qry = "SELECT * FROM Hospede "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<Hospede> hospedesEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            hospedesEncontrados
                    .add(new Hospede(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)));
            i++;
        }
        return hospedesEncontrados;
    }

    @Override
    public List<Hospede> buscaTodosHospedes() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Hospede";
        ResultSet rs = stmt.executeQuery(qry);

        List<Hospede> hospedesEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            hospedesEncontrados
                    .add(new Hospede(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)));
            i++;
        }

        return hospedesEncontrados;
    }

    @Override
    public boolean atualizaHospede(
            Object pK, 
            Hospede hospedeAtualizado) throws SQLException {
        String qry = "UPDATE Hospede "
                + "SET codCPF = ?, nomHospede = ?, desTelefone = ?, desEmail = ? "
                + "WHERE codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, hospedeAtualizado.getCodCPF());
        pStmt.setString(2, hospedeAtualizado.getNomHospede());
        pStmt.setString(3, hospedeAtualizado.getDesTelefone());
        pStmt.setString(4, hospedeAtualizado.getDesEmail());
        if (pK instanceof String) {
            pStmt.setString(5, pK.toString());
        } else {
            pStmt.setInt(5, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaHospede(Object pK) throws SQLException {
        String qry = "DELETE FROM Hospede "
                + "WHERE codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
