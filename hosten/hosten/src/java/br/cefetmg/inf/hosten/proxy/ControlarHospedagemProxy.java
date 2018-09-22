package br.cefetmg.inf.hosten.proxy;

import br.cefetmg.inf.hosten.model.domain.rel.QuartoEstado;
import br.cefetmg.inf.hosten.model.service.IControlarHospedagem;
import br.cefetmg.inf.hosten.proxy.util.CallableClient;
import br.cefetmg.inf.util.exception.NegocioException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class ControlarHospedagemProxy implements IControlarHospedagem {    
    ArrayList lista;

    public ControlarHospedagemProxy() {
        lista = new ArrayList();
        lista.add("Hospedagem");
    }

    @Override
    public boolean efetuarCheckIn(
            String nroQuarto, String codCPF, 
            int diasEstadia, int nroAdultos, int nroCriancas) {
        lista.add("CheckIn");
        lista.add(nroQuarto);
        lista.add(codCPF);
        lista.add(diasEstadia);
        lista.add(nroAdultos);
        lista.add(nroCriancas);
        
        return (boolean) operacaoRegistro(lista);
    }

    @Override
    public int efetuarCheckOut(String nroQuarto) {
        lista.add("CheckOut");
        lista.add(nroQuarto);
        
        return (int) operacaoRegistro(lista);
    }
    
    @Override
    public List<QuartoEstado> listarTodos() throws NegocioException {
        lista.add("ListarTodos");
        
        return (List<QuartoEstado>) operacaoRegistro(lista);
    }
    
    public Object operacaoRegistro (ArrayList lista) {
        try {
            FutureTask retornoCallableClient = new FutureTask(new CallableClient(lista));
            Thread t = new Thread(retornoCallableClient);
            t.start();
            
            ArrayList listaRecebida = ((ArrayList)retornoCallableClient.get());
            
            String tipoObjeto = (String)listaRecebida.get(0);
            switch (tipoObjeto) {
                case "Boolean":
                    return (boolean)listaRecebida.get(1);
                case "Int":
                    return (int)listaRecebida.get(1);
                case "List<QuartoEstado>":
                    return (List<QuartoEstado>)listaRecebida.get(1);
                case "Exception":
                    throw (Exception)listaRecebida.get(1);
            }
        }   catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
