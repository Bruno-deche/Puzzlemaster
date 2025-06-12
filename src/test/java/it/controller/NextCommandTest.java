package it.controller;

import it.model.Move;
import it.model.MoveFactory;
import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NextCommandTest {

    @Test
    public void testNextCommandCallsNextBestMoveAndAppliesIt() {
        boolean[] moveApplied = {false};

        PuzzlemasterModel fakeModel = new PuzzlemasterModel() {
            @Override
            public Move nextBestMove() {
                return MoveFactory.createMove("hint", new Point(0, 0), new Point(20, 0));
            }

            @Override
            public int getCounter() {
                return 42;
            }
        };

        PuzzlemasterUI fakeUI = new PuzzlemasterUI() {
            @Override
            public void makeMove(Move m, int counter) {
                moveApplied[0] = true;
                assertEquals(42, counter);
                assertEquals(new Point(0, 0), m.getFrom());
                assertEquals(new Point(20, 0), m.getTo());
            }
        };

        NextCommand command = new NextCommand(fakeModel, fakeUI);
        command.mousePressed(null);

        assertTrue(moveApplied[0], "La UI dovrebbe applicare la mossa suggerita");
    }
}

