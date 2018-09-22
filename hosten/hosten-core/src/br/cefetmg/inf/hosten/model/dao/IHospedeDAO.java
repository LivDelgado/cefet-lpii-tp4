package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Hospede;
import java.sql.SQLException;
import java.util.List;

public interface IHospedeDAO {

    boolean adicionaHospede(Hospede hospede)
            throws SQLException;

    List<Hospede> buscaHospede(Object dadoBusca, String coluna)
            throws SQLException;

    List<Hospede> buscaTodosHospedes()
            throws SQLException;

    boolean atualizaHospede(Object pK, Hospede hospedeAtualizado)
            throws SQLException;

    boolean deletaHospede(Object pK) throws SQLException; 
}
