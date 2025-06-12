package it.adapter;

import it.composite.Block;
import it.composite.Board;
import it.model.SavedGame;

import java.awt.*;
import java.util.logging.Logger;

public class SavedGameAdapter {

    private static final Logger logger = Logger.getLogger(SavedGameAdapter.class.getName());
    private static final Color[] PALETTE = {
            new Color(66, 135, 245),
            new Color(235, 64, 52),
            new Color(150, 150, 150),
            new Color(40, 200, 100),
            new Color(150, 100, 255)
    };
    private final Board board;

    public SavedGameAdapter(SavedGame game) {
        this.board = new Board();

        int[][] moves = game.getMoves();
        if (moves == null) {
            logger.severe("⚠️ Errore: l'array delle mosse è nullo.");
            throw new IllegalArgumentException("L'array delle mosse non può essere nullo.");
        }

        logger.info("📦 Caricamento del gioco con " + moves.length + " mosse.");
        for (int i = 0; i < moves.length; i++) {
            int x = moves[i][0];
            int y = moves[i][1];
            int w = moves[i][2];
            int h = moves[i][3];

            // Assegna un colore ciclico e la direzione basata su larghezza/altezza
            Color color = getColorByIndex(i);
            Block.Direction direction = (w > h) ? Block.Direction.HORIZONTAL : Block.Direction.VERTICAL;
            Block.Shape shape = Block.Shape.MEDIUM;

            // Aggiungiamo il blocco alla board
            try {
                Block block = new Block(new Rectangle(x, y, w, h), color, direction, shape);
                board.add(block);
                logger.info("✅ Blocco aggiunto: " + block.toString());
            } catch (Exception e) {
                logger.severe("❌ Errore durante la creazione del blocco: " + e.getMessage());
            }
        }
    }

    /**
     * Restituisce la board generata a partire dai dati salvati del gioco.
     *
     * @return l'istanza di Board contenente tutti i blocchi ricostruiti
     */
    public Board getBoard() {
        return board;
    }

    private Color getColorByIndex(int i) {
        return PALETTE[i % PALETTE.length];
    }
}











