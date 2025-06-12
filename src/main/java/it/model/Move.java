package it.model;

import java.awt.*;

/**
 * Rappresenta una mossa effettuata su un blocco all'interno del gioco.
 * Ogni mossa contiene il tipo, la posizione di partenza e quella di arrivo.
 */
public class Move {

    private final String type;
    private final Point from;
    private final Point to;

    /**
     * Crea una nuova mossa.
     *
     * @param type tipo di mossa (es. "auto", "hint", "manuale", ecc.)
     * @param from punto iniziale
     * @param to   punto finale
     */
    public Move(String type, Point from, Point to) {
        this.type = type;
        this.from = from;
        this.to = to;
    }

    /**
     * Restituisce il tipo di mossa.
     *
     * @return tipo della mossa
     */
    public String getType() {
        return type;
    }

    /**
     * Restituisce il punto di partenza della mossa.
     *
     * @return punto di partenza
     */
    public Point getFrom() {
        return from;
    }

    /**
     * Restituisce il punto di arrivo della mossa.
     *
     * @return punto di arrivo
     */
    public Point getTo() {
        return to;
    }

    /**
     * Restituisce una mossa inversa, che rappresenta l'annullamento di questa.
     *
     * @return mossa inversa (da `to` a `from`)
     */
    public Move reverse() {
        return new Move(type, to, from);
    }
}






