package br.cefetmg.inf.hosten.model.service;

import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;

public interface IManterItemConforto {

    public boolean inserir(ItemConforto itemConforto) 
            throws NegocioException, SQLException;

    public List<ItemConforto> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException;
    public List<ItemConforto> listarTodos()
            throws NegocioException, SQLException;

    public boolean alterar(String codRegistro, ItemConforto itemConforto) 
            throws NegocioException, SQLException;

    public boolean excluir(String codRegistro) 
            throws NegocioException, SQLException;
}
