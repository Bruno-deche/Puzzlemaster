package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class SaveCommandTest {

    @Test
    public void testSaveCommandTriggersModelSave() {
        final boolean[] called = {false};

        // Fake model che intercetta la chiamata reale
        PuzzlemasterModel fakeModel = new PuzzlemasterModel() {
            @Override
            public void saveGameToDB(String state, int moves, String level, String name) {
                called[0] = true;
                assertNotNull(state);
                assertTrue(state.contains("["), "Lo stato dovrebbe contenere coordinate");
                assertEquals(0, moves); // counter predefinito
                assertEquals("easy", level); // default se level = 1
                assertEquals("default_name", name);
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getCounter() {
                return 0;
            }
        };

        // Fake UI che restituisce nome e posizione blocchi
        PuzzlemasterUI fakeUI = new PuzzlemasterUI() {
            public String askGameName() {
                return "default_name";
            }

            @Override
            public Rectangle[] getCurrentBlockPositions() {
                return new Rectangle[]{
                        new Rectangle(0, 0, 80, 80),
                        new Rectangle(100, 0, 80, 80)
                };
            }

            @Override
            public void showMessage(String msg, String title, int type) {
                // silenzioso
            }
        };

        SaveCommand command = new SaveCommand(fakeModel, fakeUI);
        command.mousePressed(null);

        assertTrue(called[0], "Il metodo saveGameToDB del model dovrebbe essere chiamato");
    }
}


