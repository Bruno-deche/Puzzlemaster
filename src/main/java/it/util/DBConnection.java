package it.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Classe utility per la gestione della connessione al database.
 * Carica le credenziali da un file di configurazione.
 */
public class DBConnection {

    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.severe("Impossibile trovare il file di configurazione 'config.properties'.");
                JOptionPane.showMessageDialog(null, "Impossibile trovare il file di configurazione 'config.properties'.", "Errore", JOptionPane.ERROR_MESSAGE);
                throw new IOException("File di configurazione mancante.");
            }

            Properties prop = new Properties();
            prop.load(input);

            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");

            logger.info("Credenziali DB caricate con successo.");
        } catch (IOException e) {
            logger.severe("❌ Errore nel caricamento delle credenziali DB: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Errore nel caricamento delle credenziali DB.", "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Errore nel caricamento delle credenziali DB", e);
        }
    }

    /**
     * Restituisce una connessione al database utilizzando le credenziali caricate.
     *
     * @return connessione JDBC valida
     * @throws SQLException se la connessione fallisce
     */
    public static Connection getConnection() throws SQLException {
        try {
            logger.info("Tentativo di connessione al database...");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connessione al database riuscita.");
            return connection;
        } catch (SQLException e) {
            logger.severe("❌ Errore nella connessione al database: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Errore nella connessione al database.", "Errore", JOptionPane.ERROR_MESSAGE);
            throw e; // Rilancia l'eccezione dopo averla loggata
        }
    }
}






