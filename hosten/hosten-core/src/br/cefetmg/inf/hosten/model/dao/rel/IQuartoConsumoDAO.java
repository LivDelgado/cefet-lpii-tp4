package br.cefetmg.inf.hosten.model.dao.rel;

import br.cefetmg.inf.hosten.model.domain.rel.QuartoConsumo;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface IQuartoConsumoDAO {

    boolean adiciona(QuartoConsumo quartoConsumo) throws SQLException;

    List<QuartoConsumo> busca(Object dadoBusca, String coluna) throws SQLException;

    //atualiza();
    
    boolean deletaPorPk(int seqHospedagem, int nroQuarto, Timestamp datConsumo)
            throws SQLException;

    boolean deleta(QuartoConsumo quartoConsumo) throws SQLException;
}
