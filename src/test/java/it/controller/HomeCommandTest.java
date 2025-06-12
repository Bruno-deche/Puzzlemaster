package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeCommandTest {

    @Test
    public void testHomeCommandSavesAndReturnsHome() {
        final boolean[] savePressed = {false};
        final boolean[] uiReset = {false};

        // Fake model
        PuzzlemasterModel fakeModel = new PuzzlemasterModel();

        // Fake UI che ritorna un nome valido
        PuzzlemasterUI fakeUI = new PuzzlemasterUI() {
            public String askGameName() {
                return "default_name"; // simulazione input
            }

            @Override
            public void initStart() {
                uiReset[0] = true;
            }
        };

        // SaveCommand con tracciamento
        SaveCommand trackedSaveCommand = new SaveCommand(fakeModel, fakeUI) {
            @Override
            public void mousePressed(MouseEvent e) {
                savePressed[0] = true;
            }
        };

        // Comando da testare
        HomeCommand command = new HomeCommand(fakeModel, fakeUI, trackedSaveCommand);
        command.mousePressed(null);

        assertTrue(savePressed[0], "saveCommand.mousePressed() dovrebbe essere stato chiamato");
        assertTrue(uiReset[0], "La UI dovrebbe essere tornata alla schermata iniziale");
    }
}








