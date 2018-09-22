package br.cefetmg.inf.hosten.model.dao.rel;

import br.cefetmg.inf.hosten.model.domain.rel.QuartoEstado;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoHospedagem;
import java.sql.SQLException;
import java.util.List;

public interface IQuartoHospedagemDAO {

    boolean adiciona(QuartoHospedagem quartoHospedagem) throws SQLException;

    List<QuartoHospedagem> busca(Object dadoBusca, String coluna) throws SQLException;
    
    List<QuartoEstado> buscaTodos() throws SQLException;

    //atualiza();
    boolean deletaPorPk(int seqHospedagem, int nroQuarto) throws SQLException;

    boolean deleta(QuartoHospedagem quartoHospedagem) throws SQLException;
}
