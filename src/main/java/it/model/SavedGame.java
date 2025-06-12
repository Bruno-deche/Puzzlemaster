package it.model;

import java.io.Serializable;

/**
 * Rappresenta una partita salvata, contenente nome, mosse, numero di mosse e livello.
 */
public class SavedGame implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final int[][] moves; // ogni int[] è [x, y, width, height]
    private final int moveCount;
    private final int level;

    /**
     * Costruttore principale della partita salvata.
     *
     * @param name      nome del salvataggio
     * @param moves     array delle mosse (x, y, width, height)
     * @param moveCount numero totale di mosse effettuate
     * @param level     livello di difficoltà
     */
    public SavedGame(String name, int[][] moves, int moveCount, int level) {
        this.name = name;
        this.moves = moves;
        this.moveCount = moveCount;
        this.level = level;
    }

    /**
     * Costruttore alternativo usato per i test, senza specificare il livello.
     *
     * @param name      nome del salvataggio
     * @param moves     array delle mosse
     * @param moveCount numero di mosse
     */
    public SavedGame(String name, int[][] moves, int moveCount) {
        this(name, moves, moveCount, 0);
    }

    /**
     * Restituisce il nome del salvataggio.
     *
     * @return nome del salvataggio
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce l'elenco delle mosse effettuate.
     *
     * @return array bidimensionale di mosse
     */
    public int[][] getMoves() {
        return moves;
    }

    /**
     * Restituisce il numero totale di mosse effettuate.
     *
     * @return numero di mosse
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Restituisce il livello associato alla partita salvata.
     *
     * @return livello di difficoltà
     */
    public int getLevel() {
        return level;
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto SavedGame.
     *
     * @return stringa descrittiva del salvataggio
     */
    @Override
    public String toString() {
        return "SavedGame{name='" + name + "', moves=" + moves.length + ", moveCount=" + moveCount + ", level=" + level + "}";
    }
}




