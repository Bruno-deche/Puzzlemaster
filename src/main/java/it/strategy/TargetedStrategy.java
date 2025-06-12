package it.strategy;

import it.model.Move;
import it.model.MoveFactory;
import it.strategy.StrategyMetadata;


import java.awt.*;
import java.util.logging.Logger;

@StrategyMetadata(
        difficulty = "Difficile",
        description = "Cerca attivamente di ostacolare il completamento dell'obiettivo."
)

/**
 * Strategia mirata che genera una mossa spostando il blocco selezionato verso il basso.
 */
public class TargetedStrategy implements MoveStrategy {

    private static final Logger logger = Logger.getLogger(TargetedStrategy.class.getName());

    @Override
    public Move nextMove(Rectangle[] currentPositions, Point selectedPiece) {
        if (currentPositions == null || selectedPiece == null || currentPositions.length == 0) {
            logger.warning("⚠️ currentPositions o selectedPiece nulli");
            return null;
        }

        for (Rectangle r : currentPositions) {
            if (r.x == selectedPiece.x && r.y == selectedPiece.y) {
                Point from = new Point(r.x, r.y);
                Point to = new Point(r.x, r.y + 10); // sposta in basso

                if (from.equals(to)) {
                    logger.warning("⚠️ FROM e TO sono uguali! Nessuna mossa generata.");
                    return null;
                }

                logger.info("✅ Mossa generata da " + from + " a " + to);
                return MoveFactory.createMove("targeted", from, to);
            }
        }

        logger.warning("⚠️ Nessun blocco trovato in posizione: " + selectedPiece);
        return null;
    }
}











