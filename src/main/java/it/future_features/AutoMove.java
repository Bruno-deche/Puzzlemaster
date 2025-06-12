package it.future_features;

import it.model.Move;

import java.awt.*;

/**
 * Rappresenta una mossa automatica suggerita dal solver.
 * Estende la classe {@link Move} specificando il tipo "auto".
 */
public class AutoMove extends Move {

    /**
     * Crea una mossa automatica tra due punti specificati.
     *
     * @param from punto di partenza della mossa
     * @param to   punto di arrivo della mossa
     */
    public AutoMove(Point from, Point to) {
        super("auto", from, to);
    }
}



