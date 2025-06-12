package it.model;

/**
 * Eccezione per errori legati all'autenticazione.
 */
public class AuthenticationException extends GameException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

