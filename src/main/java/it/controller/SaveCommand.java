package it.controller;

import it.model.PuzzlemasterModel;
import it.util.GameLogger;
import it.util.InputValidator;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Comando per salvare lo stato corrente del gioco nel database.
 */
public class SaveCommand extends UIController {

    private static final Logger logger = GameLogger.getLogger();
    private final PuzzlemasterModel model;
    private final PuzzlemasterUI ui;
    private String name;

    /**
     * Costruttore del comando di salvataggio.
     *
     * @param puzzlemasterModel il modello logico del gioco
     * @param puzzlemasterUI    l'interfaccia grafica del gioco
     */
    public SaveCommand(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        super(puzzlemasterModel, puzzlemasterUI);
        this.model = puzzlemasterModel;
        this.ui = puzzlemasterUI;
    }

    /**
     * Imposta il nome del salvataggio corrente.
     *
     * @param name il nome da associare al salvataggio
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Esegue il salvataggio della partita attuale, chiedendo all’utente un nome e verificandone la validità.
     *
     * @param e evento del mouse che attiva il salvataggio
     */
    @Override
    public void mousePressed(MouseEvent e) {
        String name = JOptionPane.showInputDialog(null, "Inserisci un nome per il salvataggio:", "Salva partita", JOptionPane.PLAIN_MESSAGE);

        if (name == null || name.trim().isEmpty()) {
            ui.showMessage("Nome non valido: non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!InputValidator.isValidSaveName(name)) {
            ui.showMessage("Nome non valido. Usa solo lettere, numeri, spazi, _ o - (min 4, max 30 caratteri).", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Rectangle[] rects = ui.getCurrentBlockPositions();
            StringBuilder sb = new StringBuilder();
            for (Rectangle r : rects) {
                sb.append(String.format("[%d,%d,%d,%d],", r.x, r.y, r.width, r.height));
            }

            String state = sb.toString();
            int moves = model.getCounter();
            String level = switch (model.getLevel()) {
                case 1 -> "easy";
                case 2 -> "medium";
                case 3 -> "hard";
                default -> "unknown";
            };

            model.saveGameToDB(state, moves, level, name);
            ui.showMessage("Salvataggio eseguito con successo!", "Salva", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            logger.severe("Errore durante il salvataggio: " + ex.getMessage());
            ui.showMessage("Errore durante il salvataggio: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}








