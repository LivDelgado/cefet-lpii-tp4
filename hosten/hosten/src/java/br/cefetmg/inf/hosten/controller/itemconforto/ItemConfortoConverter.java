package br.cefetmg.inf.hosten.controller.itemconforto;

import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

//
// PADRÃO DE STRING
// 001 - Item de conforto
//
@FacesConverter("itemConfortoConverter")
public class ItemConfortoConverter implements Converter  {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String codItem = value.substring(0, value.indexOf(" -"));
        String desItem = value.substring(value.indexOf("- ")+2, value.length());
        
        return new  ItemConforto(codItem, desItem);
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        ItemConforto item = ((ItemConforto) value);
        String itemAsString = item.getCodItem() + " - " + item.getDesItem();
        
        return itemAsString;
    }
}
