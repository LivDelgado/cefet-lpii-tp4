package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Quarto;
import java.sql.SQLException;
import java.util.List;

public interface IQuartoDAO {

    boolean adicionaQuarto(Quarto quarto)
            throws SQLException;

    List<Quarto> buscaQuarto(Object dadoBusca, String coluna)
            throws SQLException;

    List<Quarto> buscaTodosQuartos()
            throws SQLException;

    boolean atualizaQuarto(Object pK, Quarto quartoAtualizado)
            throws SQLException;

    boolean deletaQuarto(Object pK) throws SQLException;
    
    int buscaUltimoRegistroRelacionadoAoQuarto(int nroQuarto)
            throws SQLException;
}
