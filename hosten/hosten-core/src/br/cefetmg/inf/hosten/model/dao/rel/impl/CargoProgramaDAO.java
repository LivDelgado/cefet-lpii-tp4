package br.cefetmg.inf.hosten.model.dao.rel.impl;

import br.cefetmg.inf.hosten.model.domain.rel.CargoPrograma;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.ICargoProgramaDAO;
import br.cefetmg.inf.hosten.model.domain.Programa;
import java.sql.Statement;

public class CargoProgramaDAO implements ICargoProgramaDAO {

    private static CargoProgramaDAO instancia;
    private static Connection con;

    private CargoProgramaDAO() {
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized CargoProgramaDAO getInstance() {
        if (instancia == null) {
            instancia = new CargoProgramaDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(CargoPrograma cargoPrograma) throws SQLException {
        String qry = "INSERT INTO "
                + "CargoPrograma(codPrograma, codCargo) "
                + "VALUES(?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cargoPrograma.getCodPrograma());
        pStmt.setString(2, cargoPrograma.getCodCargo());
        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<CargoPrograma> busca(String cod, String coluna)
            throws SQLException {
        String qry;
        if (coluna.equals("codCargo")) {
            qry = "SELECT * FROM CargoPrograma "
                    + "WHERE codCargo = ?";
        } else {
            qry = "SELECT * FROM CargoPrograma "
                    + "WHERE codPrograma = ?";
        }
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cod);

        ResultSet rs = pStmt.executeQuery();

        List<CargoPrograma> cargoProgramaEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            cargoProgramaEncontrados
                    .add(new CargoPrograma(
                            rs.getString(1),
                            rs.getString(2)));
            i++;
        }
        return cargoProgramaEncontrados;
    }

    @Override
    public List<Programa> buscaProgramasRelacionados(String codCargo) 
            throws SQLException {
        String qry = "SELECT B.codPrograma, B.desPrograma "
                + "FROM CargoPrograma A "
                + "JOIN Programa B ON A.codPrograma = B.codPrograma "
                + "WHERE A.codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codCargo);
        ResultSet rs = pStmt.executeQuery(qry);

        List<Programa> programasEncontrados = new ArrayList<>();

        while (rs.next()) {
            programasEncontrados
                    .add(new Programa(
                            rs.getString(1),
                            rs.getString(2)));
        }
        return programasEncontrados;
    }

    @Override
    public boolean deletaPorColuna(String cod, String coluna)
            throws SQLException {
        String qry = "DELETE FROM CargoPrograma "
                + "WHERE " + coluna + " = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cod);
        return pStmt.executeUpdate() > 0;
    }
}
