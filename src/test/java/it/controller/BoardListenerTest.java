package it.controller;

import it.model.Move;
import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardListenerTest {

    @Test
    public void testMakeMoveCalledOnMousePress() {
        final boolean[] moveCalled = {false};

        // Fake model che intercetta makeMove
        PuzzlemasterModel fakeModel = new PuzzlemasterModel() {
            @Override
            public Point getSelectedPiece() {
                return new Point(0, 0);  // blocco selezionato iniziale
            }

            @Override
            public void makeMove(Point from, Point to) {
                moveCalled[0] = true;
                assertEquals(new Point(0, 0), from);     // origine corretta
                assertEquals(new Point(20, 0), to);      // destinazione cliccata
            }

            @Override
            public int getCounter() {
                return 1; // simulazione
            }
        };

        // Fake UI che controlla la mossa ricevuta
        PuzzlemasterUI fakeUI = new PuzzlemasterUI() {
            @Override
            public void makeMove(Move m, int counter) {
                assertEquals(new Point(0, 0), m.getFrom());
                assertEquals(new Point(20, 0), m.getTo());
                assertEquals(1, counter);
            }
        };

        BoardListener listener = new BoardListener(fakeModel, fakeUI);

        // Simula un click su (20, 0)
        Component dummyComponent = new Button();
        MouseEvent event = new MouseEvent(dummyComponent, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
                0, 20, 0, 1, false);

        listener.mousePressed(event);

        assertTrue(moveCalled[0], "Il metodo makeMove dovrebbe essere stato chiamato");
    }
}



