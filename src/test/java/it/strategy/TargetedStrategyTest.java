package it.strategy;

import it.model.Move;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TargetedStrategyTest {

    @Test
    public void testNextMoveReturnsValidMove() {
        Rectangle[] positions = {
                new Rectangle(0, 0, 80, 80)
        };

        Point selectedPiece = new Point(0, 0); // selezione corrispondente al blocco

        TargetedStrategy strategy = new TargetedStrategy();
        Move move = strategy.nextMove(positions, selectedPiece);

        assertNotNull(move, "TargetedStrategy dovrebbe restituire una mossa valida");
        assertEquals(0, move.getFrom().x);
        assertEquals(0, move.getFrom().y);
        assertEquals(0, move.getTo().x);
        assertEquals(10, move.getTo().y); // spostato in basso di 10px
    }

    @Test
    public void testNextMoveReturnsNullIfNoMatch() {
        Rectangle[] positions = {
                new Rectangle(100, 100, 80, 80)
        };

        Point selectedPiece = new Point(0, 0); // non corrisponde

        TargetedStrategy strategy = new TargetedStrategy();
        Move move = strategy.nextMove(positions, selectedPiece);

        assertNull(move, "TargetedStrategy dovrebbe restituire null se il blocco non è trovato");
    }
}

