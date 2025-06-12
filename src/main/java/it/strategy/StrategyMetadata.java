package it.strategy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotazione personalizzata per descrivere le strategie disponibili.
 * Contiene metadati sulla difficoltà e una breve descrizione.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrategyMetadata {

    /**
     * Descrive il livello di difficoltà (es. Facile, Medio, Difficile).
     *
     * @return una stringa che rappresenta la difficoltà
     */
    String difficulty();

    /**
     * Testo descrittivo che spiega il comportamento della strategia.
     *
     * @return una stringa con la descrizione
     */
    String description();
}


