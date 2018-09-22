package br.cefetmg.inf.hosten.model.domain;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String codUsuario;
    private String nomUsuario;
    private String codCargo;
    private String desSenha;
    private String desEmail;

    public Usuario() {
    }

    public Usuario(String codUsuario, String nomUsuario, String codCargo,
            String desSenha, String desEmail) {
        this.codUsuario = codUsuario;
        this.nomUsuario = nomUsuario;
        this.codCargo = codCargo;
        this.desSenha = desSenha;
        this.desEmail = desEmail;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(String codCargo) {
        this.codCargo = codCargo;
    }

    public String getDesSenha() {
        return desSenha;
    }

    public void setDesSenha(String desSenha) {
        this.desSenha = desSenha;
    }

    public String getDesEmail() {
        return desEmail;
    }

    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }
}
