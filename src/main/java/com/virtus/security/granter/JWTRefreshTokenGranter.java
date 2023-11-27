package com.virtus.security.granter;

import com.virtus.security.config.JwtRefreshTokenConverter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import java.util.Map;

public class JWTRefreshTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "refresh_token";

    private final DefaultTokenServices jwtTokenService;
    private final JwtRefreshTokenConverter jwtRefreshTokenConverter;

    public JWTRefreshTokenGranter(AuthorizationServerEndpointsConfigurer endpointsConfigurer,
                                  DefaultTokenServices jwtTokenService,
                                  JwtRefreshTokenConverter jwtRefreshTokenConverter) {
        super(jwtTokenService, endpointsConfigurer.getClientDetailsService(), endpointsConfigurer.getOAuth2RequestFactory(), GRANT_TYPE);
        this.jwtTokenService = jwtTokenService;
        this.jwtRefreshTokenConverter = jwtRefreshTokenConverter;
    }

    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        if (!grantType.equals(GRANT_TYPE)) {
            return null;
        }
        String refrToken = tokenRequest.getRequestParameters().get("refresh_token");
        Map<String, Object> map = this.jwtRefreshTokenConverter.decode(refrToken);
        OAuth2Authentication auth = this.jwtRefreshTokenConverter.extractAuthentication(map);
        return this.jwtTokenService.createAccessToken(auth);
    }

}
