package it.model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveIteratorTest {

    @Test
    public void testIteratorReturnsMovesInOrder() {
        PuzzlemasterModel model = new PuzzlemasterModel();

        model.makeMove(new Point(0, 0), new Point(20, 0));
        model.makeMove(new Point(20, 0), new Point(40, 0));
        model.makeMove(new Point(40, 0), new Point(60, 0));

        List<Move> expected = model.getUserMoves(); // contiene tutte le mosse fatte
        Iterator<Move> iterator = model.getMoveIterator();

        int index = 0;
        while (iterator.hasNext()) {
            Move move = iterator.next();
            assertEquals(expected.get(index).getFrom(), move.getFrom());
            assertEquals(expected.get(index).getTo(), move.getTo());
            index++;
        }

        assertEquals(expected.size(), index);
    }
}
