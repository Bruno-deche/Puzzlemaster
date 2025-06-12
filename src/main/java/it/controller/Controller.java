package it.controller;

import it.model.PuzzlemasterModel;
import it.strategy.MoveStrategy;
import it.util.GameLogger;
import it.view.PuzzlemasterUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;
import it.strategy.StrategyMetadata;
import java.io.InputStream;
import java.util.Properties;



/**
 * Controller principale che inizializza il gioco e gestisce i listener dell'interfaccia utente.
 * Integra la selezione dinamica delle strategie di gioco usando Reflection.
 */
public class Controller extends UIController {

    private static final Logger logger = GameLogger.getLogger();
    private final PuzzlemasterModel puzzlemasterModel;
    private final PuzzlemasterUI puzzlemasterUI;
    private MouseListener blockListener;

    /**
     * Costruttore del controller principale.
     *
     * @param puzzlemasterModel il modello logico
     * @param puzzlemasterUI    l'interfaccia utente
     */
    public Controller(PuzzlemasterModel puzzlemasterModel, PuzzlemasterUI puzzlemasterUI) {
        super(puzzlemasterModel, puzzlemasterUI);
        this.puzzlemasterModel = puzzlemasterModel;
        this.puzzlemasterUI = puzzlemasterUI;

        this.puzzlemasterModel.setUI(puzzlemasterUI);
        this.puzzlemasterUI.setModel(puzzlemasterModel);

        initStart();
    }

