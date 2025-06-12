package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Listener che gestisce l'evento di configurazione iniziale del gioco,
 * attivato da un'azione (es. click su un pulsante).
 */
public class ConfigurationListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(ConfigurationListener.class.getName());

    private final PuzzlemasterModel model;
    private final PuzzlemasterUI ui;

    /**
     * Costruttore del listener che collega il modello e la UI.
     *
     * @param model modello logico del gioco
     * @param ui    interfaccia grafica
     */
    public ConfigurationListener(PuzzlemasterModel model, PuzzlemasterUI ui) {
        this.model = model;
        this.ui = ui;
    }

    /**
     * Metodo invocato quando si attiva un evento associato al listener.
     * In questo caso, inizializza il drag listener per la board di gioco.
     *
     * @param e evento dell'interfaccia utente
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("🔧 Configurazione avviata!");

        // Passaggio corretto di entrambi i parametri richiesti
        BlockListener dragListener = new BlockListener(ui.getBoardPanel(), model.getBoard());
        ui.addGameBoardListeners(null, dragListener);
    }
}








