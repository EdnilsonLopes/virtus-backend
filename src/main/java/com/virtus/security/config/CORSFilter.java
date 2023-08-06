package com.virtus.security.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*"); // Configura o cabeçalho para permitir acesso de qualquer origem
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT"); // Configura os métodos permitidos
        response.setHeader("Access-Control-Max-Age", "3600"); // Configura o tempo de cache para as opções pré-voo
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token"); // Configura os cabeçalhos permitidos
        response.setHeader("Access-Control-Allow-Credentials", "true"); // Permite enviar e receber cookies
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

