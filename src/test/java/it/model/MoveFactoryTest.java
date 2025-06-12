package it.model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveFactoryTest {

    @Test
    public void testCreateMoveReturnsValidObject() {
        Point from = new Point(0, 0);
        Point to = new Point(20, 0);

        Move move = MoveFactory.createMove("user", from, to);

        assertNotNull(move, "La factory dovrebbe restituire un oggetto Move");
        assertEquals(from, move.getFrom(), "Il punto di partenza dovrebbe corrispondere");
        assertEquals(to, move.getTo(), "Il punto di arrivo dovrebbe corrispondere");
    }
}

