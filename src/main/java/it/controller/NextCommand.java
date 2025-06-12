package it.controller;

import it.model.Move;
import it.model.PuzzlemasterModel;
import it.util.GameLogger;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Comando che gestisce il suggerimento della prossima mossa automatica nel gioco.
 * Recupera la mossa migliore dal solver e la applica sulla UI.
 */
public class NextCommand extends UIController {

    private static final Logger logger = GameLogger.getLogger();

    /**
     * Costruttore del comando per suggerire la prossima mossa.
     *
     * @param puzzlemasterModel il modello del gioco da cui recuperare le mosse
     * @param puzzlemasterUI    l'interfaccia utente su cui applicare la mossa
     */
    public NextCommand(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        super(puzzlemasterModel, puzzlemasterUI);
    }

    /**
     * Gestisce la pressione del mouse eseguendo la mossa suggerita dal solver.
     *
     * @param e l'evento di pressione del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        try {
            Move bestMove = puzzlemasterModel.nextBestMove();

            if (bestMove != null) {
                puzzlemasterUI.makeMove(bestMove, puzzlemasterModel.getCounter());
                logger.info("✨ Suggerita una mossa automatica tramite nextBestMove.");
                puzzlemasterUI.winHandler();
            } else {
                logger.warning("⚠️ Nessuna mossa suggeribile disponibile.");
                puzzlemasterUI.showMessage("Nessuna mossa suggerita disponibile", "Suggerimento", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            logger.severe("❌ Errore nel recuperare la mossa suggerita: " + ex.getMessage());
            puzzlemasterUI.showMessage("Problemi di connettività con il solver, riprova più tardi", "Solver", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo obbligatorio dell'interfaccia MouseListener. Non utilizzato.
     *
     * @param e l'evento del mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // non utilizzato
    }

    /**
     * Metodo obbligatorio dell'interfaccia MouseListener. Non utilizzato.
     *
     * @param e l'evento del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // non utilizzato
    }

    /**
     * Metodo obbligatorio dell'interfaccia MouseListener. Non utilizzato.
     *
     * @param e l'evento del mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // non utilizzato
    }

    /**
     * Metodo obbligatorio dell'interfaccia MouseListener. Non utilizzato.
     *
     * @param e l'evento del mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // non utilizzato
    }
}




