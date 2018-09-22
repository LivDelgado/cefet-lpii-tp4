package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import java.sql.SQLException;
import java.util.List;

public interface ICategoriaQuartoDAO {

    boolean adicionaCategoriaQuarto(CategoriaQuarto categoriaQuarto)
            throws SQLException;

    List<CategoriaQuarto> buscaCategoriaQuarto(Object dadoBusca, String coluna)
            throws SQLException;

    List<CategoriaQuarto> buscaTodosCategoriaQuartos()
            throws SQLException;

    boolean atualizaCategoriaQuarto(Object pK, CategoriaQuarto categoriaQuartoAtualizado)
            throws SQLException;

    boolean deletaCategoriaQuarto(Object pK) throws SQLException;

}
