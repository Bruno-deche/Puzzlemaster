package it.util;

import java.security.MessageDigest;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Utility per effettuare l'hashing sicuro delle password tramite SHA-256.
 */
public class PasswordUtil {

    private static final Logger logger = Logger.getLogger(PasswordUtil.class.getName());

    /**
     * Esegue l'hashing della password fornita utilizzando l'algoritmo SHA-256.
     *
     * @param password la password in chiaro da convertire
     * @return stringa esadecimale dell'hash oppure null in caso di errore
     */
    public static String hashPassword(String password) {
        try {
            logger.info("Inizio del processo di hashing della password.");

            // Crea l'istanza del MessageDigest per l'algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            // Converte il risultato in formato esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            logger.info("Hashing completato con successo.");
            return hexString.toString();

        } catch (Exception e) {
            // Log degli errori con livello severe
            logger.severe("Errore durante l'hashing della password: " + e.getMessage());

            // Mostra un messaggio all'utente
            JOptionPane.showMessageDialog(null, "Errore durante la validazione della password", "Errore", JOptionPane.ERROR_MESSAGE);

            // Non lancia l'eccezione, restituisce null per indicare l'errore
            return null;
        }
    }
}




