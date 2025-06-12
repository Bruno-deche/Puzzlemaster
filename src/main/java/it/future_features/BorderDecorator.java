package it.future_features;

import it.composite.GameComponent;
import it.decorator.BlockDecorator;

import java.awt.*;

/**
 * Decoratore che aggiunge un bordo attorno al blocco disegnato.
 */
public class BorderDecorator extends BlockDecorator {

    private final Color borderColor;

    /**
     * Costruttore che accetta il blocco da decorare e il colore del bordo.
     *
     * @param decoratedBlock il blocco da decorare
     * @param borderColor    il colore del bordo
     */
    public BorderDecorator(GameComponent decoratedBlock, Color borderColor) {
        super(decoratedBlock);
        this.borderColor = borderColor;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        Rectangle r = decoratedBlock.getBounds();
        g.setColor(borderColor);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}



