package it.view;

import java.awt.*;

/**
 * Rappresenta un obiettivo di posizione e colore che un blocco deve raggiungere.
 */
public class BlockGoal {

    /**
     * Area target per il blocco.
     */
    public Rectangle bounds;

    /**
     * Colore che il blocco deve avere in questa posizione.
     */
    public Color color;

    /**
     * Costruttore dell'obiettivo blocco.
     *
     * @param bounds area da raggiungere
     * @param color  colore richiesto
     */
    public BlockGoal(Rectangle bounds, Color color) {
        this.bounds = bounds;
        this.color = color;
    }
}


