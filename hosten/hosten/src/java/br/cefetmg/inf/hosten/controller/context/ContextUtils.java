package br.cefetmg.inf.hosten.controller.context;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ContextUtils {
    /**
     * Método simples apenas para redirecionar o usuário para a própria página
     * @throws IOException 
     */
    public static void recarregaPagina() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public static void mostraMensagem(String mensagem, boolean manterNaTela) {
        // Cria uma nova Faces Message a partir da mensagem passada de parâmetro
        FacesMessage fm = new FacesMessage(mensagem);
                
        FacesContext context = FacesContext.getCurrentInstance();
        if(manterNaTela){
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        context.addMessage(null, fm);
    }
}
