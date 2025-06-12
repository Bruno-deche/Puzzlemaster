package test;

import it.model.GamePersistenceException;
import it.model.Move;
import it.model.PuzzlemasterModel;
import it.strategy.EasyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PuzzlemasterModelTest {

    private PuzzlemasterModel model;

    @BeforeEach
    public void setUp() {
        model = new PuzzlemasterModel();
        model.setStrategy(new EasyStrategy());  // ✅ Qui va bene
    }

    @Test
    public void testMakeMoveIncrementsCounter() {
        int initial = model.getCounter();
        model.makeMove(new Point(0, 0), new Point(20, 0));
        assertEquals(initial + 1, model.getCounter());
    }

    @Test
    public void testUndoDecrementsCounter() {
        model.makeMove(new Point(0, 0), new Point(20, 0));
        int afterMove = model.getCounter();

        model.undo();  // dovrebbe annullare la mossa fatta sopra

        assertEquals(afterMove - 1, model.getCounter(), "Undo deve decrementare il contatore di 1");
    }

    @Test
    public void testNextBestMoveIsNotNull() {
        PuzzlemasterModel testModel = new PuzzlemasterModel() {
            @Override
            public Rectangle[] getCurrentPositions() {
                return new Rectangle[]{
                        new Rectangle(0, 0, 80, 80)
                };
            }
        };

        testModel.setStrategy(new EasyStrategy());

        assertNotNull(testModel.nextBestMove(), "nextBestMove() non deve restituire null");
    }

    @Test
    public void testGetMoveIteratorReturnsCorrectSequence() {
        model.makeMove(new Point(0, 0), new Point(20, 0));
        model.makeMove(new Point(20, 0), new Point(40, 0));

        Iterator<Move> iterator = model.getMoveIterator();

        assertTrue(iterator.hasNext(), "L'iteratore dovrebbe avere almeno un elemento");

        Move first = iterator.next();
        assertEquals(new Point(0, 0), first.getFrom());
        assertEquals(new Point(20, 0), first.getTo());

        Move second = iterator.next();
        assertEquals(new Point(20, 0), second.getFrom());
        assertEquals(new Point(40, 0), second.getTo());
    }

    @Test
    public void testSetAndGetSelectedPiece() {
        Point p = new Point(100, 200);
        model.setSelectedPiece(p);

        Point result = model.getSelectedPiece();
        assertEquals(p, result, "getSelectedPiece() dovrebbe restituire il punto impostato con setSelectedPiece()");
    }

    @Test
    public void testSaveGameToDBDoesNotThrow() {
        assertDoesNotThrow(() -> {
            String state = "0000010000001111110000000";
            int moves = 5;
            String level = "Facile";
            model.saveGameToDB(state, moves, level, "test_name");
        }, "saveGameToDB dovrebbe eseguirsi senza lanciare eccezioni");
    }

    @Test
    public void testUndoWithEmptyHistoryReturnsNull() {
        model = new PuzzlemasterModel(); // inizializza modello senza mosse
        Move result = model.undo();
        assertNull(result, "Se non ci sono mosse da annullare, undo() deve restituire null");
    }

    @Test
    public void testUndoReturnsCorrectInvertedMove() {
        model = new PuzzlemasterModel();
        Point from = new Point(0, 0);
        Point to = new Point(20, 0);

        model.makeMove(from, to);
        int afterMove = model.getCounter();

        Move undone = model.undo();

        assertEquals(afterMove - 1, model.getCounter(), "Il contatore dovrebbe essere decrementato dopo undo()");
        assertEquals(to, undone.getFrom(), "Il punto di partenza della mossa annullata deve essere il 'to' originale");
        assertEquals(from, undone.getTo(), "Il punto di destinazione della mossa annullata deve essere il 'from' originale");
    }


    @Test
    public void testGetSavedGameListThrowsIfFolderMissing() {
        PuzzlemasterModel model = new PuzzlemasterModel();

        File folder = new File("saves");
        if (folder.exists()) {
            folder.renameTo(new File("saves_backup")); // salva temporaneamente
        }

        Exception exception = assertThrows(GamePersistenceException.class, model::getSavedGameList);
        assertTrue(exception.getMessage().contains("Cartella salvataggi non trovata."));

        // ripristina la cartella se era stata rinominata
        File backup = new File("saves_backup");
        if (backup.exists()) {
            backup.renameTo(new File("saves"));
        }
    }

    @Test
    public void testNextBestMoveWithNullInput() {
        PuzzlemasterModel model = new PuzzlemasterModel() {
            @Override
            public Rectangle[] getCurrentPositions() {
                return null;
            }
        };

        assertNull(model.nextBestMove(), "nextBestMove() dovrebbe restituire null se input è null");
    }

    @Test
    public void testResumeStateThrowsExceptionForMissingFile() {
        assertThrows(GamePersistenceException.class, () -> {
            model.resumeState("nonEsistente");
        }, "Dovrebbe lanciare GamePersistenceException se il salvataggio non esiste");
    }

    @Test
    public void testGetSavedGameListWithMissingOrEmptyFolder() {
        File folder = new File("saves");
        File backup = new File("saves_backup");

        // Rinomina la cartella se esiste per simularne l'assenza
        if (folder.exists()) {
            folder.renameTo(backup);
        }

        assertThrows(GamePersistenceException.class, () -> model.getSavedGameList());

        // Ripristina la cartella originale se era stata rinominata
        if (backup.exists()) {
            backup.renameTo(folder);
        }
    }

    @Test
    public void testMoveIteratorIteratesCorrectly() {
        Point from = new Point(0, 0);
        Point to = new Point(20, 0);
        model.makeMove(from, to);
        model.makeMove(new Point(20, 0), new Point(40, 0));

        Iterator<Move> iterator = model.getMoveIterator();
        List<Move> moves = new ArrayList<>();
        while (iterator.hasNext()) {
            moves.add(iterator.next());
        }

        assertEquals(2, moves.size());
        assertEquals(from, moves.get(0).getFrom());
        assertEquals(to, moves.get(0).getTo());
    }

    @Test
    public void testSendGameToServerDoesNotThrow() {
        PuzzlemasterModel puzzlemasterModel = new PuzzlemasterModel();
        assertDoesNotThrow(() -> {
            puzzlemasterModel.sendGameToServer("XOXOXOXO", 10, "facile");
        }, "L'invio al server non dovrebbe lanciare eccezioni");
    }

    @Test
    public void testNextBestMoveWithNullInputDoesNotThrow() {
        PuzzlemasterModel model = new PuzzlemasterModel();
        model.setStrategy(new EasyStrategy());  // o Random/Targeted
        assertDoesNotThrow(() -> {
            Move move = model.nextBestMove();  // internamente chiama getCurrentPositions()
            // Se serve puoi anche fare assertNull(move);
        }, "La strategia non dovrebbe lanciare eccezione con input nullo o vuoto");
    }

}



