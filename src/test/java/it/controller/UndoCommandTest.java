package it.controller;

import it.model.Move;
import it.model.MoveFactory;
import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UndoCommandTest {

    @Test
    public void testUndoCommandCallsModelAndUI() {
        // Variabile per verificare se la UI ha applicato la move
        boolean[] moveApplied = {false};

        // modello finto che restituisce una Move valida
        PuzzlemasterModel fakeModel = new PuzzlemasterModel() {
            @Override
            public Move undo() {
                return MoveFactory.createMove("user", new Point(20, 0), new Point(0, 0));
            }
        };

        // UI finta che intercetta la chiamata
        PuzzlemasterUI fakeUI = new PuzzlemasterUI() {
            @Override
            public void makeMove(Move m, int counter) {
                moveApplied[0] = true;
                assertEquals(new Point(0, 0), m.getTo(), "Destinazione sbagliata");
                assertEquals(new Point(20, 0), m.getFrom(), "Origine sbagliata");
            }
        };

        UndoCommand command = new UndoCommand(fakeModel, fakeUI);
        command.mousePressed(null); // simula click

        assertTrue(moveApplied[0], "La UI dovrebbe essere aggiornata con la mossa annullata");
    }
}


