package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;

import java.awt.event.MouseEvent;

/**
 * Handles returning to the start screen.
 */
public class HomeCommand extends UIController {

    private final SaveCommand save;

    /**
     * Costruttore del comando per il ritorno alla schermata iniziale.
     *
     * @param puzzlemasterModel il modello del gioco
     * @param puzzlemasterUI    l'interfaccia utente
     * @param save              il comando di salvataggio
     */
    public HomeCommand(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI, SaveCommand save) {
        super(puzzlemasterModel, puzzlemasterUI);
        this.save = save;
    }

    /**
     * Salva il gioco corrente e torna alla schermata iniziale.
     *
     * @param e evento del mouse che ha attivato il comando
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // ✅ Prima salva il gioco
        save.mousePressed(e);

        // ✅ Poi torna alla schermata iniziale
        puzzlemasterUI.initStart();

        // ✅ Resetta il nome
        save.setName(null);
    }
}



