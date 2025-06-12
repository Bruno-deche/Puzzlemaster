package it.composite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta la board di gioco composta da più GameComponent (es. blocchi).
 * Permette di aggiungere, rimuovere e gestire collisioni tra i componenti.
 */
public class Board {

    private final List<GameComponent> components;

    /**
     * Costruisce una nuova board inizializzando la lista dei componenti.
     */
    public Board() {
        components = new ArrayList<>();
    }

    /**
     * Aggiunge un componente alla board.
     *
     * @param component il GameComponent da aggiungere
     */
    public void add(GameComponent component) {
        components.add(component);
    }

    /**
     * Rimuove un componente dalla board.
     *
     * @param component il GameComponent da rimuovere
     */
    public void remove(GameComponent component) {
        components.remove(component);
    }

    /**
     * Restituisce la lista dei componenti attualmente presenti nella board.
     *
     * @return lista dei GameComponent
     */
    public List<GameComponent> getChildren() {
        return components;
    }

    /**
     * Metodo alias per getChildren(), usato per compatibilità con i test.
     *
     * @return lista dei GameComponent
     */
    public List<GameComponent> getComponents() {
        return getChildren();
    }

    /**
     * Rimuove tutti i componenti dalla board.
     */
    public void clear() {
        components.clear();
    }

    /**
     * Restituisce il componente che contiene il punto specificato.
     *
     * @param p punto da verificare
     * @return GameComponent che contiene il punto, oppure null se nessuno
     */
    public GameComponent getComponentAt(Point p) {
        for (GameComponent component : components) {
            if (component.getBounds().contains(p)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Verifica se il nuovo posizionamento di un componente causa collisioni con altri.
     *
     * @param moving     componente in movimento
     * @param newBounds  nuovi limiti da testare
     * @return true se c'è collisione, false altrimenti
     */
    public boolean checkCollision(GameComponent moving, Rectangle newBounds) {
        for (GameComponent component : components) {
            if (component != moving && component.getBounds().intersects(newBounds)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo segnaposto per restituire il componente Swing associato al GameComponent.
     * Va implementato nel contesto della UI con associazione tra JButton e Block.
     *
     * @param block componente logico di tipo Block
     * @return componente Swing associato, oppure null se non gestito
     */
    public Component getSwingComponent(GameComponent block) {
        return null;
    }
}









