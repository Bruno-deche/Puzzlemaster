package it.view;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Pannello grafico che rappresenta la board di gioco e visualizza la combinazione da raggiungere.
 */
public class BoardPanel extends JPanel {
    private static final Logger logger = Logger.getLogger(BoardPanel.class.getName());
    private List<Color> targetCombination;
    private List<PuzzlemasterUI.BlockGoal> targetBlocks = new ArrayList<>();

    /**
     * Costruttore del BoardPanel.
     *
     * @param positions le posizioni iniziali dei blocchi
     * @param colors    i colori iniziali dei blocchi
     * @param listener  il listener per la gestione degli eventi mouse
     */
    public BoardPanel(List<Point> positions, Color[] colors, MouseInputListener listener) {
        setLayout(null);
    }

    /**
     * Imposta la combinazione bersaglio da visualizzare.
     *
     * @param combination lista di colori che rappresentano la combinazione da raggiungere
     */
    public void setTargetCombination(List<Color> combination) {
        this.targetCombination = combination;
        repaint();
    }

    /**
     * Restituisce la lista dei blocchi bersaglio, utile per verificare la condizione di vittoria.
     *
     * @return lista di oggetti BlockGoal
     */
    public List<PuzzlemasterUI.BlockGoal> getTargetBlocks() {
        return targetBlocks;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (targetCombination != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            int blockSize = 80;
            int spacing = 10;
            int count = targetCombination.size();
            int totalWidth = count * (blockSize + spacing) - spacing;

            int startX = (getWidth() - totalWidth) / 2;
            int y = getHeight() - blockSize - 20;

            for (int i = 0; i < count; i++) {
                Color c = targetCombination.get(i);
                int x = startX + i * (blockSize + spacing);

                g2.setColor(c);
                g2.fillRect(x, y, blockSize, blockSize);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(x, y, blockSize, blockSize);
            }

            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
            g2.drawString("Combinazione da raggiungere", startX, y - 8);

            g2.dispose();
            logger.info("🎯 BoardPanel sta disegnando!");
        }
    }
}







