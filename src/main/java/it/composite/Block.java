package it.composite;

import java.awt.*;

/**
 * Rappresenta un blocco posizionabile all'interno della board di gioco.
 * Implementa l'interfaccia GameComponent per essere disegnato graficamente.
 */
public class Block implements GameComponent {

    private Rectangle bounds;
    private Color color;
    private Direction direction;
    private Shape shape;

    /**
     * Costruisce un nuovo blocco con le proprietà specificate.
     *
     * @param bounds     l'area occupata dal blocco
     * @param color      il colore del blocco
     * @param direction  la direzione consentita per il movimento
     * @param shape      la forma logica del blocco
     */
    public Block(Rectangle bounds, Color color, Direction direction, Shape shape) {
        this.bounds = bounds;
        this.color = color;
        this.direction = direction;
        this.shape = shape;
    }

    /**
     * Muove il blocco secondo la sua direzione: orizzontale o verticale.
     *
     * @param dx spostamento orizzontale (ignorato se direzione è VERTICALE)
     * @param dy spostamento verticale (ignorato se direzione è ORIZZONTALE)
     */
    public void move(int dx, int dy) {
        if (direction == Direction.HORIZONTAL) {
            bounds.translate(dx, 0);
        } else if (direction == Direction.VERTICAL) {
            bounds.translate(0, dy);
        }
    }

    /**
     * Disegna il blocco su un componente grafico usando Graphics.
     *
     * @param g oggetto Graphics su cui disegnare
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * Restituisce i limiti rettangolari del blocco.
     *
     * @return i bounds del blocco
     */
    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Imposta nuovi limiti rettangolari per il blocco.
     *
     * @param bounds nuovi bounds da assegnare
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds);
    }

    /**
     * Restituisce il colore del blocco.
     *
     * @return colore del blocco
     */
    public Color getColor() {
        return color;
    }

    /**
     * Imposta un nuovo colore al blocco.
     *
     * @param color colore da assegnare
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Restituisce la direzione di movimento del blocco.
     *
     * @return direzione corrente del blocco
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Imposta la direzione consentita per il movimento del blocco.
     *
     * @param direction nuova direzione da impostare
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Restituisce la forma logica del blocco.
     *
     * @return forma del blocco
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Imposta una nuova posizione per il blocco.
     *
     * @param x nuova coordinata X
     * @param y nuova coordinata Y
     */
    public void setPosition(int x, int y) {
        bounds.setLocation(x, y);
    }

    /**
     * Enum che definisce le direzioni consentite per il movimento del blocco.
     */
    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Enum che definisce le possibili forme del blocco.
     */
    public enum Shape {
        SMALL, MEDIUM, LARGE
    }
}









