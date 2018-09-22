package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Cargo;
import java.sql.SQLException;
import java.util.List;

public interface ICargoDAO {
    boolean adicionaCargo(Cargo cargo)
            throws SQLException;

    List<Cargo> buscaCargo(Object dadoBusca, String coluna)
            throws SQLException;

    List<Cargo> buscaTodosCargos()
            throws SQLException;

    boolean atualizaCargo(Object pK, Cargo cargoAtualizado)
            throws SQLException;

    boolean deletaCargo(Object pK) throws SQLException;
}
