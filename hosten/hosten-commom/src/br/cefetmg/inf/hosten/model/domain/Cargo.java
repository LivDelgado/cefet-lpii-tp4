package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;

public class Cargo implements Serializable {
    private String codCargo; 
    private String nomCargo; 
    private boolean idtMaster; 

    public Cargo(String codCargo, String nomCargo, boolean idtMaster) {
        this.codCargo = codCargo;
        this.nomCargo = nomCargo;
        this.idtMaster = idtMaster;
    }

    public String getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(String codCargo) {
        this.codCargo = codCargo;
    }

    public String getNomCargo() {
        return nomCargo;
    }

    public void setNomCargo(String nomCargo) {
        this.nomCargo = nomCargo;
    }

    public boolean isIdtMaster() {
        return idtMaster;
    }

    public void setIdtMaster(boolean idtMaster) {
        this.idtMaster = idtMaster;
    }
    
    
}
