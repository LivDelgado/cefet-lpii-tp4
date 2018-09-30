package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Programa implements Serializable {

    private String codPrograma;
    private String desPrograma;

    public Programa(String codPrograma, String desPrograma) {
        this.codPrograma = codPrograma;
        this.desPrograma = desPrograma;
    }

    public String getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(String codPrograma) {
        this.codPrograma = codPrograma;
    }

    public String getDesPrograma() {
        return desPrograma;
    }

    public void setDesPrograma(String desPrograma) {
        this.desPrograma = desPrograma;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Programa other = (Programa) obj;
        if (!Objects.equals(this.codPrograma, other.codPrograma)) {
            return false;
        }
        return true;
    }
    
    
}
