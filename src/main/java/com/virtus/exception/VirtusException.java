package com.virtus.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class VirtusException extends RuntimeException {

    @Getter
    @Setter
    public class VirtusError {

        private String message;

        public VirtusError(String message) {
            this.message = message;
        }

    }

    private Collection<VirtusError> errors = new ArrayList<>();

    public VirtusException() {
        super();
    }

    public VirtusException(String message) {
        super(message);
        errors.add(new VirtusError(message));
    }

    public VirtusException(String message, Throwable cause) {
        super(message, cause);
        errors.add(new VirtusError(message));
    }

    public VirtusException(Throwable cause) {
        super(cause);
        errors.add(new VirtusError(cause.getMessage()));
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public void addError(String message) {
        errors.add(new VirtusError(message));
    }

    public void appendException(VirtusException exception) {
        errors.addAll(exception.getErrors());
    }

    public void appendException(Exception e) {
        errors.add(new VirtusError(e.getMessage()));
    }

}
