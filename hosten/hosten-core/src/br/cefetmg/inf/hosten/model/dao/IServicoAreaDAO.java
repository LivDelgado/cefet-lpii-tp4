package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.ServicoArea;
import java.sql.SQLException;
import java.util.List;

public interface IServicoAreaDAO {

    boolean adicionaServicoArea(ServicoArea servicoArea)
            throws SQLException;

    List<ServicoArea> buscaServicoArea(Object dadoBusca, String coluna)
            throws SQLException;

    List<ServicoArea> buscaTodosServicoAreas()
            throws SQLException;

    boolean atualizaServicoArea(Object pK, ServicoArea servicoAreaAtualizado)
            throws SQLException;

    boolean deletaServicoArea(Object pK) throws SQLException; 
}
