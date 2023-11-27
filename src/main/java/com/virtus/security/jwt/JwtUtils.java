package com.virtus.security.jwt;

import com.nimbusds.jwt.SignedJWT;
import com.virtus.domain.model.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@Slf4j
public class JwtUtils {

    public static CurrentUser extractUser(OAuth2Authentication authentication) {
        try {
            String jwt = getJwtFromAuthentication(authentication);
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            return CurrentUser.builder()
                    .id(Integer.parseInt(signedJWT.getJWTClaimsSet().getSubject()))
                    .name(signedJWT.getJWTClaimsSet().getStringClaim("name"))
                    .email(signedJWT.getJWTClaimsSet().getStringClaim("email"))
                    .username(signedJWT.getJWTClaimsSet().getStringClaim("username"))
                    .role(signedJWT.getJWTClaimsSet().getStringClaim("authorities"))
                    .build();
        } catch (Exception e) {
            log.error("Error to read subject from jwt", e);
            return null;
        }
    }

    public static CurrentUser extractUser(String jwt) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            return CurrentUser.builder()
                    .id(signedJWT.getJWTClaimsSet().getIntegerClaim("id"))
                    .name(signedJWT.getJWTClaimsSet().getStringClaim("name"))
                    .email(signedJWT.getJWTClaimsSet().getStringClaim("email"))
                    .username(signedJWT.getJWTClaimsSet().getStringClaim("username"))
                    .role(signedJWT.getJWTClaimsSet().getStringClaim("role"))
                    .build();
        } catch (Exception e) {
            log.error("Error to read subject from jwt", e);
            return null;
        }
    }

    private static String getJwtFromAuthentication(OAuth2Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
    }

}
