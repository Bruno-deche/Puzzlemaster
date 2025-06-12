package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;

import java.awt.event.MouseAdapter;

/**
 * Classe astratta base per tutti i controller UI.
 * Estende MouseAdapter per gestire eventi mouse.
 * Fornisce accesso condiviso al modello e alla vista.
 */
public abstract class UIController extends MouseAdapter {

    protected final PuzzlemasterModel puzzlemasterModel;
    protected final PuzzlemasterUI puzzlemasterUI;

    /**
     * Costruttore comune a tutti i controller derivati.
     *
     * @param puzzlemasterModel modello del gioco
     * @param puzzlemasterUI     interfaccia grafica del gioco
     */
    public UIController(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        this.puzzlemasterModel = puzzlemasterModel;
        this.puzzlemasterUI = puzzlemasterUI;
    }
}



