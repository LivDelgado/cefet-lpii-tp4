package br.cefetmg.inf.hosten.model.service;

import br.cefetmg.inf.hosten.model.domain.ServicoArea;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;

public interface IManterServicoArea {
    
    public boolean inserir(ServicoArea servicoArea) 
            throws NegocioException, SQLException;
    
    public List<ServicoArea> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException;
    public List<ServicoArea> listarTodos()
            throws NegocioException, SQLException;
    
    public boolean alterar(String codRegistro, ServicoArea servicoArea) 
            throws NegocioException, SQLException;
    
    public boolean excluir(String codRegistro) 
            throws NegocioException, SQLException;
}
