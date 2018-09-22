package br.cefetmg.inf.hosten.model.domain.rel;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuartoConsumo implements Serializable {
    private int seqHospedagem;
    private int nroQuarto;
    private Timestamp datConsumo;
    private int qtdConsumo;
    private int seqServico;
    private String codUsuarioRegistro;

    public QuartoConsumo(int seqHospedagem, int nroQuarto, Timestamp datConsumo, 
            int qtdConsumo, int seqServico, String codUsuarioRegistro) {
        this.seqHospedagem = seqHospedagem;
        this.nroQuarto = nroQuarto;
        this.datConsumo = datConsumo;
        this.qtdConsumo = qtdConsumo;
        this.seqServico = seqServico;
        this.codUsuarioRegistro = codUsuarioRegistro;
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

    public Timestamp getDatConsumo() {
        return datConsumo;
    }

    public void setDatConsumo(Timestamp datConsumo) {
        this.datConsumo = datConsumo;
    }

    public int getQtdConsumo() {
        return qtdConsumo;
    }

    public void setQtdConsumo(int qtdConsumo) {
        this.qtdConsumo = qtdConsumo;
    }

    public int getSeqServico() {
        return seqServico;
    }

    public void setSeqServico(int seqServico) {
        this.seqServico = seqServico;
    }

    public String getCodUsuarioRegistro() {
        return codUsuarioRegistro;
    }

    public void setCodUsuarioRegistro(String codUsuarioRegistro) {
        this.codUsuarioRegistro = codUsuarioRegistro;
    }
}
