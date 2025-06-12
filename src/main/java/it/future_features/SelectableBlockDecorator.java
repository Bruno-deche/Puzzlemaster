package it.future_features;

import it.composite.GameComponent;
import it.decorator.BlockDecorator;

import java.awt.*;

/**
 * Decoratore che aggiunge la funzionalità di selezione visiva a un GameComponent.
 * Quando selezionato, il blocco viene evidenziato con un bordo giallo.
 */
public class SelectableBlockDecorator extends BlockDecorator {

    private boolean selected;

    /**
     * Costruisce un decoratore selezionabile attorno al GameComponent fornito.
     *
     * @param decoratedBlock il blocco da decorare
     */
    public SelectableBlockDecorator(GameComponent decoratedBlock) {
        super(decoratedBlock);
        this.selected = false;
    }

    /**
     * Verifica se il blocco è attualmente selezionato.
     *
     * @return true se selezionato, false altrimenti
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Imposta lo stato di selezione del blocco.
     *
     * @param selected true per selezionare, false per deselezionare
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Disegna il blocco decorato e, se selezionato, evidenzia con bordo giallo.
     *
     * @param g oggetto Graphics usato per il rendering
     */
    @Override
    public void draw(Graphics g) {
        decoratedBlock.draw(g);

        if (selected && decoratedBlock.getBounds() != null) {
            Rectangle r = decoratedBlock.getBounds();
            g.setColor(Color.YELLOW);
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }

    /**
     * Muove il blocco decorato di un certo offset.
     *
     * @param dx spostamento orizzontale
     * @param dy spostamento verticale
     */
    @Override
    public void move(int dx, int dy) {
        decoratedBlock.move(dx, dy);
    }

    /**
     * Restituisce i limiti del blocco decorato.
     *
     * @return bounds del blocco
     */
    @Override
    public Rectangle getBounds() {
        return decoratedBlock.getBounds();
    }
}



