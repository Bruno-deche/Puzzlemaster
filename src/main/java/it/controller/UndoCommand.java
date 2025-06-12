package it.controller;

import it.model.Move;
import it.model.PuzzlemasterModel;
import it.util.GameLogger;
import it.view.PuzzlemasterUI;

import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Gestisce l'operazione di Undo nel gioco.
 */
public class UndoCommand extends UIController {

    private static final Logger logger = GameLogger.getLogger();

    public UndoCommand(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        super(puzzlemasterModel, puzzlemasterUI);
    }

    /**
     * Esegue l'annullamento dell'ultima mossa effettuata nel gioco, aggiornando il modello e l'interfaccia utente.
     *
     * @param e evento di pressione del mouse che attiva il comando.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        try {
            // Recupera l'ultima mossa da annullare
            Move lastMoveRev = puzzlemasterModel.undo();

            if (lastMoveRev != null) {
                // Applica la mossa inversa alla UI
                puzzlemasterUI.makeMove(lastMoveRev, puzzlemasterModel.getCounter());

                // (Facoltativo) forza ridisegno del pannello
                puzzlemasterUI.repaint();

                logger.info("↩️ Undo eseguito. Mossa annullata.");
            } else {
                logger.warning("⚠️ Nessuna mossa da annullare.");
                puzzlemasterUI.showMessage("Nessuna mossa da annullare", "Undo", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            logger.severe("❌ Errore durante l'undo: " + ex.getMessage());
            puzzlemasterUI.showMessage("Errore durante l'annullamento della mossa", "Errore", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}




