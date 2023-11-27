package com.virtus.security.config;

import com.virtus.domain.entity.User;
import com.virtus.persistence.RoleRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.security.jwt.JwtAuthenticationEntryPoint;
import com.virtus.security.jwt.JwtAuthenticationTokenFilter;
import com.virtus.security.service.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthorizationService authorizationService;

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfiguration(AuthorizationService authorizationService, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.authorizationService = authorizationService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authorizationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(authenticationTokenFilterBean(authorizationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean(AuthorizationService authorizationService) throws Exception {
        return new JwtAuthenticationTokenFilter(authorizationService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
