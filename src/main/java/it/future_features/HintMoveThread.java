package it.future_features;

import it.model.Move;
import it.model.PuzzlemasterModel;
import it.util.GameLogger;

import java.util.logging.Logger;

/**
 * {@code HintMoveThread} è un thread dedicato al calcolo della mossa
 * suggerita (hint) da parte del solver, in modo asincrono.
 *
 * <p>Questo consente di eseguire l'elaborazione senza bloccare l'interfaccia utente,
 * migliorando la reattività dell'applicazione. La mossa viene solo loggata,
 * senza modificare la logica di gioco o l'interfaccia grafica.</p>
 *
 * @author Bruno Checchi
 */
public class HintMoveThread extends Thread {

    /** Riferimento al modello principale del gioco. */
    private final PuzzlemasterModel model;

    /** Logger condiviso per tracciare attività del thread. */
    private final Logger logger = GameLogger.getLogger();

    /**
     * Costruisce un nuovo {@code HintMoveThread} con accesso al modello di gioco.
     *
     * @param model il modello di gioco su cui eseguire il calcolo del suggerimento
     */
    public HintMoveThread(PuzzlemasterModel model) {
        this.model = model;
    }

    /**
     * Metodo eseguito automaticamente quando il thread viene avviato.
     * Calcola la prossima mossa suggerita usando il metodo {@code nextBestMove()}
     * del modello e scrive il risultato nei log.
     */
    @Override
    public void run() {
        try {
            logger.info("💭 Calcolo della mossa suggerita in background...");
            Move move = model.nextBestMove();
            logger.info("💡 Mossa suggerita calcolata: " + move);
        } catch (Exception e) {
            logger.severe("❌ Errore durante il calcolo della mossa suggerita: " + e.getMessage());
        }
    }
}

