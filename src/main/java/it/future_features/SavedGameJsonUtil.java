package it.future_features;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.model.SavedGame;
import it.util.InputValidator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Utility per il salvataggio e il caricamento di oggetti {@link SavedGame} in formato JSON.
 */
public class SavedGameJsonUtil {

    private static final Logger logger = Logger.getLogger(SavedGameJsonUtil.class.getName());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String SAVE_PATH = "saves/json/";

    /**
     * Salva l'oggetto {@link SavedGame} in un file JSON.
     *
     * @param game il salvataggio da esportare
     */
    public static void saveToJson(SavedGame game) {
        String name = game.getName();

        // Validazione difensiva del nome del file
        if (!InputValidator.isValidSaveName(name)) {
            logger.warning("⚠️ Nome del file JSON non valido: " + name);
            JOptionPane.showMessageDialog(null, "Nome del file JSON non valido: " + name, "Errore", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Nome del file JSON non valido: " + name);
        }

        try (FileWriter writer = new FileWriter(SAVE_PATH + name + ".json")) {
            gson.toJson(game, writer);
            logger.info("✅ Partita salvata in JSON: " + name);
        } catch (IOException e) {
            logger.severe("❌ Errore durante il salvataggio JSON: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Errore durante il salvataggio del gioco: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("❌ Errore durante il salvataggio JSON", e);
        }
    }

    /**
     * Carica un oggetto {@link SavedGame} da un file JSON dato il nome del salvataggio.
     *
     * @param fileName il nome del file senza estensione
     * @return l'oggetto {@link SavedGame} deserializzato
     */
    public static SavedGame loadFromJson(String fileName) {
        // Validazione difensiva anche in lettura
        if (!InputValidator.isValidSaveName(fileName)) {
            logger.warning("⚠️ Nome del file JSON non valido: " + fileName);
            JOptionPane.showMessageDialog(null, "Nome del file JSON non valido: " + fileName, "Errore", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Nome del file JSON non valido: " + fileName);
        }

        try (FileReader reader = new FileReader(SAVE_PATH + fileName + ".json")) {
            logger.info("✅ Caricamento partita da JSON: " + fileName);
            return gson.fromJson(reader, SavedGame.class);
        } catch (IOException e) {
            logger.severe("❌ Errore durante il caricamento JSON: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Errore durante il caricamento del gioco: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("❌ Errore durante il caricamento JSON", e);
        }
    }
}






