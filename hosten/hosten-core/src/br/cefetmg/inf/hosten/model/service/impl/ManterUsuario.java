package br.cefetmg.inf.hosten.model.service.impl;

import br.cefetmg.inf.hosten.model.dao.IUsuarioDAO;
import br.cefetmg.inf.hosten.model.dao.impl.UsuarioDAO;
import br.cefetmg.inf.hosten.model.dao.rel.impl.QuartoConsumoDAO;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.domain.rel.QuartoConsumo;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.IQuartoConsumoDAO;
import br.cefetmg.inf.hosten.model.service.IManterUsuario;

public class ManterUsuario implements IManterUsuario {

    IUsuarioDAO objetoDAO;

    public ManterUsuario() {
        objetoDAO = UsuarioDAO.getInstance();
    }

    @Override
    public boolean inserir(Usuario usuario)
            throws NegocioException, SQLException {
        // testa tamanho dos campos
        if (usuario.getCodUsuario().length() != 4) {
            throw new NegocioException("O código do usuário deve ter 4 caracteres.");
        }
        if (usuario.getNomUsuario().length() > 90) {
            throw new NegocioException("O nome do usuário ultrapassou os 90 caracteres máximos permitidos.");
        }
        if (usuario.getDesEmail().length() > 60) {
            throw new NegocioException("O email do usuário ultrapassou os 60 caracteres máximos permitidos.");
        }

        // pesquisa para saber se há algum usuário já 
        // inserido que possui o mesmo código
        List<Usuario> usuariosPesquisados
                = listar(usuario.getCodUsuario(), "codUsuario");

        if (usuariosPesquisados.isEmpty()) {
            // não tem item com o mesmo código

            // busca se tem usuario com o mesmo email
            List<Usuario> usuariosPesquisados1
                    = listar(usuario.getDesEmail(), "desEmail");
            if (usuariosPesquisados1.isEmpty()) {
                // não tem usuário com o mesmo email
                // pode inserir
                boolean testeRegistro;
                try {
                    testeRegistro = objetoDAO.adicionaUsuario(usuario);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    throw new NegocioException("Não foi possível inserir o usuário!");
                }
                return testeRegistro;
            } else {
                // tem usuário com o mesmo email
                throw new NegocioException("Email repetido!");
            }
        } else {
            // tem usuário com o mesmo código
            throw new NegocioException("Código do usuário repetido!");
        }
    }

    @Override
    public boolean alterar(String codRegistro, Usuario usuario)
            throws NegocioException, SQLException {
        // testa tamanho dos campos
        if (usuario.getCodUsuario().length() != 4) {
            throw new NegocioException("O código do usuário deve ter 4 caracteres.");
        }
        if (usuario.getNomUsuario().length() > 90) {
            throw new NegocioException("O nome do usuário ultrapassou os 90 caracteres máximos permitidos.");
        }
        if (usuario.getDesEmail().length() > 60) {
            throw new NegocioException("O email do usuário ultrapassou os 60 caracteres máximos permitidos.");
        }

        List<Usuario> buscaRegistroAntigo = listar(codRegistro, "codUsuario");
        Usuario registroAntigo = buscaRegistroAntigo.get(0);
        
        // pesquisa para saber se há algum usuário já 
        // inserido que possui o mesmo código
        List<Usuario> usuariosPesquisados
                = listar(usuario.getCodUsuario(), "codUsuario");

        if (usuariosPesquisados.isEmpty() || (codRegistro.equals(usuario.getCodUsuario()))) {
            // não tem item com o mesmo código

            // busca se tem usuario com o mesmo email
            List<Usuario> usuariosPesquisados1
                    = listar(usuario.getDesEmail(), "desEmail");
            if (usuariosPesquisados1.isEmpty() ||
                    (registroAntigo.getDesEmail().equals(usuario.getDesEmail())) ) {
                // não tem usuário com o mesmo email
                // pode alterar
                boolean testeRegistro;
                try {
                    testeRegistro = objetoDAO.atualizaUsuario(codRegistro, usuario);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    throw new NegocioException("Não foi possível alterar os dados o usuário!");
                }
                return testeRegistro;
            } else {
                // tem usuário com o mesmo email
                throw new NegocioException("Email repetido!");
            }
        } else {
            // tem usuário com o mesmo código
            throw new NegocioException("Código do usuário repetido!");
        }
    }

    @Override
    public boolean excluir(String codRegistro)
            throws NegocioException, SQLException {
        // testa se o codUsuario é usado em QuartoConsumo
        IQuartoConsumoDAO dao = QuartoConsumoDAO.getInstance();
        List<QuartoConsumo> listaQuartoConsumo = dao.busca(codRegistro, "codUsuario");
        if (listaQuartoConsumo.isEmpty()) {
            // não é usado em quarto consumo
            // pode excluir
            return objetoDAO.deletaUsuario(codRegistro);
        } else {
            throw new NegocioException(
                    "Não é possível excluir o usuário. "
                    + "Ele já lançou serviços no sistema - "
                    + "não é possóvel excluir tais registros.");
        }
    }

    @Override
    public List<Usuario> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException {
        //
        // confere se foi digitado um dado busca e se a coluna é válida
        //
        if (dadoBusca != null) {
            if (coluna.equals("codUsuario")
                    || coluna.equals("nomUsuario")
                    || coluna.equals("desEmail")
                    || coluna.equals("codCargo")) {
                try {
                    return objetoDAO.buscaUsuario(dadoBusca, coluna);
                } catch (NoSuchAlgorithmException
                        | UnsupportedEncodingException ex) {
                    throw new NegocioException("Busca mal sucedida");
                }
            } else {
                throw new NegocioException(
                        "Não existe essa informação sobre o usuário! "
                        + "Busque pelo código, pelo nome, "
                        + "pelo email ou pelo cargo.");
            }
        } else {
            throw new NegocioException("Nenhum usuário buscado!");
        }
    }

    @Override
    public List<Usuario> listarTodos()
            throws NegocioException, SQLException {
        try {
            return objetoDAO.buscaTodosUsuarios();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new NegocioException("Não foi possível excluir o usuário.");
        }
    }

    @Override
    public Usuario usuarioLogin(String email, String senha) 
            throws NegocioException, SQLException {
        if(email != null && senha != null) {
            try {
                return objetoDAO.usuarioLogin(email, senha);
            } catch (NoSuchAlgorithmException 
                    | UnsupportedEncodingException ex) {
                throw new NegocioException("Ocorreu um erro inesperado "
                        + "ao conferir a senha");
            }
        }
        // A senha ou email foram nulos
        return null;
    }
}
