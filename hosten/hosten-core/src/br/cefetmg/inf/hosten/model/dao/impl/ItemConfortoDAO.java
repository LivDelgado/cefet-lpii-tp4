package br.cefetmg.inf.hosten.model.dao.impl;

import br.cefetmg.inf.hosten.model.dao.IItemConfortoDAO;
import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class ItemConfortoDAO implements IItemConfortoDAO {

    private static Connection con;
    private static ItemConfortoDAO instancia;

    private ItemConfortoDAO() {
        super();
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized ItemConfortoDAO getInstance() {
        if (instancia == null) {
            instancia = new ItemConfortoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adicionaItemConforto(
            ItemConforto itemConforto) throws SQLException {
        String qry = "INSERT INTO ItemConforto"
                + "(codItem, desItem)"
                + " VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, itemConforto.getCodItem());
        pStmt.setString(2, itemConforto.getDesItem());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public List<ItemConforto> buscaItemConforto(
            Object dadoBusca, 
            String coluna) throws SQLException {
        String qry = "SELECT * FROM ItemConforto "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        
        if(dadoBusca instanceof String) 
            pStmt.setString(1, dadoBusca.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        
        ResultSet rs = pStmt.executeQuery();

        List<ItemConforto> itemConfortoEncontrados = new ArrayList<>();

        int i = 0;
        while (rs.next()) {
            itemConfortoEncontrados
                    .add(new ItemConforto(
                            rs.getString(1), 
                            rs.getString(2)));
            i++;
        }

        return itemConfortoEncontrados;
    }
    
    @Override
    public List<ItemConforto> buscaTodosItemConfortos() throws SQLException {
        Statement stmt = con.createStatement();

        String qry = "SELECT * FROM ItemConforto";
        ResultSet rs = stmt.executeQuery(qry);

        List<ItemConforto> itemConfortosEncontrados = new ArrayList<>();
        
        int i = 0;
        while (rs.next()) {
            itemConfortosEncontrados
                    .add(new ItemConforto(
                            rs.getString(1), 
                            rs.getString(2)));
            i++;
        }

        return itemConfortosEncontrados;
    }

    @Override
    public boolean atualizaItemConforto(
            Object pK, 
            ItemConforto itemConfortoAtualizado) throws SQLException {
        String qry = "UPDATE ItemConforto "
                + "SET codItem = ?, desItem = ?"
                + "WHERE codItem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, itemConfortoAtualizado.getCodItem());
        pStmt.setString(2, itemConfortoAtualizado.getDesItem());
        if(pK instanceof String) 
            pStmt.setString(3, pK.toString());
        else 
            pStmt.setInt(3, Integer.parseInt(pK.toString()));

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deletaItemConforto(Object pK) throws SQLException {
        String qry = "DELETE FROM ItemConforto "
                + "WHERE codItem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if(pK instanceof String) 
            pStmt.setString(1, pK.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(pK.toString()));

        return pStmt.executeUpdate() > 0;
    }
}
