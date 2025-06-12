package it.util;

import java.io.IOException;
import java.util.logging.*;

/**
 * Utility per la configurazione del logger globale dell'applicazione.
 * Scrive i log su un file "puzzlemaster.log" con livello dettagliato.
 */
public class GameLogger {

    private static final Logger logger = Logger.getLogger("PuzzlemasterLogger");

    static {
        try {
            Handler fileHandler = new FileHandler("puzzlemaster.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // disabilita output su console
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.severe("Errore nella configurazione del logger: " + e.getMessage());
        }
    }

    /**
     * Restituisce l'istanza del logger configurato.
     *
     * @return logger statico condiviso per l'applicazione
     */
    public static Logger getLogger() {
        return logger;
    }
}


