package it.util;

/**
 * Utility per la validazione degli input utente, come i nomi dei salvataggi.
 */
public final class InputValidator {

    // Costruttore privato per evitare l'istanziazione
    private InputValidator() {}

    /**
     * Valida un nome di salvataggio.
     * Accetta da 4 a 30 caratteri alfanumerici, spazi, underscore e trattini.
     * Rimuove eventuali spazi iniziali/finali prima della validazione.
     *
     * @param input il nome da validare
     * @return true se valido, false altrimenti
     */
    public static boolean isValidSaveName(String input) {
        return input != null && input.trim().matches("[a-zA-Z0-9_\\- ]{4,30}");
    }
}


