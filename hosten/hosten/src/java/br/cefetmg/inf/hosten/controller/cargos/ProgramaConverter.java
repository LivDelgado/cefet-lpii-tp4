package br.cefetmg.inf.hosten.controller.cargos;

import br.cefetmg.inf.hosten.model.domain.Programa;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("programaConverter")
public class ProgramaConverter implements Converter  {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String codPrograma = value.substring(0, value.indexOf(" -"));
        String desPrograma = value.substring(value.indexOf("- ")+2, value.length());
        
        return new Programa(codPrograma, desPrograma);
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Programa programa = ((Programa) value);
        String programaAsString = programa.getCodPrograma() + " - " + programa.getDesPrograma();
        
        return programaAsString;
    }
}
