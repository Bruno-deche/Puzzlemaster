package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestartCommandTest {

    @Test
    public void testRestartCommandTriggersModelReset() {
        final boolean[] restarted = {false};

        // modello finto che intercetta restartState()
        PuzzlemasterModel fakeModel = new PuzzlemasterModel() {
            @Override
            public void restartState() {
                restarted[0] = true;
            }
        };

        // UI finta (non serve fare nulla qui)
        PuzzlemasterUI fakeUI = new PuzzlemasterUI();

        RestartCommand command = new RestartCommand(fakeModel, fakeUI);
        command.mousePressed(null);

        assertTrue(restarted[0], "Il metodo restartState() dovrebbe essere chiamato dal comando");
    }
}

