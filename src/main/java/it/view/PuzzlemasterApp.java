package it.view;

import it.controller.Controller;
import it.model.PuzzlemasterModel;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Classe principale per avviare l'applicazione Puzzlemaster.
 * Inizializza modello, vista e controller, e crea la finestra principale.
 */
public class PuzzlemasterApp {

    private static final Logger logger = Logger.getLogger(PuzzlemasterApp.class.getName());

    /**
     * Costruttore che configura e avvia l'applicazione.
     * Inizializza il modello, la vista, il controller e la finestra JFrame.
     */
    public PuzzlemasterApp() {
        PuzzlemasterModel model = new PuzzlemasterModel();
        PuzzlemasterUI view = new PuzzlemasterUI();

        try {
            view.setModel(model);         // collega model e view
            model.initState(1);           // inizializza livello facile
            new Controller(model, view);

            JFrame frame = new JFrame("Puzzlemaster");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.setSize(1100, 800);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            logger.info("🎮 Puzzlemaster avviato con successo.");

        } catch (Exception e) {
            logger.severe("❌ Errore durante l'inizializzazione dell'app: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Errore durante l'inizializzazione dell'app.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}




