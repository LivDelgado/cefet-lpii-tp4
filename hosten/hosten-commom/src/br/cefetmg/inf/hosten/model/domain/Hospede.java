package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;

public class Hospede implements Serializable {

    private String codCPF;
    private String nomHospede;
    private String desTelefone;
    private String desEmail;

    public Hospede(String codCPF, String nomHospede, String desTelefone, String desEmail) {
        this.codCPF = codCPF;
        this.nomHospede = nomHospede;
        this.desTelefone = desTelefone;
        this.desEmail = desEmail;
    }

    public String getCodCPF() {
        return codCPF;
    }

    public void setCodCPF(String codCPF) {
        this.codCPF = codCPF;
    }

    public String getNomHospede() {
        return nomHospede;
    }

    public void setNomHospede(String nomHospede) {
        this.nomHospede = nomHospede;
    }

    public String getDesTelefone() {
        return desTelefone;
    }

    public void setDesTelefone(String desTelefone) {
        this.desTelefone = desTelefone;
    }

    public String getDesEmail() {
        return desEmail;
    }

    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

}
