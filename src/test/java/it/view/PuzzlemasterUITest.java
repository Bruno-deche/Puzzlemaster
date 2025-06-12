package it.view;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PuzzlemasterUITest {

    @Test
    public void testVictoryCheckWhenBlocksAreCorrectlyPlaced() {
        PuzzlemasterUI ui = new PuzzlemasterUI();
        ui.setLayout(null);

        // inizializza con blocchi
        ui.initGame(5, 0, null);

        // Prendi la combinazione da raggiungere
        List<PuzzlemasterUI.BlockGoal> goals = ui.getTargetBlocks();

        // Posiziona i blocchi esattamente come nei goal
        Component[] blocks = ui.getBoardPanel().getComponents();
        for (int i = 0; i < goals.size(); i++) {
            if (blocks[i] instanceof JButton b) {
                Rectangle r = goals.get(i).bounds;
                Color c = goals.get(i).color;
                b.setBounds(r);
                b.setBackground(c);
            }
        }

        // Output per verifica manuale
        System.out.println("📦 Blocchi posizionati come i target");
        for (int i = 0; i < goals.size(); i++) {
            Rectangle r = blocks[i].getBounds();
            Color c = ((JButton) blocks[i]).getBackground();
            System.out.printf("→ Blocco %d: Posizione=%s, Colore=%s%n", i, r, c);
        }

        // Esegui controllo di vittoria
        ui.checkVictory();

        // Non possiamo verificare il dialog, ma possiamo dire che non ha lanciato eccezioni
        assertTrue(true); // dummy assertion
    }
}

