package it.controller;

import it.view.PuzzlemasterUI;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Listener che gestisce il trascinamento dei blocchi con collision detection.
 * Impedisce il movimento fuori dai bordi e la sovrapposizione con altri blocchi.
 */
public class CollisionBlockListener implements MouseInputListener {

    private final JPanel boardPanel;
    private final PuzzlemasterUI ui;
    private JButton selectedBlock;
    private Point initialClick;
    private Direction allowedDirection = Direction.NONE;

    /**
     * Enum che rappresenta la direzione consentita di movimento.
     */
    public enum Direction {
        NONE, HORIZONTAL, VERTICAL
    }

    /**
     * Costruttore del listener.
     *
     * @param boardPanel pannello su cui si trovano i blocchi
     * @param ui         interfaccia grafica del gioco
     */
    public CollisionBlockListener(JPanel boardPanel, PuzzlemasterUI ui) {
        this.boardPanel = boardPanel;
        this.ui = ui;
    }

    /**
     * Reimposta lo stato del listener (selezione, punto iniziale e direzione).
     */
    public void resetState() {
        selectedBlock = null;
        initialClick = null;
        allowedDirection = Direction.NONE;
    }

    /**
     * Memorizza il blocco selezionato e il punto iniziale del click.
     *
     * @param e evento di pressione del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Component source = e.getComponent();
        if (source instanceof JButton) {
            selectedBlock = (JButton) source;
            initialClick = SwingUtilities.convertPoint(selectedBlock, e.getPoint(), boardPanel);
            allowedDirection = Direction.NONE;
        }
    }

    /**
     * Gestisce il movimento del blocco selezionato con collision detection.
     *
     * @param e evento di trascinamento del mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedBlock == null || initialClick == null) return;

        Point draggedPoint = SwingUtilities.convertPoint(selectedBlock, e.getPoint(), boardPanel);
        int dx = draggedPoint.x - initialClick.x;
        int dy = draggedPoint.y - initialClick.y;

        // Blocca la direzione solo la prima volta
        if (allowedDirection == Direction.NONE) {
            allowedDirection = (Math.abs(dx) > Math.abs(dy)) ? Direction.HORIZONTAL : Direction.VERTICAL;
        }

        // Annulla il movimento non consentito
        if (allowedDirection == Direction.HORIZONTAL) dy = 0;
        if (allowedDirection == Direction.VERTICAL) dx = 0;

        int stepX = Integer.signum(dx);
        int stepY = Integer.signum(dy);
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        Point currentPos = selectedBlock.getLocation();

        for (int i = 0; i < steps; i++) {
            Rectangle nextBounds = new Rectangle(
                    currentPos.x + stepX,
                    currentPos.y + stepY,
                    selectedBlock.getWidth(),
                    selectedBlock.getHeight()
            );

            // Blocca se fuori dai bordi
            if (nextBounds.x < 0 || nextBounds.y < 0 ||
                    nextBounds.x + nextBounds.width > boardPanel.getWidth() ||
                    nextBounds.y + nextBounds.height > boardPanel.getHeight()) {
                break;
            }

            // Blocca se collide con altri blocchi
            boolean collision = false;
            for (Component comp : boardPanel.getComponents()) {
                if (comp instanceof JButton block && block != selectedBlock) {
                    if (nextBounds.intersects(block.getBounds())) {
                        collision = true;
                        break;
                    }
                }
            }

            if (collision) break;

            // Muove il blocco di un pixel
            currentPos.translate(stepX, stepY);
            selectedBlock.setLocation(currentPos);
        }

        // Aggiorna il punto iniziale per il prossimo movimento
        initialClick = draggedPoint;
    }

    /**
     * Reimposta lo stato al rilascio del mouse.
     *
     * @param e evento di rilascio del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        resetState();
    }

    /**
     * Metodo non implementato per il movimento semplice del mouse.
     *
     * @param e evento di movimento del mouse
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Metodo non implementato per il clic completo del mouse.
     *
     * @param e evento di clic del mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Metodo non implementato per ingresso del mouse.
     *
     * @param e evento di ingresso del mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metodo non implementato per uscita del mouse.
     *
     * @param e evento di uscita del mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}




