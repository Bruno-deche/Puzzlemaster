package it.strategy;

import it.model.Move;
import it.model.MoveFactory;
import it.strategy.StrategyMetadata;

import java.awt.*;

/**
 * Strategia facile: muove i blocchi con movimenti prevedibili e semplici.
 */
@StrategyMetadata(
        difficulty = "Facile",
        description = "Muove i blocchi con strategia sequenziale semplice."
)

public class EasyStrategy implements MoveStrategy {

    /**
     * Restituisce la prossima mossa suggerita per la strategia "facile".
     * Se trova un blocco nella posizione selezionata, lo sposta verso destra.
     *
     * @param currentPositions array delle posizioni correnti dei blocchi
     * @param selectedPiece    punto selezionato dall'utente
     * @return oggetto {@code Move} suggerito, oppure {@code null} se non valido
     */
    @Override
    public Move nextMove(Rectangle[] currentPositions, Point selectedPiece) {
        if (currentPositions == null || currentPositions.length == 0 || selectedPiece == null) return null;

        for (Rectangle r : currentPositions) {
            if (r.x == selectedPiece.x && r.y == selectedPiece.y) {
                Point from = new Point(r.x, r.y);
                Point to = new Point(r.x + 10, r.y); // lo sposta a destra
                return MoveFactory.createMove("hint", from, to);
            }
        }

        return null;
    }
}





