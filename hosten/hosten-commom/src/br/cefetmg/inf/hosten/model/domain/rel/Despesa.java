package br.cefetmg.inf.hosten.model.domain.rel;

import java.io.Serializable;
import java.sql.Timestamp;

public class Despesa implements Serializable {
    private int seqHospedagem;
    private int nroQuarto;
    private int nroAdultos;
    private int nroCriancas;
    private Double vlrDiaria;
    private Timestamp datCheckIn;
    private Timestamp datCheckOut;
    private Double vlrPago;
    private String nomeHospede;
    private int seqServico;
    private int qtdConsumo;
    private String desServico;
    private Double vlrUnit;

    public Despesa(int seqHospedagem, int nroQuarto, int nroAdultos, int nroCriancas, Double vlrDiaria, Timestamp datCheckIn, Timestamp datCheckOut, Double vlrPago, String nomeHospede, int seqServico, int qtdConsumo, String desServico, Double vlrUnit) {
        this.seqHospedagem = seqHospedagem;
        this.nroQuarto = nroQuarto;
        this.nroAdultos = nroAdultos;
        this.nroCriancas = nroCriancas;
        this.vlrDiaria = vlrDiaria;
        this.datCheckIn = datCheckIn;
        this.datCheckOut = datCheckOut;
        this.vlrPago = vlrPago;
        this.nomeHospede = nomeHospede;
        this.seqServico = seqServico;
        this.qtdConsumo = qtdConsumo;
        this.desServico = desServico;
        this.vlrUnit = vlrUnit;
    }

    public int getSeqHospedagem() {
        return seqHospedagem;
    }

    public void setSeqHospedagem(int seqHospedagem) {
        this.seqHospedagem = seqHospedagem;
    }

    public int getNroQuarto() {
        return nroQuarto;
    }

    public void setNroQuarto(int nroQuarto) {
        this.nroQuarto = nroQuarto;
    }

    public int getNroAdultos() {
        return nroAdultos;
    }

    public void setNroAdultos(int nroAdultos) {
        this.nroAdultos = nroAdultos;
    }

    public int getNroCriancas() {
        return nroCriancas;
    }

    public void setNroCriancas(int nroCriancas) {
        this.nroCriancas = nroCriancas;
    }

    public Double getVlrDiaria() {
        return vlrDiaria;
    }

    public void setVlrDiaria(Double vlrDiaria) {
        this.vlrDiaria = vlrDiaria;
    }

    public Timestamp getDatCheckIn() {
        return datCheckIn;
    }

    public void setDatCheckIn(Timestamp datCheckIn) {
        this.datCheckIn = datCheckIn;
    }

    public Timestamp getDatCheckOut() {
        return datCheckOut;
    }

    public void setDatCheckOut(Timestamp datCheckOut) {
        this.datCheckOut = datCheckOut;
    }

    public Double getVlrPago() {
        return vlrPago;
    }

    public void setVlrPago(Double vlrPago) {
        this.vlrPago = vlrPago;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public int getSeqServico() {
        return seqServico;
    }

    public void setSeqServico(int seqServico) {
        this.seqServico = seqServico;
    }

    public int getQtdConsumo() {
        return qtdConsumo;
    }

    public void setQtdConsumo(int qtdConsumo) {
        this.qtdConsumo = qtdConsumo;
    }

    public String getDesServico() {
        return desServico;
    }

    public void setDesServico(String desServico) {
        this.desServico = desServico;
    }

    public Double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(Double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }
}
