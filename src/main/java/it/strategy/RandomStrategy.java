package it.strategy;

import it.model.Move;
import it.model.MoveFactory;
import it.strategy.StrategyMetadata;


import java.awt.*;
import java.util.Random;


@StrategyMetadata(
        difficulty = "Medio",
        description = "Muove i blocchi in direzioni casuali ad ogni turno."
)

/**
 * Strategia che seleziona casualmente un blocco tra quelli presenti
 * e genera una mossa che lo sposta verso destra.
 */
public class RandomStrategy implements MoveStrategy {

    private final Random rand = new Random();

    /**
     * Genera una mossa casuale spostando un blocco selezionato a destra.
     *
     * @param currentPositions array delle posizioni correnti dei blocchi
     * @param selectedPiece ignorato in questa strategia
     * @return una mossa creata casualmente oppure null se non ci sono blocchi
     */
    @Override
    public Move nextMove(Rectangle[] currentPositions, Point selectedPiece) {
        if (currentPositions == null || currentPositions.length == 0) return null;

        int index = rand.nextInt(currentPositions.length);
        Rectangle r = currentPositions[index];

        Point from = new Point(r.x, r.y);
        Point to = new Point(r.x + 10, r.y); // sposta il blocco scelto a destra

        return MoveFactory.createMove("random", from, to);
    }
}




