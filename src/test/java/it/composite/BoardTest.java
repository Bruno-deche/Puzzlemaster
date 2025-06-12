package it.composite;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest {

    @Test
    public void testAddAndGetComponents() {
        Board board = new Board();
        Block block = new Block(
                new Rectangle(0, 0, 80, 80),
                Color.BLUE,
                Block.Direction.HORIZONTAL,
                Block.Shape.SMALL
        );

        board.add(block);

        List<GameComponent> components = board.getComponents();

        assertEquals(1, components.size(), "La board dovrebbe contenere 1 componente");
        assertSame(block, components.get(0), "Il blocco aggiunto dovrebbe essere il primo elemento nella board");
    }

    @Test
    public void testClearRemovesAllComponents() {
        Board board = new Board();
        Block b1 = new Block(
                new Rectangle(0, 0, 80, 80),
                Color.BLUE,
                Block.Direction.HORIZONTAL,
                Block.Shape.SMALL
        );
        Block b2 = new Block(
                new Rectangle(100, 0, 80, 80),
                Color.RED,
                Block.Direction.VERTICAL,
                Block.Shape.MEDIUM
        );

        board.add(b1);
        board.add(b2);

        assertEquals(2, board.getComponents().size(), "La board dovrebbe contenere 2 componenti");

        board.clear();

        assertEquals(0, board.getComponents().size(), "La board dovrebbe essere vuota dopo clear()");
    }

    @Test
    public void testBlockMoveUpdatesPosition() {
        Rectangle r = new Rectangle(100, 200, 80, 80);
        Block block = new Block(
                r,
                Color.GREEN,
                Block.Direction.HORIZONTAL,
                Block.Shape.LARGE
        );

        block.move(20, -50);

        Rectangle updated = block.getBounds();
        assertEquals(120, updated.x, "La nuova x dovrebbe essere 120");
        assertEquals(150, updated.y, "La nuova y dovrebbe essere 150");
    }
}


