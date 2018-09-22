package br.cefetmg.inf.hosten.model.service;

import br.cefetmg.inf.hosten.model.domain.Servico;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;

public interface IManterServico {

    public boolean inserir(Servico servico) 
            throws NegocioException, SQLException;

    public List<Servico> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException;
    public List<Servico> listarTodos()
            throws NegocioException, SQLException;

    public boolean alterar(String codRegistro, Servico servico)
            throws NegocioException, SQLException;

    public boolean excluir(String codRegistro) 
            throws NegocioException, SQLException;
}
