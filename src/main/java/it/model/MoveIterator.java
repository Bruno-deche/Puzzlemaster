package it.model;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iteratore per una lista di mosse di gioco.
 */
public class MoveIterator implements Iterator<Move> {

    private final List<Move> moves;
    private int index = 0;

    /**
     * Costruisce un iteratore per la lista fornita di mosse.
     *
     * @param moves la lista delle mosse da iterare
     */
    public MoveIterator(List<Move> moves) {
        this.moves = moves;
    }

    /**
     * Verifica se ci sono altre mosse nella lista.
     *
     * @return true se ci sono altre mosse, false altrimenti
     */
    @Override
    public boolean hasNext() {
        return index < moves.size();
    }

    /**
     * Restituisce la prossima mossa nella sequenza.
     *
     * @return la prossima mossa
     * @throws NoSuchElementException se non ci sono più mosse
     */
    @Override
    public Move next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Nessuna mossa successiva.");
        }
        return moves.get(index++);
    }
}



