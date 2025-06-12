package it.decorator;

import it.composite.GameComponent;

import java.awt.*;

/**
 * Classe astratta base per i decoratori di blocchi. Implementa GameComponent e
 * delega le operazioni al componente decorato.
 */
public abstract class BlockDecorator implements GameComponent {

    protected GameComponent decoratedBlock;

    /**
     * Costruttore che accetta un componente da decorare.
     *
     * @param decoratedBlock il componente da decorare
     */
    public BlockDecorator(GameComponent decoratedBlock) {
        this.decoratedBlock = decoratedBlock;
    }

    @Override
    public void draw(Graphics g) {
        decoratedBlock.draw(g);
    }

    @Override
    public void move(int dx, int dy) {
        decoratedBlock.move(dx, dy);
    }

    @Override
    public Rectangle getBounds() {
        return decoratedBlock.getBounds();
    }
}



