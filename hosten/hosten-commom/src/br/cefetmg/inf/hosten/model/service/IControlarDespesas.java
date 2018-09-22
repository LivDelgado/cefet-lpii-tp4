package br.cefetmg.inf.hosten.model.service;

import br.cefetmg.inf.hosten.model.domain.rel.Despesa;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoConsumo;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IControlarDespesas {
    boolean inserir(QuartoConsumo quartoConsumo) 
            throws NegocioException, SQLException;

    List<Despesa> listar(int seqHospedagem, int nroQuarto)
            throws NegocioException, SQLException;

    boolean excluir(QuartoConsumo quartoConsumo) 
            throws NegocioException, SQLException;
    
    Map<String, Object> retornaRelatorioDespesas(int seqHospedagem, int nroQuarto) 
            throws NegocioException, SQLException;
}
