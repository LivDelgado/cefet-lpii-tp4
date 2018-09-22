package br.cefetmg.inf.hosten.model.service;

import br.cefetmg.inf.hosten.model.domain.Hospede;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;

public interface IManterHospede {

    public boolean inserir(Hospede hospede) 
            throws NegocioException, SQLException;

    public List<Hospede> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException;
    public List<Hospede> listarTodos()
            throws NegocioException, SQLException;

    public boolean alterar(String codRegistro, Hospede hospede) 
            throws NegocioException, SQLException;

//    public boolean excluir(String codRegistro) 
//            throws NegocioException, SQLException;
}
