package com.virtus.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties("security")
public class SecurityProperties {

    private ClientProperties client;
    private JwtProperties jwt;
    private List<String> resourceIds = new ArrayList<>();

    @Data
    public static class JwtProperties {
        private String iss;
        private Resource keyStore;
        private String keyStorePassword;
        private String keyPairAlias;
        private String keyPairPassword;
        private Resource publicKey;
    }

    @Data
    public static class ClientProperties {
        private String id;
        private String secret;
        private Integer accessTokenValiditySeconds;
        private Integer refreshTokenValiditySeconds;
    }
}