    /**
     * Metodo chiamato alla chiusura della finestra per salvare lo stato o chiudere il database.
     *
     * @param e evento del mouse (può essere null in caso di chiamata programmatica)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        puzzlemasterModel.closeDatabaseConnection();
        logger.info("👋 Applicazione chiusa correttamente.");
    }

    /**
     * Legge un file di configurazione chiamato "config.properties" presente nelle risorse del progetto.
     * Il file contiene parametri come la strategia da utilizzare e le credenziali del database.
     *
     * @return un oggetto Properties con le chiavi caricate dal file, o valori predefiniti se il file non è trovato
     */
    private Properties loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.warning("⚠️ File di configurazione non trovato. Verrà utilizzata la strategia predefinita.");
                props.setProperty("strategy", "EasyStrategy");
                return props;
            }
            props.load(input);
        } catch (Exception e) {
            logger.severe("❌ Errore nella lettura del file di configurazione: " + e.getMessage());
            props.setProperty("strategy", "EasyStrategy");
        }
        return props;
    }


    /**
     * Inizializza l'interfaccia e il gioco, chiedendo il livello di difficoltà all'utente.
     * Imposta dinamicamente la strategia scelta usando Reflection.
     */
    /**
     * Inizializza l'interfaccia e il gioco, leggendo la strategia da un file di configurazione.
     * Carica dinamicamente la classe di strategia tramite reflection e assegna il livello.
     */
    private void initStart() {
        try {
            puzzlemasterModel.initDatabase();
            puzzlemasterUI.initStart();
            logger.info("🎯 Inizializzazione completata");

            // Legge configurazione da file
            Properties config = loadConfiguration();
            String strategyClassName = config.getProperty("strategy", "EasyStrategy");

            // Valuta il livello e blocchi in base alla strategia
            int blockCount = 5;
            switch (strategyClassName) {
                case "EasyStrategy" -> {
                    puzzlemasterModel.setLevel(1);
                    blockCount = 5;
                }
                case "RandomStrategy" -> {
                    puzzlemasterModel.setLevel(2);
                    blockCount = 10;
                }
                case "TargetedStrategy" -> {
                    puzzlemasterModel.setLevel(3);
                    blockCount = 15;
                }
                default -> {
                    logger.warning("⚠️ Strategia non riconosciuta: " + strategyClassName + ". Uso valori di default.");
                    puzzlemasterModel.setLevel(1);
                    blockCount = 5;
                }
            }

            // Caricamento dinamico della strategia
            MoveStrategy strategy = loadStrategy(strategyClassName);
            puzzlemasterModel.setStrategy(strategy);
            logger.info("📌 Strategia caricata da config: " + strategyClassName);

            // Avvio UI e listener
            puzzlemasterUI.initGame(blockCount, 0, null);
            this.blockListener = new CollisionBlockListener(puzzlemasterUI.getBoardPanel(), puzzlemasterUI);
            addListener(this.blockListener);
            puzzlemasterUI.attachBlockListeners(this.blockListener);

        } catch (Exception e) {
            logger.severe("❌ Errore all'avvio: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "❌ Errore durante l'inizializzazione del database.");
            puzzlemasterUI.showMessage("Errore inizializzazione", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Carica dinamicamente una classe di strategia a partire dal nome e ne crea un'istanza.
     *
     * @param strategyName il nome della classe della strategia (es. "EasyStrategy")
     * @return un'istanza di MoveStrategy corrispondente alla classe specificata
     * @throws RuntimeException se la classe non viene trovata o non può essere istanziata
     */
    /**
     * Carica dinamicamente una classe di strategia a partire dal nome e ne crea un'istanza.
     * Se la classe è annotata con {@link StrategyMetadata}, ne stampa i metadati nel logger.
     *
     * @param strategyName il nome della classe della strategia (es. "EasyStrategy")
     * @return un'istanza di MoveStrategy corrispondente alla classe specificata
     * @throws RuntimeException se la classe non viene trovata o non può essere istanziata
     */
    private MoveStrategy loadStrategy(String strategyName) {
        try {
            String fullClassName = "it.strategy." + strategyName;
            Class<?> strategyClass = Class.forName(fullClassName);

            // Istanziazione dinamica
            MoveStrategy instance = (MoveStrategy) strategyClass.getDeclaredConstructor().newInstance();

            // Estrazione dei metadati se presenti
            if (strategyClass.isAnnotationPresent(StrategyMetadata.class)) {
                StrategyMetadata meta = strategyClass.getAnnotation(StrategyMetadata.class);
                logger.info("🧠 Strategia: " + meta.difficulty() + " - " + meta.description());
            }

            return instance;
        } catch (Exception e) {
            logger.severe("⚠️ Errore nel caricamento della strategia: " + strategyName + " → " + e.getMessage());
            throw new RuntimeException("Errore nella creazione della strategia: " + strategyName, e);
        }
    }


    /**
     * Assegna i listener principali alla finestra e ai componenti della UI.
     *
     * @param blockListener il listener per i blocchi della board
     */
    private void addListener(MouseListener blockListener) {
        WindowAdapter exit = new WindowAdapter() {
            /**
             * Metodo eseguito quando la finestra viene chiusa.
             *
             * @param e evento di chiusura della finestra
             */
            @Override
            public void windowClosing(WindowEvent e) {
                mousePressed(null);
            }
        };

        SaveCommand saveCommand = new SaveCommand(puzzlemasterModel, puzzlemasterUI);

        puzzlemasterUI.addStartListener(
                exit,
                new ConfigurationListener(puzzlemasterModel, puzzlemasterUI)
        );

        puzzlemasterUI.addAuthenticationListeners(
                new AuthListener(puzzlemasterModel, puzzlemasterUI),
                new DisconnectionListener(puzzlemasterModel, puzzlemasterUI),
                new SavedGamesListener(puzzlemasterModel, puzzlemasterUI, saveCommand)
        );

        puzzlemasterUI.addGameBoardListeners(
                new BoardListener(puzzlemasterModel, puzzlemasterUI),
                blockListener
        );

        puzzlemasterUI.addButtonsListeners(
                CommandFactory.createCommand("restart", puzzlemasterModel, puzzlemasterUI, saveCommand),
                saveCommand,
                null
        );

        logger.info("✅ Tutti i listener assegnati correttamente.");
    }
}













