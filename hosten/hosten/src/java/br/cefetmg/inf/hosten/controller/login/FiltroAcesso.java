package br.cefetmg.inf.hosten.controller.login;

import br.cefetmg.inf.hosten.controller.sessao.ConstantesSessao;
import br.cefetmg.inf.hosten.model.domain.Cargo;
import br.cefetmg.inf.hosten.model.domain.Programa;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import br.cefetmg.inf.hosten.model.service.IManterCargo;
import br.cefetmg.inf.hosten.proxy.ManterCargoProxy;
import br.cefetmg.inf.util.exception.NegocioException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "FiltroLogin")
public class FiltroAcesso implements Filter {

    private final static String URL_PATTERN = ".jsf";
    private final static String URL_LOGIN = "/index.jsf";

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("REQUEST =>" + ((HttpServletRequest) request).getRequestURI());
        System.out.println("REQUEST CONTEXT PATH =>" + ((HttpServletRequest) request).getContextPath());

        Usuario usuario = null;
        HttpSession sessaoHttp = ((HttpServletRequest) request).getSession(false);

        if (sessaoHttp != null) {
            usuario = (Usuario) sessaoHttp.getAttribute(ConstantesSessao.USUARIO_LOGADO.name());
        }

        // Caminho de contexto do Hosten (/hosten)
        String contextPath = ((HttpServletRequest) request).getContextPath();

        if (usuario == null) {
            // Caso o atributo usuário seja null, manda o usuário do Hosten para a página de login
            ((HttpServletResponse) response).sendRedirect(contextPath + URL_LOGIN);
        } else {
            IManterCargo mCargo = new ManterCargoProxy();
            try {
                // Obtém o cargo do Usuário
                Cargo cargoUsuario = mCargo.listar(usuario.getCodCargo(), "codCargo").get(0);

                // Caso o cargo do usuário seja de Gerente, (idtMaster == true)
                // libera o acesso dele à qualquer página do sistema
                if (cargoUsuario.isIdtMaster()) {
                    chain.doFilter(request, response);
                } // Caso contrário, deve haver relação entre o cargo e a página
                else {
                    // Obtém o nome da página requisitada (ex.: 'itens-conforto')
                    String pgRqstdCompleta = ((HttpServletRequest) request).getRequestURI();
                    String nomPgRqstd = getNomPgRqstd(pgRqstdCompleta);

                    // Obtém os programas relacionados ao cargo
                    List<Programa> listaProg = mCargo.listarProgramasRelacionados(usuario.getCodCargo());

                    for (Programa prog : listaProg) {
                        // Verifica se o usuário tem acesso à página requisitada
                        if (prog.getDesPrograma().equals(nomPgRqstd)) {
                            chain.doFilter(request, response);
                        }
                    }
                    // Caso o mesmo não o possua, volta para o "menu"
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/template.jsf");
                }
            } catch (NegocioException | SQLException e) {
                // Caso haja qualquer exceção, manda o usuário do Hosten para 
                // a página de login
                ((HttpServletResponse) response).sendRedirect(contextPath + "/template.jsf");
            }
        }
    }

    private String getNomPgRqstd(String pgRqstdCompleta) {
        int indexBarra = pgRqstdCompleta.lastIndexOf('/');
        int indexUrlPattern = pgRqstdCompleta.lastIndexOf(URL_PATTERN);

        String nomPgRqstd = pgRqstdCompleta.substring(indexBarra + 1, indexUrlPattern);
        System.out.println("nomPgRqstd =>" + nomPgRqstd);

        return nomPgRqstd;
    }
}
