package com.virtus.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.virtus.security.serializer.OAuth2ExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.*;

@JsonSerialize(using = OAuth2ExceptionSerializer.class)
public class OAuth2Exception
        extends org.springframework.security.oauth2.common.exceptions.OAuth2Exception {

    private int authErrorCode;

    public OAuth2Exception(org.springframework.security.oauth2.common.exceptions.OAuth2Exception oAuth2Exception) {
        super(oAuth2Exception.getSummary());
        this.lookupException(oAuth2Exception);
    }

    public OAuth2Exception(String msg, int authErrorCode) {
        super(msg);
        this.authErrorCode = authErrorCode;
    }

    private void lookupException(org.springframework.security.oauth2.common.exceptions.OAuth2Exception oAuth2Exception) {

        if (oAuth2Exception == null) {
            this.authErrorCode = 401;
        } else if (oAuth2Exception instanceof UserDeniedAuthorizationException) {
            this.authErrorCode = 401;
        } else if (oAuth2Exception instanceof InsufficientScopeException) {
            this.authErrorCode = 401;
        } else if (oAuth2Exception instanceof UnsupportedGrantTypeException) {
            this.authErrorCode = 403;
        } else if (oAuth2Exception instanceof UnauthorizedClientException) {
            this.authErrorCode = 403;
        } else if (oAuth2Exception instanceof InvalidTokenException) {
            this.authErrorCode = 401;
        } else if (oAuth2Exception instanceof InvalidGrantException) {
            this.authErrorCode = 403;
        } else if (oAuth2Exception instanceof InvalidRequestException) {
            this.authErrorCode = 400;
        } else {
            this.authErrorCode = 401;
        }

    }

    public int getAuthErrorCode() {
        return authErrorCode;
    }

}
