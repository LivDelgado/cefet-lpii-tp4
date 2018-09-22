package br.cefetmg.inf.hosten.model.domain.rel;

import java.io.Serializable;

public class CargoPrograma implements Serializable {
    private String codPrograma;
    private String codCargo;

    public CargoPrograma(String codPrograma, String codCargo) {
        this.codPrograma = codPrograma;
        this.codCargo = codCargo;
    }

    public String getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(String codPrograma) {
        this.codPrograma = codPrograma;
    }

    public String getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(String codCargo) {
        this.codCargo = codCargo;
    }
}
