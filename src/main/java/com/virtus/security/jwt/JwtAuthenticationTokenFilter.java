package com.virtus.security.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.virtus.domain.model.CurrentUser;
import com.virtus.security.service.AuthorizationService;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final AuthorizationService authService;

    public JwtAuthenticationTokenFilter(AuthorizationService authService) {
        this.authService = authService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {

            String authToken = request.getHeader("Authorization");
            if (StringUtils.hasText(authToken) && !authToken.contains("Basic ")) {

                CurrentUser currentUser = JwtUtils.extractUser(authToken.replace("Bearer ", ""));

                //if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (currentUser != null) { //TODO Verificar validade do token
                    //if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            currentUser, null, Arrays.asList(new SimpleGrantedAuthority(currentUser.getRole())));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + currentUser + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    //}
                }
            }
        } catch (Exception e) {
        }
        chain.doFilter(request, response);
    }
}
