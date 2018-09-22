package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Hospedagem implements Serializable {
    private int seqHospedagem; 
    private Timestamp datCheckIn; 
    private Timestamp datCheckOut; 
    private Double vlrPago; 
    private String codCPF; 

    public Hospedagem(int seqHospedagem, Timestamp datCheckIn, Timestamp datCheckOut, Double vlrPago, String codCPF) {
        this.seqHospedagem = seqHospedagem;
        this.datCheckIn = datCheckIn;
        this.datCheckOut = datCheckOut;
        this.vlrPago = vlrPago;
        this.codCPF = codCPF;
    }
    
    public Hospedagem(Timestamp datCheckIn, Timestamp datCheckOut, Double vlrPago, String codCPF) {
        this.datCheckIn = datCheckIn;
        this.datCheckOut = datCheckOut;
        this.vlrPago = vlrPago;
        this.codCPF = codCPF;
    }

    public int getSeqHospedagem() {
        return seqHospedagem;
    }

    public void setSeqHospedagem(int seqHospedagem) {
        this.seqHospedagem = seqHospedagem;
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

    public String getCodCPF() {
        return codCPF;
    }

    public void setCodCPF(String codCPF) {
        this.codCPF = codCPF;
    }
    
    
}
