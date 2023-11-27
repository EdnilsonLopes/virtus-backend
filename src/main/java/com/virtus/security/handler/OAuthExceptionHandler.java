package com.virtus.security.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthExceptionHandler implements WebResponseExceptionTranslator<OAuth2Exception> {

        private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

        @Override
        public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

            Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

            Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(
                    OAuth2Exception.class, causeChain);

            if (ase != null) {
                return handleOAuth2Exception(new com.virtus.exception.OAuth2Exception((OAuth2Exception) ase));
            }

            ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                    causeChain);

            if (ase != null) {
                return handleOAuth2Exception(new com.virtus.exception.OAuth2Exception(e.getMessage(), 401));
            }

            ase = (AccessDeniedException) throwableAnalyzer
                    .getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            if (ase != null) {
                return handleOAuth2Exception(new com.virtus.exception.OAuth2Exception(e.getMessage(), 403));
            }

            return handleOAuth2Exception(new com.virtus.exception.OAuth2Exception(e.getMessage(), 400));
        }

        private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

            int status = e.getHttpErrorCode();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cache-Control", "no-store");
            headers.set("Pragma", "no-cache");
            if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
                headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
            }

            ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(e, headers,
                    HttpStatus.valueOf(status));

            return response;

        }

        public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
            this.throwableAnalyzer = throwableAnalyzer;
        }

    }