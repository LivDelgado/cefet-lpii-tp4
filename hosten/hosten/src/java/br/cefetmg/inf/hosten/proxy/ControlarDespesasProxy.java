package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.rel.Despesa;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoConsumo;
import br.cefetmg.inf.hosten.model.service.IControlarDespesas;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class ControlarDespesasProxy implements IControlarDespesas {

    ArrayList lista;
    
    public ControlarDespesasProxy() {
        lista = new ArrayList();
        lista.add("Despesa");
    }

    @Override
    public boolean inserir(QuartoConsumo quartoConsumo) 
            throws NegocioException, SQLException {
        lista.add("Inserir");
        lista.add(quartoConsumo);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<Despesa> listar(int seqHospedagem, int nroQuarto) 
            throws NegocioException, SQLException {
        lista.add("Listar");
        lista.add(seqHospedagem);
        lista.add(nroQuarto);
        
        try {
            return (List<Despesa>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public boolean excluir(QuartoConsumo quartoConsumo) 
            throws NegocioException, SQLException {
        lista.add("Excluir");
        lista.add(quartoConsumo);
        
        try {
            return (boolean)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public Map<String, Object> retornaRelatorioDespesas(
            int seqHospedagem, int nroQuarto) 
            throws NegocioException, SQLException {
        lista.add("RetornarRelatorioDespesas");
        lista.add(seqHospedagem);
        lista.add(nroQuarto);
        
        try {
            return (Map<String, Object>)operacaoRegistro(lista);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public Object operacaoRegistro (ArrayList lista) throws Exception {
        try {
            FutureTask retornoCallableClient = new FutureTask(new CallableClient(lista));
            Thread t = new Thread(retornoCallableClient);
            t.start();
            
            ArrayList listaRecebida = ((ArrayList)retornoCallableClient.get());
            
            String tipoObjeto = (String)listaRecebida.get(0);
            switch (tipoObjeto) {
                case "Boolean":
                    return (boolean)listaRecebida.get(1);
                case "List<Despesa>":
                    return (List<Despesa>)listaRecebida.get(1);
                case "Map<String, Object>":
                    return (Map<String, Object>)listaRecebida.get(1);
                case "Exception":
                    throw (Exception)listaRecebida.get(1);
            }
        }   catch (Exception ex) {
            throw ex;
        }
        return null;
    }
}
