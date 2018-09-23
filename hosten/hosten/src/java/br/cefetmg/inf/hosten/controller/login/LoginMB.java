package br.cefetmg.inf.hosten.controller.login;

import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;
import br.cefetmg.inf.hosten.proxy.ManterUsuarioProxy;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {
    
    private String email;
    private String senha;

    private Usuario usuarioLogin;

    public LoginMB() {
        usuarioLogin = new Usuario("000", "0000", "email@email.com", "senha", "Usuário");
    }
    
    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

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
    
    public String validaLogin () {
        IManterUsuario manterUsuario = new ManterUsuarioProxy();
        try {
            usuarioLogin = manterUsuario.usuarioLogin(email, senha);
            if (usuarioLogin != null) {
                return "sucesso";
            } else {
                return "falha";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "falha";
        }
    }
}