package br.cefetmg.inf.util.bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class BdUtils {

    private static Connection con = new ConnectionFactory().getConnection();

    public static int contaLinhasResultSet(ResultSet rs) throws SQLException {
        rs.last();
        int nroLinhas = rs.getRow();

        return nroLinhas;
    }
    
    public static void apagarBDHosten() throws SQLException {
        Statement stmt = con.createStatement();
        stmt.execute("DROP SCHEMA public CASCADE;"
                + "CREATE SCHEMA public;"
                + "GRANT ALL ON SCHEMA public TO postgres;"
                + "GRANT ALL ON SCHEMA public TO public;");
    }
}
