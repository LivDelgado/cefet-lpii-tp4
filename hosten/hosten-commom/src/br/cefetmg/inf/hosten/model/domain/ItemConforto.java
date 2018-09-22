package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;

public class ItemConforto implements Serializable {
    private String codItem; 
    private String desItem; 

    public ItemConforto(String codItem, String desItem) {
        this.codItem = codItem;
        this.desItem = desItem;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }

    public String getDesItem() {
        return desItem;
    }

    public void setDesItem(String desItem) {
        this.desItem = desItem;
    }
}
