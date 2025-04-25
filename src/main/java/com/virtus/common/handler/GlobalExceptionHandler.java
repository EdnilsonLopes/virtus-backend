package com.virtus.common.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.virtus.common.domain.dto.ErrorInfoResponseDTO;
import com.virtus.exception.VirtusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    public GlobalExceptionHandler() {

    }

    @ExceptionHandler(VirtusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorInfoResponseDTO> handleGenericApiException(VirtusException e) {
        if (e.hasErrors()) {
            String errorMessage = "";
            for (VirtusException.VirtusError error : e.getErrors()) {
                errorMessage += error.getMessage() + "\n";
            }
            log.info("handleGenericApiException, msg {}", errorMessage);
            return ResponseEntity.badRequest().body(new ErrorInfoResponseDTO(100, errorMessage));
        } else {
            return builderErrorInfoResponse(400, e.getMessage());
        }
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ErrorInfoResponseDTO> handleUnauthorizedException(UsernameNotFoundException e) {
        String errorMessage = e.getMessage();
        log.info("handleUnauthorizedException, msg {}", errorMessage);
        return ResponseEntity.badRequest().body(new ErrorInfoResponseDTO(HttpStatus.UNAUTHORIZED.value(), errorMessage));
    }

    @ExceptionHandler(InvalidGrantException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ErrorInfoResponseDTO> handleInvalidGrandException(InvalidGrantException e) {
        String errorMessage = e.getMessage();
        log.info("handleInvalidGrandException, msg {}", errorMessage);
        return ResponseEntity.badRequest().body(new ErrorInfoResponseDTO(HttpStatus.UNAUTHORIZED.value(), errorMessage));
    }

    private ResponseEntity<ErrorInfoResponseDTO> builderErrorInfoResponse(
            Integer errorCode, String message) {
        return ResponseEntity.badRequest().body(new ErrorInfoResponseDTO(errorCode, message));
    }

}
