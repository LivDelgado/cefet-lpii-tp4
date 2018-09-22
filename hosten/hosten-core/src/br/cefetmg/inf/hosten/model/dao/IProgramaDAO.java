package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Programa;
import java.sql.SQLException;   
import java.util.List;

public interface IProgramaDAO {
        boolean adicionaPrograma(Programa programa)
            throws SQLException;

    List<Programa> buscaPrograma(Object dadoBusca, String coluna)
            throws SQLException;

    List<Programa> buscaTodosProgramas()
            throws SQLException;

    boolean atualizaPrograma(Object pK, Programa programaAtualizado)
            throws SQLException;

    boolean deletaPrograma(Object pK) throws SQLException;
}
