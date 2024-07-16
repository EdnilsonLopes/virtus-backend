package com.virtus.security.config;

import com.virtus.common.properties.SecurityProperties;
import com.virtus.domain.model.UserDtl;
import com.virtus.exception.VirtusException;
import com.virtus.translate.Translator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenEnhancerConfig implements TokenEnhancer {

    private final SecurityProperties securityProperties;
    private final VirtusException ROLE_NOT_FOUND = new VirtusException(Translator.translate("role.not.found.msg"));


    public TokenEnhancerConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put("iss", securityProperties.getJwt().getIss());
        if (oAuth2Authentication.getPrincipal() instanceof UserDtl) {
            UserDtl user = (UserDtl) oAuth2Authentication.getPrincipal();
            additionalInfo.put("role", user.getRole());
            additionalInfo.put("name", user.getName());
            additionalInfo.put("id", user.getId());
            additionalInfo.put("username", user.getUsername());
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
        return oAuth2AccessToken;
    }
}
