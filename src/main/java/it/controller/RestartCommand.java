package it.controller;

import it.model.PuzzlemasterModel;
import it.util.GameLogger;
import it.view.PuzzlemasterUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

/**
 * Comando per riavviare la partita ripristinando lo stato iniziale.
 */
public class RestartCommand implements MouseListener {

    private static final Logger logger = GameLogger.getLogger();

    private final PuzzlemasterModel model;
    private final PuzzlemasterUI ui;

    /**
     * Costruttore del comando di restart.
     *
     * @param model il modello del gioco
     * @param ui    l'interfaccia utente
     */
    public RestartCommand(PuzzlemasterModel model, PuzzlemasterUI ui) {
        this.model = model;
        this.ui = ui;
    }

    /**
     * Ripristina lo stato iniziale del gioco e reinizializza la UI.
     *
     * @param e evento del mouse che attiva il comando
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Recupera posizioni e colori iniziali dal modello
        Rectangle[] initialPositions = model.getInitialPositions();
        Color[] initialColors = model.getInitialColors();

        // Imposta lo stato iniziale e riavvia la UI
        model.setInitialState(initialPositions, initialColors);
        ui.restart();
        ui.attachBlockListeners(new CollisionBlockListener(ui.getBoardPanel(), ui));

        logger.info("🔁 Restart eseguito");
    }

    // Metodi richiesti da MouseListener (vuoti)
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}







