package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Hospedagem;
import java.sql.SQLException;
import java.util.List;

public interface IHospedagemDAO {

    boolean adicionaHospedagem(Hospedagem hospedagem)
            throws SQLException;

    List<Hospedagem> buscaHospedagem(Object dadoBusca, String coluna)
            throws SQLException;

    List<Hospedagem> buscaTodosHospedagems()
            throws SQLException;

    List<Hospedagem> busca(Hospedagem hospedagem) 
            throws SQLException;

    boolean atualizaHospedagemPorPk(Object pK, Hospedagem hospedagemAtualizado)
            throws SQLException;
    
    boolean atualizaHospedagem(Hospedagem hospedagemAntiga,
            Hospedagem hospedagemAtualizado) throws SQLException;

    boolean deletaHospedagem(Object pK) throws SQLException; 
}
