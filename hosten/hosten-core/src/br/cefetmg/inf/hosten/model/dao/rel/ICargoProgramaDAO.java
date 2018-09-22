package br.cefetmg.inf.hosten.model.dao.rel;

import br.cefetmg.inf.hosten.model.domain.Programa;
import br.cefetmg.inf.hosten.model.domain.rel.CargoPrograma;
import java.sql.SQLException;
import java.util.List;

public interface ICargoProgramaDAO {
    boolean adiciona(CargoPrograma cargoPrograma) throws SQLException;
    
    List<CargoPrograma> busca(String cod, String coluna) throws SQLException;
    
    List<Programa> buscaProgramasRelacionados(String codCargo) throws SQLException;
    
    //atualiza();
    
    boolean deletaPorColuna(String cod, String coluna) throws SQLException;
}
