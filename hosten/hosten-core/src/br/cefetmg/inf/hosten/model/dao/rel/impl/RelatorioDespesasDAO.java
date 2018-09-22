package br.cefetmg.inf.hosten.model.dao.rel.impl;

import br.cefetmg.inf.hosten.model.domain.rel.Despesa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.IRelatorioDespesasDAO;
import br.cefetmg.inf.util.bd.ConnectionFactory;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class RelatorioDespesasDAO implements IRelatorioDespesasDAO {
    
    private final Connection con;
    private static RelatorioDespesasDAO instancia;
    
    private RelatorioDespesasDAO() {
        con = new ConnectionFactory().getConnection();
    }
    
    public static synchronized RelatorioDespesasDAO getInstance() {
        if (instancia == null) {
            instancia  = new RelatorioDespesasDAO();
        }
        return instancia;
    }
    
    @Override
    public List<Despesa> busca(int seqHospedagem, int nroQuarto) throws SQLException {
        String qry = "SELECT * "
                + "FROM RelatorioDespesas "
                + "WHERE "
                + "seqHospedagem = ? AND "
                + "nroQuarto = ?";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        ResultSet rs = pStmt.executeQuery();

        List<Despesa> despesaEncontradas = new ArrayList<>();

        int i = 0;
        /* 
        int seqHospedagem, int nroQuarto, int nroAdultos, int nroCriancas, 
        Double vlrDiaria, 
        Timestamp datCheckIn, Timestamp datCheckOut, 
        Double vlrPago, 
        String nomeHospede, 
        int seqServico, int qtdConsumo, 
        String desServico, 
        Double vlrUnit
        */
        while (rs.next()) {
            despesaEncontradas
                    .add(new Despesa(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getDouble(5),
                            rs.getTimestamp(6),
                            rs.getTimestamp(7),
                            rs.getDouble(8),
                            rs.getString(9),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getString(12),
                            rs.getDouble(13)));
            i++;
        }
        return despesaEncontradas;
    }
    
    @Override
    public Map<String, Object> retornaRelatorioDespesas(int seqHospedagem, int nroQuarto) 
            throws SQLException {
        String qry = "SELECT * "
                + "FROM  RelatorioDespesas "
                + "WHERE seqHospedagem = ? "
                + "AND nroQuarto = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        ResultSet rs = pStmt.executeQuery();
        
        Map<String, Object> map = new HashMap<>();
        rs.next();
        map.put("seqHospedagem", rs.getInt("seqHospedagem"));
        map.put("nroQuarto", rs.getInt("nroQuarto"));
        map.put("nroAdultos", rs.getInt("nroAdultos"));
        map.put("nroCriancas", rs.getInt("nroCriancas"));
        map.put("vlrDiaria", rs.getDouble("vlrDiaria"));
        map.put("datCheckIn", rs.getTimestamp("datCheckIn"));
        map.put("datCheckOut", rs.getTimestamp("datCheckOut"));
        map.put("vlrPago", rs.getDouble("vlrPago"));
        map.put("nomHospede", rs.getString("nomHospede"));
        
        ArrayList despesas = new ArrayList();
        while(rs.next()) {            
            Map<String, Object> despesa = new HashMap<>();
            map.put("seqServico", rs.getInt("seqServico"));
            map.put("desServico", rs.getString("desServico"));
            map.put("qtdConsumo", rs.getInt("qtdConsumo"));
            map.put("vlrUnit", rs.getDouble("vlrUnit"));
            
            despesas.add(despesa);
        }
        map.put("arrayDespesas", despesas);
        
        return map;
    }
}
