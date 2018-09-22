package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;

public class Quarto implements Serializable {
    private int nroQuarto; 
    private String codCategoria; 
    private boolean idtOcupado; 

    public Quarto(int nroQuarto, String codCategoria, boolean idtOcupado) {
        this.nroQuarto = nroQuarto;
        this.codCategoria = codCategoria;
        this.idtOcupado = idtOcupado;
    }

    public int getNroQuarto() {
        return nroQuarto;
    }

    public void setNroQuarto(int nroQuarto) {
        this.nroQuarto = nroQuarto;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public boolean isIdtOcupado() {
        return idtOcupado;
    }

    public void setIdtOcupado(boolean idtOcupado) {
        this.idtOcupado = idtOcupado;
    }
    
    
}
