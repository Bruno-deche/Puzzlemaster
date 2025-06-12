package it.future_features;

import it.model.Move;

import java.awt.*;

/**
 * Rappresenta una mossa suggerita nel gioco, etichettata come "hint".
 * Estende la classe Move con un tipo predefinito.
 */
public class HintMove extends Move {

    /**
     * Crea una nuova mossa suggerita dal punto di partenza a quello di arrivo.
     *
     * @param from punto di partenza
     * @param to   punto di arrivo
     */
    public HintMove(Point from, Point to) {
        super("hint", from, to);
    }
}



