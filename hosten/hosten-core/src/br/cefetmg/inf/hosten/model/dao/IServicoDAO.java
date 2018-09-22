package br.cefetmg.inf.hosten.model.dao;

import br.cefetmg.inf.hosten.model.domain.Servico;
import java.sql.SQLException;
import java.util.List;

public interface IServicoDAO {

    boolean adicionaServico(Servico servico)
            throws SQLException;

    List<Servico> buscaServico(Object dadoBusca, String coluna)
            throws SQLException;

    List<Servico> buscaTodosServicos()
            throws SQLException;

    boolean atualizaServicoPorPk(Object pK, Servico servicoAtualizado)
            throws SQLException;
    
    boolean atualizaServico(
            Servico servicoAntigo, 
            Servico servicoAtualizado) throws SQLException;
    
    boolean atualizaServicoPorAtributos(
            String desServicoAntigo, 
            String codServicoAreaAntigo,
            Servico servicoAtualizado) 
            throws SQLException;

    boolean deletaServicoPorPk(Object pK) throws SQLException;
    
    boolean deletaServico(Servico servicoAntigo) throws SQLException;
    
    boolean deletaServicoPorAtributos(
            String desServicoAntigo, 
            String codServicoAreaAntigo) 
            throws SQLException;
}
