package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;

/**
 * Listener per la gestione dei salvataggi.
 * Consente di visualizzare, caricare e cancellare salvataggi esistenti.
 */
public class SavedGamesListener extends MouseAdapter {

    private static final Logger logger = Logger.getLogger(SavedGamesListener.class.getName());
    private final PuzzlemasterModel model;
    private final PuzzlemasterUI ui;
    private final SaveCommand saveCommand;

    /**
     * Costruttore del listener.
     *
     * @param model         il modello del gioco
     * @param ui            l'interfaccia utente
     * @param saveCommand   il comando di salvataggio da riutilizzare
     */
    public SavedGamesListener(PuzzlemasterModel model, PuzzlemasterUI ui, SaveCommand saveCommand) {
        this.model = model;
        this.ui = ui;
        this.saveCommand = saveCommand;
    }

    /**
     * Mostra la lista dei salvataggi. Quando uno viene selezionato, viene ripristinato lo stato.
     * Se l'utente clicca sull'opzione di eliminazione, il salvataggio viene rimosso dal database.
     *
     * @param e evento del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        try {
            List<String> savedGames = model.getSavedGameList();

            if (savedGames.isEmpty()) {
                ui.showMessage("Nessun salvataggio disponibile.", "Caricamento", JOptionPane.INFORMATION_MESSAGE);
                logger.info("Nessun salvataggio trovato.");
                return;
            }

            ui.showSavedGames(savedGames, new MouseAdapter() {
                /**
                 * Carica il salvataggio selezionato quando un pulsante è premuto.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    JButton source = (JButton) e.getSource();
                    String selectedSave = (String) source.getClientProperty("selectedSave");

                    if (selectedSave != null) {
                        try {
                            model.resumeState(selectedSave);
                            Rectangle[] pos = model.getInitialPositions();
                            ui.initGame(pos.length, model.getCounter(), saveCommand);
                            ui.getBoardPanel().revalidate();
                            ui.getBoardPanel().repaint();
                            logger.info("Stato salvato ripristinato per: " + selectedSave);
                        } catch (Exception ex) {
                            ui.showMessage("Errore nel caricamento: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            logger.severe("Errore nel caricamento dello stato salvato: " + ex.getMessage());
                        }
                    }
                }

                /**
                 * Elimina un salvataggio se il pulsante corrispondente viene cliccato.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    JButton source = (JButton) e.getSource();
                    String saveToDelete = (String) source.getClientProperty("deleteSave");
                    if (saveToDelete != null) {
                        try {
                            model.deleteSavedGame(saveToDelete);
                            ui.showMessage("Salvataggio eliminato: " + saveToDelete, "Elimina", JOptionPane.INFORMATION_MESSAGE);
                            logger.info("Salvataggio eliminato: " + saveToDelete);
                        } catch (Exception ex) {
                            ui.showMessage("Errore nell'eliminazione del salvataggio.", "Errore", JOptionPane.ERROR_MESSAGE);
                            logger.severe("Errore nell'eliminazione del salvataggio: " + ex.getMessage());
                        }
                    }
                }
            });

        } catch (Exception ex) {
            ui.showMessage("Errore durante il recupero dei salvataggi.", "Errore", JOptionPane.ERROR_MESSAGE);
            logger.severe("Errore durante il recupero dei salvataggi: " + ex.getMessage());
        }
    }
}














