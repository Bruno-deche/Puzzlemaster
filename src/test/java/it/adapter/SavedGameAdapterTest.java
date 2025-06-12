package it.adapter;

import it.composite.GameComponent;
import it.model.SavedGame;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavedGameAdapterTest {

    @Test
    public void testAdapterCreatesCorrectNumberOfBlocks() {
        int[][] moves = {
                {0, 0, 80, 80},
                {100, 100, 80, 80}
        };
        SavedGame game = new SavedGame("test", moves, 3);
        SavedGameAdapter adapter = new SavedGameAdapter(game);

        List<GameComponent> blocks = adapter.getBoard().getComponents();

        assertEquals(2, blocks.size(), "Dovrebbero esserci 2 blocchi creati");
    }
}


