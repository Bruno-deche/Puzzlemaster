package it.controller;

import it.model.AuthenticationException;
import it.model.PuzzlemasterModel;
import it.util.GameLogger;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Gestisce il logout e l'eliminazione dell'utente.
 */
public class DisconnectionListener extends UIController {

    private static final Logger logger = GameLogger.getLogger();

    /**
     * Costruttore del listener per la disconnessione dell'utente.
     *
     * @param puzzlemasterModel modello logico
     * @param puzzlemasterUI    interfaccia utente
     */
    public DisconnectionListener(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        super(puzzlemasterModel, puzzlemasterUI);
    }

    /**
     * Gestisce gli eventi di click su pulsanti per logout o eliminazione utente.
     *
     * @param e evento del mouse associato al pulsante
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!(e.getSource() instanceof JButton)) return;

        String name = ((JButton) e.getSource()).getName();

        if ("logOut".equals(name)) {
            // Chiamata al metodo logout del modello e UI
            puzzlemasterModel.logout();
            puzzlemasterUI.logout();
            logger.info("👤 Logout eseguito correttamente.");

        } else if ("delUser".equals(name)) {
            try {
                // Tentativo di eliminazione utente
                puzzlemasterModel.delUser();
                puzzlemasterUI.logout();
                logger.info("🗑️ Utente eliminato con successo.");
            } catch (AuthenticationException ex) {
                logger.warning("❌ Errore durante l'eliminazione dell'utente: " + ex.getMessage());
                puzzlemasterUI.showMessage(ex.getMessage(), "Authentication", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}





