package com.virtus.security.config;

import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

public class JwtRefreshTokenConverter extends JwtAccessTokenConverter {

    public Map<String, Object> decode(String token) {
        return super.decode(token);
    }

}