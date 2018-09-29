package br.cefetmg.inf.hosten.controller.sessao;

import br.cefetmg.inf.hosten.model.domain.Usuario;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
  
public class Sessao {
    private static Sessao instancia;
     
    public static Sessao getInstance(){
         if (instancia == null){
             instancia = new Sessao();
         }
         return instancia;
    }
     
    private ExternalContext contextoAtual(){
         if (FacesContext.getCurrentInstance() == null){
             throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisição HTTP");
         }else{
             return FacesContext.getCurrentInstance().getExternalContext();
         }
    }
     
    public Usuario getUsuarioLogado(){
         return (Usuario) getAtributo(ConstantesSessao.USUARIO_LOGADO);
    }
     
    public void setUsuarioLogado(Usuario usuario){
         setAtributo(ConstantesSessao.USUARIO_LOGADO, usuario);
    }
     
    private Object getAtributo(ConstantesSessao cs){
         return contextoAtual().getSessionMap().get(cs.name());
    }
     
    private void setAtributo(ConstantesSessao cs, Object valor){
         contextoAtual().getSessionMap().put(cs.name(), valor);
    }
    
    public void encerrarSessao(){   
         contextoAtual().invalidateSession();
    }
}