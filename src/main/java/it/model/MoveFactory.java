package it.model;

import java.awt.*;

/**
 * Factory per la creazione di oggetti {@link Move}.
 * Fornisce un metodo statico per creare mosse standard con controllo di validità.
 */
public class MoveFactory {

    /**
     * Crea un oggetto {@link Move} del tipo specificato.
     *
     * @param type tipo della mossa (es. "auto", "manual", ecc.)
     * @param from punto di partenza
     * @param to   punto di destinazione
     * @return un'istanza concreta di {@link Move}, attualmente {@link StandardMove}
     * @throws IllegalArgumentException se i parametri sono nulli, uguali o il tipo è mancante
     */
    public static Move createMove(String type, Point from, Point to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("❌ from o to è null");
        }

        if (from.equals(to)) {
            throw new IllegalArgumentException("❌ FROM e TO sono uguali: " + from);
        }

        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("❌ Tipo di mossa non specificato");
        }

        // ✅ Restituiamo una StandardMove concreta
        return new StandardMove(type, from, to);
    }
}



