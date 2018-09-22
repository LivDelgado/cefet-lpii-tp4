package br.cefetmg.inf.hosten.model.domain.rel;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuartoEstado implements Serializable {
    private int seqHospedagem;
    private int nroQuarto;
    private int nroAdultos;
    private int nroCriancas;
    private Double vlrDiaria;
    private boolean idtOcupado;
    private Timestamp datCheckOut;

    public QuartoEstado() {
    }

    public QuartoEstado(int seqHospedagem, int nroQuarto, int nroAdultos, int nroCriancas, Double vlrDiaria, boolean idtOcupado, Timestamp datCheckOut) {
        this.seqHospedagem = seqHospedagem;
        this.nroQuarto = nroQuarto;
        this.nroAdultos = nroAdultos;
        this.nroCriancas = nroCriancas;
        this.vlrDiaria = vlrDiaria;
        this.idtOcupado = idtOcupado;
        this.datCheckOut = datCheckOut;
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

    public boolean isIdtOcupado() {
        return idtOcupado;
    }

    public void setIdtOcupado(boolean idtOcupado) {
        this.idtOcupado = idtOcupado;
    }

    public Timestamp getDatCheckOut() {
        return datCheckOut;
    }

    public void setDatCheckOut(Timestamp datCheckOut) {
        this.datCheckOut = datCheckOut;
    }
}
