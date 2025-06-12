package it.strategy;

import it.model.Move;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EasyStrategyTest {

    @Test
    public void testNextMoveReturnsValidMove() {
        Rectangle[] positions = {
                new Rectangle(0, 0, 80, 80)
        };

        EasyStrategy strategy = new EasyStrategy();
        Point selectedPiece = new Point(0, 0); // selezione corrispondente al primo blocco

        Move move = strategy.nextMove(positions, selectedPiece);

        assertNotNull(move, "EasyStrategy dovrebbe restituire una Move valida");
        assertEquals(0, move.getFrom().x);
        assertEquals(0, move.getFrom().y);
        assertEquals(10, move.getTo().x); // ← ora lo sposta di 10, non 20!
        assertEquals(0, move.getTo().y);
    }
}

