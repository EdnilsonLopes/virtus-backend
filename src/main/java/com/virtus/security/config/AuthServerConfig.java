package com.virtus.security.config;

import com.virtus.common.properties.SecurityProperties;
import com.virtus.security.granter.JWTRefreshTokenGranter;
import com.virtus.security.granter.PasswordTokenGranter;
import com.virtus.security.handler.OAuthExceptionHandler;
import com.virtus.security.service.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;
    private final SecurityProperties securityProperties;
    private final TokenEnhancerConfig tokenEnhancerConfig;
    private final PasswordEncoder passwordEncoder;
    private final OAuthExceptionHandler oAuthExceptionHandler;


    public AuthServerConfig(AuthenticationManager authenticationManager,
                            AuthorizationService authorizationService, SecurityProperties securityProperties,
                            TokenEnhancerConfig tokenEnhancerConfig,
                            PasswordEncoder passwordEncoder, OAuthExceptionHandler oAuthExceptionHandler) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.securityProperties = securityProperties;
        this.tokenEnhancerConfig = tokenEnhancerConfig;
        this.passwordEncoder = passwordEncoder;
        this.oAuthExceptionHandler = oAuthExceptionHandler;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenGranter(tokenGranter(endpoints))
                .tokenStore(this.tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .authenticationManager(authenticationManager)
                .userDetailsService(authorizationService)
                .exceptionTranslator(oAuthExceptionHandler);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(securityProperties.getClient().getId())
                .authorizedGrantTypes("password", "refresh_token", "mfa", "authorization_enterprise")
                .scopes("read", "write")
                .accessTokenValiditySeconds(securityProperties.getClient().getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(securityProperties.getClient().getRefreshTokenValiditySeconds())
                .resourceIds(securityProperties.getResourceIds().toArray(String[]::new))
                .secret(passwordEncoder.encode(securityProperties.getClient().getSecret()));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    @Bean
    public JwtAccessTokenConverter refreshTokenConverter() {
        JwtRefreshTokenConverter converter = new JwtRefreshTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    private KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                securityProperties.getJwt().getKeyStore(),
                securityProperties.getJwt().getKeyPairPassword().toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair(securityProperties.getJwt().getKeyPairAlias());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(this.tokenStore());
        tokenServices.setTokenEnhancer(tokenEnhancer());
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }

    private TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancerConfig, this.accessTokenConverter(), this.refreshTokenConverter()));
        return tokenEnhancerChain;
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(List.of(endpoints.getTokenGranter()));
        granters.add(new PasswordTokenGranter(endpoints, authenticationManager, tokenServices(), tokenStore(), tokenEnhancer(), securityProperties));
        granters.add(new JWTRefreshTokenGranter(endpoints, tokenServices(), (JwtRefreshTokenConverter) refreshTokenConverter()));
        return new CompositeTokenGranter(granters);
    }
}
