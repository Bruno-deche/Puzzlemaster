package it.controller;

import it.model.AuthenticationException;
import it.model.PuzzlemasterModel;
import it.util.GameLogger;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * The class is a UI controller that handles authentication actions (login and signup).
 */
public class AuthListener extends UIController {

    private static final Logger logger = GameLogger.getLogger();

    public AuthListener(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        super(puzzlemasterModel, puzzlemasterUI);
    }

    /**
     * Gestisce il clic su un pulsante di autenticazione (login o registrazione).
     * Recupera i dati utente dai client property del JButton e attiva il login o signup
     * a seconda del testo del bottone.
     *
     * @param e evento di tipo MouseEvent generato dal clic su un JButton
     */
    @Override
    public void mousePressed(MouseEvent e) {
        String username = ((JButton) e.getSource()).getClientProperty("username").toString();
        String password = ((JButton) e.getSource()).getClientProperty("password").toString();
        String type = ((JButton) e.getSource()).getText();  // Log in or Sign up

        try {
            if (type.equals("Log in")) {
                logger.info("🔐 Tentativo di login per utente: " + username);
                puzzlemasterModel.login(username, password);
            } else if (type.equals("Sign up")) {
                logger.info("📝 Tentativo di registrazione per utente: " + username);
                puzzlemasterModel.registration(username, password);
            } else {
                logger.warning("⚠️ Azione sconosciuta: " + type);
                return;
            }

            puzzlemasterUI.showMessage("Hi " + username + "! " + type + " successfully", type, JOptionPane.INFORMATION_MESSAGE);
            puzzlemasterUI.initUser(username);
            logger.info("✅ " + type + " eseguito con successo per utente: " + username);

        } catch (AuthenticationException ex) {
            logger.warning("❌ Errore di autenticazione per '" + username + "': " + ex.getMessage());
            puzzlemasterUI.showMessage(ex.getMessage(), type, JOptionPane.ERROR_MESSAGE);
        }
    }
}




