package br.cefetmg.inf.hosten.controller.login;

import br.cefetmg.inf.hosten.model.domain.Usuario;
//import br.cefetmg.inf.hosten.model.service.IManterUsuario;
//import br.cefetmg.inf.hosten.proxy.ManterUsuarioProxy;
import javax.inject.Named;
//import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "loginMB")
//@SessionScoped
public class LoginMB implements Serializable {
    
    private Usuario usuarioLogin;
    private String email;
    private String senha;

    public LoginMB() {
        usuarioLogin = new Usuario();
        usuarioLogin.setCodCargo("000");
        usuarioLogin.setCodUsuario("0000");
        usuarioLogin.setDesEmail("email@email.com");
        usuarioLogin.setDesSenha("senha");
        usuarioLogin.setNomUsuario("Usuário");
    }
    
    public Usuario getUserLogin() {
        return usuarioLogin;
    }

    public void setUserLogin(Usuario usuarioLogin) {
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
    
    public void validaLogin () {
    }

}
