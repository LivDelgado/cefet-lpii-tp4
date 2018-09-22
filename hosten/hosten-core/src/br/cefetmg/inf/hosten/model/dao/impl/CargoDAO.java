package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.ICargoDAO;
import br.cefetmg.inf.hosten.model.domain.Cargo;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO implements ICargoDAO{

    private static CargoDAO instancia;
    private static Connection con;

    private CargoDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized CargoDAO getInstance() {
        if (instancia == null) {
            instancia = new CargoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaCargo(Cargo cargo) throws SQLException {
        String qry = "INSERT INTO Cargo"
                + "(codCargo, nomCargo, idtMaster)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cargo.getCodCargo());
        pStmt.setString(2, cargo.getNomCargo());
        pStmt.setBoolean(3, cargo.isIdtMaster());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<Cargo> buscaCargo(Object dadoBusca, String coluna)
            throws SQLException {
        String qry = "SELECT * FROM Cargo "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        List<Cargo> cargosEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            cargosEncontrados
                    .add(new Cargo(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getBoolean(3)));
            i++;
        }

        return cargosEncontrados;
    }

    @Override
    public List<Cargo> buscaTodosCargos() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM Cargo";
        ResultSet rs = stmt.executeQuery(qry);

        List<Cargo> cargosEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            cargosEncontrados
                    .add(new Cargo(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getBoolean(3)));
            i++;
        }

        return cargosEncontrados;
    }

    @Override
    public boolean atualizaCargo(Object pK, Cargo cargoAtualizado) 
            throws SQLException {
        String qry = "UPDATE Cargo "
                + "SET codCargo = ?, nomCargo = ?, idtMaster = ? "
                + "WHERE codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cargoAtualizado.getCodCargo());
        pStmt.setString(2, cargoAtualizado.getNomCargo());
        pStmt.setBoolean(3, cargoAtualizado.isIdtMaster());
        if (pK instanceof String) {
            pStmt.setString(4, pK.toString());
        } else {
            pStmt.setInt(4, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaCargo(Object pK) throws SQLException {
        String qry = "DELETE FROM Cargo "
                + "WHERE codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
