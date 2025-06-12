package it.controller;

import it.composite.Block;
import it.composite.Board;
import it.composite.GameComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listener che gestisce il trascinamento dei blocchi con il mouse all'interno della board.
 */
public class BlockListener extends MouseAdapter {

    private final JPanel boardPanel;
    private final Board board;
    private Block selectedBlock;
    private Point initialClick;

    /**
     * Costruttore che inizializza il listener con la board e il pannello grafico.
     *
     * @param boardPanel il pannello della board su cui avviene il disegno
     * @param board      la board logica che contiene i blocchi
     */
    public BlockListener(JPanel boardPanel, Board board) {
        if (boardPanel == null || board == null)
            throw new IllegalArgumentException("Board e boardPanel non possono essere null");
        this.boardPanel = boardPanel;
        this.board = board;
    }

    /**
     * Identifica il blocco selezionato quando si preme il mouse su di esso.
     *
     * @param e evento di pressione del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point click = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), boardPanel);
        GameComponent component = board.getComponentAt(click);
        if (component instanceof Block block) {
            selectedBlock = block;
            initialClick = click;
        }
    }

    /**
     * Trascina il blocco selezionato nella direzione consentita, se non ci sono collisioni.
     *
     * @param e evento di trascinamento del mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedBlock == null || initialClick == null) return;

        Point currentPoint = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), boardPanel);
        int dx = currentPoint.x - initialClick.x;
        int dy = currentPoint.y - initialClick.y;

        Rectangle currentBounds = selectedBlock.getBounds();
        Rectangle testBounds = new Rectangle(currentBounds);

        int stepX = Integer.signum(dx);
        int stepY = Integer.signum(dy);

        boolean moved = false;

        if (selectedBlock.getDirection() == Block.Direction.HORIZONTAL) {
            int distance = Math.abs(dx);
            for (int i = 0; i < distance; i++) {
                testBounds.setLocation(testBounds.x + stepX, testBounds.y);
                if (board.checkCollision(selectedBlock, testBounds)) break;
                selectedBlock.move(stepX, 0);
                updateButtonPosition();
                moved = true;
            }
        } else if (selectedBlock.getDirection() == Block.Direction.VERTICAL) {
            int distance = Math.abs(dy);
            for (int i = 0; i < distance; i++) {
                testBounds.setLocation(testBounds.x, testBounds.y + stepY);
                if (board.checkCollision(selectedBlock, testBounds)) break;
                selectedBlock.move(0, stepY);
                updateButtonPosition();
                moved = true;
            }
        }

        if (moved) {
            boardPanel.repaint();
            initialClick = currentPoint;
        }
    }

    /**
     * Reimposta lo stato di selezione al rilascio del mouse.
     *
     * @param e evento di rilascio del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        selectedBlock = null;
        initialClick = null;
    }

    private void updateButtonPosition() {
        Component button = board.getSwingComponent(selectedBlock);
        if (button != null) {
            button.setBounds(selectedBlock.getBounds());
        }
    }
}

















