package it.model;

/**
 * Eccezione specifica per problemi legati alla persistenza dei dati del gioco.
 * Usata per indicare errori durante il salvataggio o il caricamento da database o file.
 */
public class GamePersistenceException extends RuntimeException {

    /**
     * Costruisce una nuova GamePersistenceException con il messaggio specificato.
     *
     * @param message descrizione dell'errore
     */
    public GamePersistenceException(String message) {
        super(message);
    }

    /**
     * Costruisce una nuova GamePersistenceException con un messaggio e una causa.
     *
     * @param message descrizione dell'errore
     * @param cause eccezione che ha causato questo errore
     */
    public GamePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}



