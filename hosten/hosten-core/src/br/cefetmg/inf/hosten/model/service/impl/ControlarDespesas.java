package br.cefetmg.inf.hosten.model.service.impl;

import br.cefetmg.inf.hosten.model.dao.rel.IQuartoConsumoDAO;
import br.cefetmg.inf.hosten.model.dao.rel.IRelatorioDespesasDAO;
import br.cefetmg.inf.hosten.model.dao.rel.impl.QuartoConsumoDAO;
import br.cefetmg.inf.hosten.model.dao.rel.impl.RelatorioDespesasDAO;
import br.cefetmg.inf.hosten.model.domain.rel.Despesa;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoConsumo;
import br.cefetmg.inf.hosten.model.service.IControlarDespesas;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ControlarDespesas implements IControlarDespesas {

    @Override
    public boolean inserir(QuartoConsumo quartoConsumo) throws NegocioException, SQLException {
        IQuartoConsumoDAO quartoConsumoDAO = QuartoConsumoDAO.getInstance();
        if (quartoConsumo != null) {
            try {
                quartoConsumoDAO.adiciona(quartoConsumo);
                return true;
            } catch (SQLException e) {
            }
        } else {
            throw new NegocioException("O QuartoConsumo passado é inválido");
        }
        return false;
    }

    @Override
    public List<Despesa> listar(int seqHospedagem, int nroQuarto) throws NegocioException, SQLException {
        IRelatorioDespesasDAO relatorioDespesasDAO = RelatorioDespesasDAO.getInstance();
        List<Despesa> despesaEncontradas = null;
        if (seqHospedagem > 0 && nroQuarto > 0) {
            try {
                despesaEncontradas = relatorioDespesasDAO.busca(seqHospedagem, nroQuarto);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new NegocioException("O 'seqHospedagem', e / ou o 'nroQuarto' é(são) inválido(s)");
        }
        return despesaEncontradas;
    }

    @Override
    public boolean excluir(QuartoConsumo quartoConsumo) throws NegocioException, SQLException {
        IQuartoConsumoDAO quartoConsumoDAO = QuartoConsumoDAO.getInstance();
        if (quartoConsumo != null) {
            try {
                quartoConsumoDAO.deleta(quartoConsumo);
                return true;
            } catch (SQLException e) {
            }
        } else {
            throw new NegocioException("O QuartoConsumo passado é inválido");
        }
        return false;
    }

    @Override
    public Map<String, Object> retornaRelatorioDespesas(
            int seqHospedagem, int nroQuarto) 
            throws NegocioException, SQLException {
        
        IRelatorioDespesasDAO relatorioDespesasDAO 
                = RelatorioDespesasDAO.getInstance();
        
        Map<String, Object> despesaEncontradas = null;
        if (seqHospedagem > 0 && nroQuarto > 0) {
            try {
                despesaEncontradas 
                        = relatorioDespesasDAO
                                .retornaRelatorioDespesas(
                                        seqHospedagem, nroQuarto);
            } catch (SQLException e) {
            }
        } else {
            throw new NegocioException(
                    "O 'seqHospedagem', e / ou o 'nroQuarto' é(são) inválido(s)");
        }
        return despesaEncontradas;
    }
}
