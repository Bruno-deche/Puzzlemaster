package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Listener per il pannello di gioco che rileva clic e rilasci del mouse sui blocchi JButton.
 */
public class BoardListener extends MouseAdapter {

    private static final Logger logger = Logger.getLogger(BoardListener.class.getName());

    private final PuzzlemasterModel model;
    private final PuzzlemasterUI ui;

    /**
     * Costruttore che associa il listener al modello logico e alla UI del gioco.
     *
     * @param model riferimento al modello logico
     * @param ui    riferimento alla UI del gioco
     */
    public BoardListener(PuzzlemasterModel model, PuzzlemasterUI ui) {
        this.model = model;
        this.ui = ui;
    }

    /**
     * Rileva la pressione del mouse su un blocco (JButton) e stampa informazioni di log.
     *
     * @param e evento del mouse generato alla pressione
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Component c = e.getComponent();
        if (c instanceof JButton button) {
            Point point = button.getLocation();
            String buttonText = button.getText(); // Recuperiamo il testo del bottone
            logger.info("🔴 Blocco cliccato: " + buttonText + " in posizione: " + point);
        }
    }

    /**
     * Rileva il rilascio del mouse su un blocco (JButton) e stampa informazioni di log.
     *
     * @param e evento del mouse generato al rilascio
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Component c = e.getComponent();
        if (c instanceof JButton button) {
            String buttonText = button.getText();
            logger.info("🟢 Blocco rilasciato: " + buttonText);

            // Aggiungere altre azioni se necessarie per mouseReleased
            logger.info("🟢 Il rilascio del blocco è stato completato: " + buttonText);
        }
    }
}






