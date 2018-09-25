package br.cefetmg.inf.hosten.controller.login;

import br.cefetmg.inf.hosten.controller.sessao.ConstantesSessao;
import br.cefetmg.inf.hosten.controller.sessao.Sessao;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;
import br.cefetmg.inf.hosten.proxy.ManterUsuarioProxy;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {
    
    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean validaLogin () {
        boolean retorno = false;
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        
        IManterUsuario manterUsuario = new ManterUsuarioProxy();
        try {
            Usuario usuarioAtual = manterUsuario.usuarioLogin(email, senha);
            if (usuarioAtual != null) {
                Sessao.getInstance().setAtributo(ConstantesSessao.USUARIO_LOGADO, usuarioAtual);
                
                retorno = true;
                
            } else {
                context.addMessage(null, new FacesMessage("Tentativa de login inválida. Tente novamente."));
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("Tentativa de login inválida. Tente novamente."));

            ex.printStackTrace();
            return retorno;
        }
        return retorno;
    }
    
    public String efetuaLogout() {
        try {
            Sessao.getInstance().encerrarSessao();
            return "sucesso";
        } catch(Exception e) {
            // TODO
            return "falha";
        }
    }
}