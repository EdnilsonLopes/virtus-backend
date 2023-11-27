package com.virtus.security.config;

import com.virtus.common.handler.GlobalExceptionHandler;
import com.virtus.exception.VirtusException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreAuthFilter implements Filter {

    private final GlobalExceptionHandler globalExceptionHandler;

    public PreAuthFilter(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException, VirtusException {

        HttpServletResponse response = (HttpServletResponse) res;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            String path = ((HttpServletRequest) req).getRequestURI();

                chain.doFilter(req, res);

        } else {
            chain.doFilter(req, res);
        }

    }

    @Override
    public void destroy() {
    }

}
