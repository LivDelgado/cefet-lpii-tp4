package br.cefetmg.inf.hosten.controller.context;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ContextUtils {

    public static void mostrarMensagem(String titulo, String descricao, boolean manterNaTela) {
        // Cria uma nova Faces Message a partir da mensagem passada de parâmetro
        FacesMessage fm = new FacesMessage(titulo, descricao);

        FacesContext context = FacesContext.getCurrentInstance();
        if (manterNaTela) {
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        context.addMessage(null, fm);
    }

    /**
     * Método usado para redirecionar para uma página facilmente
     *
     * @param paginaDestino String que carrega a página destino (termina com o
     * padrão de URL do projeto), caso seja vazio ou nulo, recarrega a página
     *
     * @throws IOException
     */
    public static void redireciona(String paginaDestino) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (paginaDestino == null || paginaDestino.equals("")) {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } else {
            ec.redirect(paginaDestino);
        }
    }
}
