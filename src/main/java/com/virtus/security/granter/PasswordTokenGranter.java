package com.virtus.security.granter;


import com.virtus.common.properties.SecurityProperties;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.*;

public class PasswordTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "password";
    private static final GrantedAuthority PRE_AUTH = new SimpleGrantedAuthority("PRE_AUTH");
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final DefaultTokenServices jwtService;
    private final TokenStore tokenStore;
	private final SecurityProperties securityProperties;

	private final TokenEnhancer accessTokenEnhancer;

    public PasswordTokenGranter(AuthorizationServerEndpointsConfigurer endpointsConfigurer,
                                AuthenticationManager authenticationManager,
                                DefaultTokenServices jwtService,
                                TokenStore tokenStore,
                                TokenEnhancer accessTokenEnhancer,
								SecurityProperties securityProperties) {
        super(jwtService, endpointsConfigurer.getClientDetailsService(), endpointsConfigurer.getOAuth2RequestFactory(), GRANT_TYPE);
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = endpointsConfigurer.getClientDetailsService();
        this.jwtService = jwtService;
        this.tokenStore = tokenStore;
        this.accessTokenEnhancer = accessTokenEnhancer;
		this.securityProperties = securityProperties;
    }

    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        if (!grantType.equals(GRANT_TYPE)) {
            return null;
        } else {
            Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
            String username = parameters.get("username");
            String password = parameters.get("password");
            parameters.remove("password");
            Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
            ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

            String clientId = tokenRequest.getClientId();
            ClientDetails client = this.clientDetailsService.loadClientByClientId(clientId);

            this.validateGrantType(grantType, client);

            try {
                userAuth = this.authenticationManager.authenticate(userAuth);
            } catch (AccountStatusException | BadCredentialsException e) {
                throw new InvalidGrantException(e.getMessage());
            }

            if (userAuth != null && userAuth.isAuthenticated()) {
                OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
				return this.jwtService.createAccessToken(new OAuth2Authentication(storedOAuth2Request, userAuth));
            } else {
                throw new InvalidGrantException("Could not authenticate user: " + username);
            }
        }
    }
    
    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication) {
		if (!isSupportRefreshToken(authentication.getOAuth2Request())) {
			return null;
		}
		int validitySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
		String value = UUID.randomUUID().toString();
		if (validitySeconds > 0) {
			return new DefaultExpiringOAuth2RefreshToken(value, new Date(System.currentTimeMillis()
					+ (validitySeconds * 1000L)));
		}
		return new DefaultOAuth2RefreshToken(value);
	}
    
    protected boolean isSupportRefreshToken(OAuth2Request clientAuth) {
		if (clientDetailsService != null) {
			ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
			return client.getAuthorizedGrantTypes().contains("refresh_token");
		}
		return false;
	}
    
    protected int getRefreshTokenValiditySeconds(OAuth2Request clientAuth) {
		if (clientDetailsService != null) {
			ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
			Integer validity = client.getRefreshTokenValiditySeconds();
			if (validity != null) {
				return validity;
			}
		}
		return securityProperties.getClient().getRefreshTokenValiditySeconds();
	}
    
    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
		int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
		if (validitySeconds > 0) {
			token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
		}
		token.setRefreshToken(refreshToken);
		token.setScope(authentication.getOAuth2Request().getScope());
		
		
		return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
	}
    
    protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
		if (clientDetailsService != null) {
			ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
			Integer validity = client.getAccessTokenValiditySeconds();
			if (validity != null) {
				return validity;
			}
		}
		return securityProperties.getClient().getAccessTokenValiditySeconds();
	}
    
    private OAuth2AccessToken createCustomAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
		OAuth2RefreshToken refreshToken = null;
		if (existingAccessToken != null) {
			if (existingAccessToken.isExpired()) {
				if (existingAccessToken.getRefreshToken() != null) {
					refreshToken = existingAccessToken.getRefreshToken();
					// The token store could remove the refresh token when the
					// access token is removed, but we want to
					// be sure...
					tokenStore.removeRefreshToken(refreshToken);
				}
				tokenStore.removeAccessToken(existingAccessToken);
			}
			else {
				// Re-store the access token in case the authentication has changed
				tokenStore.storeAccessToken(existingAccessToken, authentication);
				return existingAccessToken;
			}
		}

		// Only create a new refresh token if there wasn't an existing one
		// associated with an expired access token.
		// Clients might be holding existing refresh tokens, so we re-use it in
		// the case that the old access token
		// expired.
		if (refreshToken == null) {
			refreshToken = createRefreshToken(authentication);
		}
		// But the refresh token itself might need to be re-issued if it has
		// expired.
		else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
			ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
			if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
				refreshToken = createRefreshToken(authentication);
			}
		}

		OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
		tokenStore.storeAccessToken(accessToken, authentication);
		// In case it was modified
		refreshToken = accessToken.getRefreshToken();
		if (refreshToken != null) {
			tokenStore.storeRefreshToken(refreshToken, authentication);
		}
		return accessToken;
    }

}
