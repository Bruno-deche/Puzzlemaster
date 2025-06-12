package it.strategy;

import it.model.Move;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RandomStrategyTest {

    @Test
    public void testNextMoveReturnsValidMove() {
        Rectangle[] positions = {
                new Rectangle(0, 0, 80, 80),
                new Rectangle(100, 100, 80, 80)
        };

        // Selezioniamo esplicitamente un blocco da muovere
        Point selectedPiece = new Point(100, 100);

        RandomStrategy strategy = new RandomStrategy();
        Move move = strategy.nextMove(positions, selectedPiece);

        assertNotNull(move, "RandomStrategy dovrebbe restituire una mossa valida");

        // Verifica che il punto di partenza corrisponda a quello selezionato
        assertEquals(selectedPiece.x, move.getFrom().x, "La mossa dovrebbe partire dal blocco selezionato (x)");
        assertEquals(selectedPiece.y, move.getFrom().y, "La mossa dovrebbe partire dal blocco selezionato (y)");

        // Verifica che il punto di arrivo sia una modifica coerente (es. verso destra)
        assertEquals(selectedPiece.x + 10, move.getTo().x, "La mossa dovrebbe spostare verso destra di 10px");
        assertEquals(selectedPiece.y, move.getTo().y, "La coordinata y non dovrebbe cambiare");
    }
}




