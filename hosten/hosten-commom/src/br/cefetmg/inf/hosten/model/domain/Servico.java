package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;

public class Servico implements Serializable {
    private int seqServico; 
    private String desServico; 
    private Double vlrUnit; 
    private String codServicoArea; 

    public Servico(int seqServico, String desServico, Double vlrUnit, String codServicoArea) {
        this.seqServico = seqServico;
        this.desServico = desServico;
        this.vlrUnit = vlrUnit;
        this.codServicoArea = codServicoArea;
    }

    public Servico(String desServico, Double vlrUnit, String codServicoArea) {
        this.desServico = desServico;
        this.vlrUnit = vlrUnit;
        this.codServicoArea = codServicoArea;
    }

    public int getSeqServico() {
        return seqServico;
    }

    public void setSeqServico(int seqServico) {
        this.seqServico = seqServico;
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

    public String getCodServicoArea() {
        return codServicoArea;
    }

    public void setCodServicoArea(String codServicoArea) {
        this.codServicoArea = codServicoArea;
    }
    
    
}
