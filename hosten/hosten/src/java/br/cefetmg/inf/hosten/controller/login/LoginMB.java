package br.cefetmg.inf.hosten.controller.login;

import br.cefetmg.inf.hosten.controller.context.ContextUtils;
import br.cefetmg.inf.hosten.controller.sessao.Sessao;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;
import br.cefetmg.inf.hosten.proxy.ManterUsuarioProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;

@SessionScoped
@Named("loginMB")
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
        
        IManterUsuario manterUsuario = new ManterUsuarioProxy();
        try {
            Usuario usuarioAtual = manterUsuario.usuarioLogin(email, senha);
            if (usuarioAtual != null) {
                Sessao.getInstance().setUsuarioLogado(usuarioAtual);
                
                ContextUtils.mostrarMensagem("LogIn efetudo", "Logado com sucesso.", true);
                retorno = true;
            } else {
                ContextUtils.mostrarMensagem("Falha no LogIn", "Tentativa de login inválida. Tente novamente.", true);
            }
        } catch (NegocioException | SQLException ex) {
            ContextUtils.mostrarMensagem("Falha no LogIn", "Tentativa de login inválida. Tente novamente.", true);

            ex.printStackTrace();
            return retorno;
        }
        return retorno;
    }
    
    public String efetuaLogout() {
        try {
            Sessao.getInstance().encerrarSessao();
            ContextUtils.redireciona("index.jsf");
            ContextUtils.mostrarMensagem("LogOut", "LogOut efetuado com sucesso", true);
            
            return "sucesso";
        } catch(IOException e) {
            ContextUtils.mostrarMensagem("LogOut", "Não foi possível efetuar o LogOut", false);
            return "falha";
        }
    }
}