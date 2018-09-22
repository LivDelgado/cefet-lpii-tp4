package br.cefetmg.inf.hosten.model.domain.rel;

import java.io.Serializable;

public class CategoriaItemConforto implements Serializable {
    private String codCategoria;
    private String codItem;

    public CategoriaItemConforto(String codCategoria, String codItem) {
        this.codCategoria = codCategoria;
        this.codItem = codItem;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }
}
