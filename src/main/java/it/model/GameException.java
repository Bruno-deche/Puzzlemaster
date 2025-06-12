package it.model;

/**
 * Eccezione personalizzata usata per segnalare errori generici nel gioco.
 */
public class GameException extends RuntimeException {

    /**
     * Costruisce una nuova GameException con il messaggio specificato.
     *
     * @param message descrizione dell'errore
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Costruisce una nuova GameException con un messaggio e una causa.
     *
     * @param message descrizione dell'errore
     * @param cause eccezione che ha causato questo errore
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}



