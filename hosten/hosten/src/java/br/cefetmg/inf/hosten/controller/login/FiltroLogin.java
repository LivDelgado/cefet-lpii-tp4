package br.cefetmg.inf.hosten.controller.login;

import br.cefetmg.inf.hosten.controller.sessao.ConstantesSessao;
import br.cefetmg.inf.hosten.model.domain.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroLogin implements Filter {
    private final static String URL_LOGIN = "/index.xhtml";

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Usuario usuario = null;
        HttpSession sessaoHttp = ((HttpServletRequest) request).getSession(false);

        if (sessaoHttp != null) {
            usuario = (Usuario) sessaoHttp.getAttribute(ConstantesSessao.USUARIO_LOGADO.name());
        }

        if (usuario == null) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + URL_LOGIN);
        } else {
            chain.doFilter(request, response);
        }
    }
}