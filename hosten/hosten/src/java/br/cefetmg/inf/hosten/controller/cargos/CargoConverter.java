package br.cefetmg.inf.hosten.controller.cargos;

import br.cefetmg.inf.hosten.model.domain.Cargo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

// PADRÃO DE STRING
// 001 - Cargo | Gerente

@FacesConverter("cargoConverter")
public class CargoConverter implements Converter  {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String codCargo = value.substring(0, value.indexOf(" -"));
        String nomCargo = value.substring(value.indexOf("- ")+2, value.indexOf(" |"));
        String bool = value.substring(value.indexOf("| ")+2, value.length());
        boolean idtMaster = Boolean.valueOf(bool);
        
        return new Cargo(codCargo, nomCargo, idtMaster);
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Cargo cargo = ((Cargo) value);
        String gerente;
        if (cargo.isIdtMaster()) {
            gerente = "Gerente";
        } else {
            gerente = "Não gerente";
        }
        
        String cargoAsString = cargo.getCodCargo() + " - " + cargo.getNomCargo() + " | " + gerente;
        
        return cargoAsString;
    }
}
