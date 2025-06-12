package it.composite;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BlockTest {

    @Test
    public void testMoveChangesPosition() {
        Block block = new Block(
                new Rectangle(50, 50, 80, 80),
                Color.RED,
                Block.Direction.HORIZONTAL,
                Block.Shape.SMALL
        );
        block.move(20, 30);
        Rectangle expected = new Rectangle(70, 80, 80, 80);
        assertEquals(expected, block.getBounds(), "Il blocco dovrebbe essere spostato di (20,30)");
    }

    @Test
    public void testGetBoundsReturnsCopy() {
        Rectangle original = new Rectangle(100, 100, 50, 50);
        Block block = new Block(
                original,
                Color.BLUE,
                Block.Direction.VERTICAL,
                Block.Shape.MEDIUM
        );
        Rectangle bounds = block.getBounds();
        bounds.setLocation(200, 200);
        assertNotEquals(bounds.getLocation(), block.getBounds().getLocation(),
                "getBounds() dovrebbe restituire una copia difensiva");
    }

    @Test
    public void testColorIsStoredCorrectly() {
        Block block = new Block(
                new Rectangle(0, 0, 10, 10),
                Color.GREEN,
                Block.Direction.HORIZONTAL,
                Block.Shape.LARGE
        );
        assertEquals(Color.GREEN, block.getColor(), "Il colore dovrebbe essere memorizzato correttamente");
    }
}


