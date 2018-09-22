package br.cefetmg.inf.hosten.model.service.impl;

import br.cefetmg.inf.hosten.model.dao.ICategoriaQuartoDAO;
import br.cefetmg.inf.hosten.model.dao.IHospedagemDAO;
import br.cefetmg.inf.hosten.model.dao.IQuartoDAO;
import br.cefetmg.inf.hosten.model.dao.impl.CategoriaQuartoDAO;
import br.cefetmg.inf.hosten.model.dao.impl.HospedagemDAO;
import br.cefetmg.inf.hosten.model.dao.impl.QuartoDAO;
import br.cefetmg.inf.hosten.model.dao.rel.IQuartoHospedagemDAO;
import br.cefetmg.inf.hosten.model.dao.rel.impl.QuartoHospedagemDAO;
import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.domain.Hospedagem;
import br.cefetmg.inf.hosten.model.domain.Quarto;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoEstado;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoHospedagem;
import br.cefetmg.inf.hosten.model.service.IControlarHospedagem;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControlarHospedagem implements IControlarHospedagem {

    @Override
    public boolean efetuarCheckIn(String nroQuarto, String codCPF, int diasEstadia, int nroAdultos, int nroCriancas) {
        // data check-in
        Date dataAtual = new Date();
        Timestamp dataCheckIn = new Timestamp(dataAtual.getTime());
        // data check-out
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.format(dataAtual);
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dataAtual);
        calendario.add(Calendar.DATE, +diasEstadia);
        Timestamp dataCheckOut = new Timestamp(calendario.getTimeInMillis());

        try {
            // vlrDiaria
            IQuartoDAO quartoDAO = QuartoDAO.getInstance();
            List<Quarto> listaQuarto;
            
            listaQuarto = quartoDAO.buscaQuarto(Integer.parseInt(nroQuarto), "nroQuarto");
            System.out.println("buscou o quarto");
            String codCategoria = listaQuarto.get(0).getCodCategoria();
            
            ICategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
            List<CategoriaQuarto> categorias = categoriaDAO.buscaCategoriaQuarto(codCategoria, "codCategoria");
            Double valorDiaria = categorias.get(0).getVlrDiaria();

            double valorTotal = valorDiaria * diasEstadia;
            
            // ----------------------------------------------------------------------------------------------------------------------------------------
            // realiza a operação de check-in
            Hospedagem hosp = new Hospedagem(dataCheckIn, dataCheckOut, valorTotal, codCPF);
            IHospedagemDAO hospDAO = HospedagemDAO.getInstance();

            boolean testeAddHospedagem = hospDAO.adicionaHospedagem(hosp);

            List<Hospedagem> hospEncontrada = hospDAO.busca(hosp);

            IQuartoHospedagemDAO quartoHosp = QuartoHospedagemDAO.getInstance();
            int seqHospedagem = hospEncontrada.get(0).getSeqHospedagem();
            QuartoHospedagem objAdicionar = new QuartoHospedagem(seqHospedagem, Integer.parseInt(nroQuarto), nroAdultos, nroCriancas, valorDiaria);
            boolean testeAddQuarto = quartoHosp.adiciona(objAdicionar);
            
            // atualiza o idtOcupado do quarto pra ocupado
            Quarto quartoAtualizado = listaQuarto.get(0);
            quartoAtualizado.setIdtOcupado(true);
            boolean testeAtualizaQuarto = quartoDAO.atualizaQuarto(Integer.parseInt(nroQuarto), quartoAtualizado);
            
            return (testeAddQuarto && testeAtualizaQuarto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public int efetuarCheckOut(String nroQuarto) {
        int intNroQuarto = Integer.parseInt(nroQuarto);

        IQuartoDAO quartoDAO = QuartoDAO.getInstance();
        int seqHospedagem = 0;
        try {
            seqHospedagem = quartoDAO.buscaUltimoRegistroRelacionadoAoQuarto(intNroQuarto);
            
            Date dataAtual = new Date();
            Timestamp dataCheckOut = new Timestamp(dataAtual.getTime());
            
            IHospedagemDAO hospDAO = HospedagemDAO.getInstance();
            List<Hospedagem> hospBuscada
                    = hospDAO.buscaHospedagem(seqHospedagem, "seqHospedagem");
            
            Hospedagem hospedagemAtualizado = hospBuscada.get(0);
            hospedagemAtualizado.setDatCheckOut(dataCheckOut);
            

            // atualiza a data de check-out da hospedagem
            hospDAO.atualizaHospedagemPorPk(seqHospedagem, hospedagemAtualizado);

            List<Quarto> listaQuarto;
            listaQuarto = quartoDAO.buscaQuarto(Integer.parseInt(nroQuarto), "nroQuarto");
            Quarto quartoAtualizado = listaQuarto.get(0);
            quartoAtualizado.setIdtOcupado(false);
            quartoDAO.atualizaQuarto(Integer.parseInt(nroQuarto), quartoAtualizado);
            
            // retorna o seqHospedagem 
            return seqHospedagem;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<QuartoEstado> listarTodos() throws NegocioException{
        IQuartoHospedagemDAO dao = QuartoHospedagemDAO.getInstance();
        try {
            List<QuartoEstado> lista = dao.buscaTodos();
            if(lista == null) {
                throw new NegocioException("Não existem registros de QuartoHospedagem");
            }
            return lista;
        } catch (SQLException ex) {
            throw new NegocioException("Houve um erro no processamento da requisição");
        }
    }
}
