package it.view;

import it.controller.*;
import it.model.Move;
import it.model.PuzzlemasterModel;
import it.strategy.MoveStrategy;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import it.composite.Block;
import it.composite.Board;


public class PuzzlemasterUI extends JPanel {

    private static final Logger logger = Logger.getLogger(PuzzlemasterUI.class.getName());
    private JLabel moveCounterLabel;
    private List<BlockGoal> targetBlocks = new ArrayList<>();

    private JButton restartButton, saveButton;
    private Board board;
    private BoardPanel boardPanel;
    private PuzzlemasterModel puzzlemasterModel;
    private MouseListener blockListener;
    private boolean restartUsed = false;

    private int counter;
    private Color[] targetColors;

    /**
 * Imposta la strategia di gioco da utilizzare.
 *
 * @param strategy l'oggetto MoveStrategy che definisce la logica dei movimenti.
 */
public void setStrategy(MoveStrategy strategy) {
        puzzlemasterModel.setStrategy(strategy);
    }

    public void setModel(PuzzlemasterModel model) {
        this.puzzlemasterModel = model;
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    public void makeMove(Move move, int counter) {
        Component[] components = boardPanel.getComponents();
        Point from = move.getFrom();
        Point to = move.getTo();

        for (Component c : components) {
            if (c instanceof JButton) {
                Rectangle bounds = c.getBounds();
                if (bounds.x == from.x && bounds.y == from.y) {
                    c.setLocation(to.x, to.y);
                    break;
                }
            }
        }

        moveCounterLabel.setText("Mosse: " + counter);
        revalidate();
        repaint();

        // Riattacca i listener per mantenere attiva l'interazione
        attachBlockListeners(blockListener);
    }

    /**
 * Inizializza l'interfaccia utente per un nuovo utente.
 *
 * @param username il nome dell'utente da visualizzare.
 */
public void initUser(String username) {
        // Logica per inizializzare l'utente nella UI
    }

    /**
 * Mostra un pannello di dialogo con l'elenco delle partite salvate.
 *
 * @param savedGames lista dei salvataggi disponibili.
 * @param listener listener da associare ai bottoni di caricamento e cancellazione.
 */
public void showSavedGames(List<String> savedGames, MouseListener listener) {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        savedGames.forEach(listModel::addElement);

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(list);

        JButton loadButton = new JButton("Carica");
        JButton deleteButton = new JButton("Elimina");

        JPanel buttons = new JPanel();
        buttons.add(loadButton);
        buttons.add(deleteButton);

        panel.add(new JLabel("Partite salvate:"), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setTitle("Saved Games");
        dialog.setContentPane(panel);
        dialog.setSize(400, 300);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        // ✅ Listener CARICA con validazione
        loadButton.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null) {

                if (!it.util.InputValidator.isValidSaveName(selected)) {
                    showMessage("Nome del salvataggio non valido (min 4, max 30 caratteri).", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dialog.dispose();
                try {
                    puzzlemasterModel.resumeState(selected);

                    // Crea un nuovo listener funzionante
                    MouseListener newBlockListener = new CollisionBlockListener(this.boardPanel, this);
                    this.blockListener = newBlockListener;

                    // Ricarica la partita con il nuovo listener
                    Rectangle[] loaded = puzzlemasterModel.getInitialPositions();
                    int counter = puzzlemasterModel.getCounter();
                    this.initGameFromResume(loaded, counter, newBlockListener);

                    // COLLEGA i blocchi al listener appena creato
                    this.attachBlockListeners(newBlockListener);

                    logger.info("✅ Caricamento completato. Listener riattivato.");

                } catch (Exception ex) {
                    logger.severe("❌ Errore nel caricamento del salvataggio: " + ex.getMessage());
                    showMessage("Errore nel caricamento del salvataggio: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showMessage("Seleziona prima un salvataggio", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        // ✅ Listener ELIMINA con validazione
        deleteButton.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null) {

                if (!it.util.InputValidator.isValidSaveName(selected)) {
                    showMessage("Nome del salvataggio non valido (min 4, max 30 caratteri).", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Vuoi davvero eliminare il salvataggio \"" + selected + "\"?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        puzzlemasterModel.deleteSavedGame(selected);
                        listModel.removeElement(selected);
                        showMessage("Salvataggio \"" + selected + "\" eliminato.", "Eliminato", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        logger.severe("❌ Errore durante l'eliminazione: " + ex.getMessage());
                        showMessage("Errore durante l'eliminazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                showMessage("Seleziona prima un salvataggio", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }


    public void showSavedGamesFromDB(List<String> entries, Consumer<Integer> onLoad, Consumer<Integer> onDelete) {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        entries.forEach(listModel::addElement);

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(list);

        JButton loadButton = new JButton("Carica");
        JButton deleteButton = new JButton("Elimina");

        JPanel buttons = new JPanel();
        buttons.add(loadButton);
        buttons.add(deleteButton);

        panel.add(new JLabel("Salvataggi disponibili (DB):"), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setTitle("Saved Games (Database)");
        dialog.setContentPane(panel);
        dialog.setSize(400, 300);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        // Caricamento
        loadButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex >= 0) {
                String entry = listModel.getElementAt(selectedIndex);
                int id = Integer.parseInt(entry.replaceAll("[^0-9]", ""));
                onLoad.accept(id);
                dialog.dispose();
            }
        });

        // Eliminazione
        deleteButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex >= 0) {
                String entry = listModel.getElementAt(selectedIndex);
                int id = Integer.parseInt(entry.replaceAll("[^0-9]", ""));
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Vuoi davvero eliminare il salvataggio ID: " + id + "?",
                        "Conferma",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    onDelete.accept(id);
                    listModel.remove(selectedIndex);
                }
            }
        });

        dialog.setVisible(true);
    }


    public void addStartListener(java.awt.event.WindowAdapter exitListener, Object configurationListener) {
        // Logica per aggiungere listener alla schermata iniziale
    }

    public void addAuthenticationListeners(Object authListener, Object disconnectionListener, Object savedGamesListListener) {
        // Logica per autenticazione
    }

    public void addGameBoardListeners(Object boardListener, Object blockListener) {
        // Logica per aggiungere listener alla board
    }

    public void initGame(int blockCount, int counter, MouseListener blockListener) {
        this.counter = counter;

        Rectangle[] positions = generateBlockPositions(blockCount);
        removeAll();
        setLayout(null);

        // Genera lista di posizioni per il BoardPanel
        List<Point> positionsList = Arrays.stream(positions)
                .map(r -> new Point(r.x, r.y))
                .collect(Collectors.toList());

        // Inizializza colori
        List<Color> baseColors = List.of(
                new Color(66, 135, 245),   // blu
                new Color(40, 200, 100),   // verde
                new Color(255, 255, 0),    // giallo
                new Color(255, 165, 0),    // arancione
                new Color(255, 0, 255)     // magenta
        );
        List<Color> combination = new ArrayList<>(baseColors);
        Collections.shuffle(combination, new Random(System.nanoTime()));
        Color[] targetColors = combination.toArray(new Color[0]);

        List<Color> shuffled = new ArrayList<>(combination);
        Collections.shuffle(shuffled, new Random(System.nanoTime()));
        Color[] shuffledColors = shuffled.toArray(new Color[0]);

        // Crea board logica
        this.board = new Board();

        // Crea boardPanel grafico
        boardPanel = new BoardPanel(positionsList, shuffledColors, null);
        boardPanel.setLayout(null);
        boardPanel.setBounds(50, 50, 600, 600);
        boardPanel.setBorder(BorderFactory.createTitledBorder("Game Board"));
        boardPanel.setTargetCombination(Arrays.asList(targetColors));
        add(boardPanel);

        // Crea o assegna listener valido
        MouseListener listener = (blockListener != null)
                ? blockListener
                : new CollisionBlockListener(boardPanel, this);
        this.blockListener = listener;

        // Etichetta contatore
        moveCounterLabel = new JLabel("Mosse: " + counter);
        moveCounterLabel.setBounds(50, 10, 150, 30);
        add(moveCounterLabel);

        // Aggiunta dei blocchi logici e visivi
        Block.Direction[] allDirections = Block.Direction.values();
        for (int i = 0; i < positions.length; i++) {
            Rectangle r = positions[i];

            JButton blockButton = new JButton();
            blockButton.setBounds(r);
            blockButton.setBackground(shuffledColors[i % shuffledColors.length]);
            blockButton.setOpaque(true);
            blockButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            blockButton.setFocusable(false);

            Block.Direction direction = allDirections[i % allDirections.length];
            blockButton.putClientProperty("direction", direction);

            // Blocchi logici
            Block block = new Block(r, shuffledColors[i % shuffledColors.length], direction, Block.Shape.SMALL);
            board.add(block);

            // Blocchi grafici
            blockButton.addMouseListener(listener);
            blockButton.addMouseMotionListener((MouseMotionListener) listener);
            boardPanel.add(blockButton);
        }

        // Etichetta a destra del pulsante Restart
        JLabel restartInfoLabel = new JLabel("Only one restart allowed");
        restartInfoLabel.setBounds(810, 50, 180, 30); // posizione a destra
        add(restartInfoLabel);

        // Bottoni
        restartButton = new JButton("Restart");
        restartButton.setBounds(700, 50, 100, 30);
        add(restartButton);

        saveButton = new JButton("Save");
        saveButton.setBounds(700, 90, 100, 30);
        add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.setBounds(700, 130, 100, 30);
        add(loadButton);

        configureLoadButton(loadButton);

        // Stato iniziale
        puzzlemasterModel.setInitialState(positions, shuffledColors);
        puzzlemasterModel.setTargetColors(targetColors);

        revalidate();
        repaint();
    }


    public void initGameFromResume(Rectangle[] positions, int counter, MouseListener blockListener) {
        this.counter = counter;
        this.blockListener = blockListener;

        List<Point> positionList = Arrays.stream(positions)
                .map(r -> new Point(r.x, r.y))
                .collect(Collectors.toList());

        Color[] colors = puzzlemasterModel.getInitialColors();
        Color[] targetColors = puzzlemasterModel.getTargetColors();

        boardPanel = new BoardPanel(positionList, colors, (MouseInputListener) blockListener);
        boardPanel.setLayout(null);
        boardPanel.setBounds(50, 50, 600, 600);
        boardPanel.setBorder(BorderFactory.createTitledBorder("Game Board"));

        Block.Direction[] directions = Block.Direction.values();
        for (int i = 0; i < positions.length; i++) {
            Rectangle r = positions[i];
            JButton block = new JButton();
            block.setBounds(r);
            block.setBackground(colors[i % colors.length]);
            block.setOpaque(true);
            block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            block.setFocusable(true);  // 🔁 Focus abilitato
            block.requestFocusInWindow();  // 🔁 Richiesta focus attiva
            block.putClientProperty("direction", directions[i % directions.length]);
            block.addMouseListener(blockListener);
            block.addMouseMotionListener((MouseMotionListener) blockListener);
            boardPanel.add(block);
        }

        boardPanel.setTargetCombination(Arrays.asList(targetColors));

        removeAll();
        setLayout(null);
        add(boardPanel);

        moveCounterLabel = new JLabel("Mosse: " + counter);
        moveCounterLabel.setBounds(50, 10, 150, 30);
        add(moveCounterLabel);

        restartButton = new JButton("Restart");
        restartButton.setBounds(700, 50, 100, 30);
        add(restartButton);

        saveButton = new JButton("Save");
        saveButton.setBounds(700, 90, 100, 30);
        add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.setBounds(700, 130, 100, 30);
        add(loadButton);

        configureLoadButton(loadButton);

        addButtonsListeners(
                new RestartCommand(puzzlemasterModel, this),
                new SaveCommand(puzzlemasterModel, this),
                null
        );

        attachBlockListeners(blockListener);

        // Reset dello stato del listener se è un CollisionBlockListener
        if (blockListener instanceof CollisionBlockListener cl) {
            cl.resetState();
        }

        revalidate();
        repaint();
        this.requestFocusInWindow();  // 🔁 Ulteriore richiesta focus a livello di UI

        logger.info("✅ Blocchi aggiunti: " + boardPanel.getComponentCount());
        logger.info("✅ Listener associato: " + blockListener.getClass().getSimpleName());
    }

    private void configureLoadButton(JButton loadButton) {
        // Rimuove tutti i listener esistenti
        for (ActionListener al : loadButton.getActionListeners()) {
            loadButton.removeActionListener(al);
        }

        // Aggiunge il nuovo listener
        loadButton.addActionListener(e -> {
            try {
                java.util.List<String> saved = puzzlemasterModel.getSavedGameList();

                // Flag per impedire doppia apertura dalla stessa finestra
                final boolean[] loadingDone = {false};

                showSavedGames(saved, new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (loadingDone[0]) return; // previene doppia azione
                        loadingDone[0] = true;

                        String selected = (String) ((JButton) e.getSource()).getClientProperty("selectedSave");
                        if (selected != null) {
                            try {
                                logger.info("🔄 Avvio resumeState con salvataggio: " + selected);
                                puzzlemasterModel.resumeState(selected);

                                MouseListener newBlockListener = new CollisionBlockListener(boardPanel, PuzzlemasterUI.this);
                                blockListener = newBlockListener;

                                Rectangle[] positions = puzzlemasterModel.getInitialPositions();
                                int counter = puzzlemasterModel.getCounter();

                                logger.info("🟢 Chiamata initGameFromResume...");
                                initGameFromResume(positions, counter, newBlockListener);
                                logger.info("✅ initGameFromResume completata.");

                                // Reset stato listener
                                if (newBlockListener instanceof CollisionBlockListener cl) {
                                    cl.resetState();
                                    logger.info("✅ Listener resettato correttamente.");
                                }

                            } catch (Exception ex) {
                                logger.severe("❌ Errore nel caricamento del salvataggio: " + ex.getMessage());
                                showMessage("Errore nel caricamento del salvataggio: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            logger.warning("⚠️ Nessun salvataggio selezionato.");
                        }
                    }
                });

            } catch (Exception ex) {
                logger.severe("❌ Errore durante il recupero della lista salvataggi: " + ex.getMessage());
                showMessage("Nessun salvataggio trovato.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    private Rectangle[] generateBlockPositions(int blockCount) {
        Rectangle[] positions = new Rectangle[blockCount];
        int blockSize = 80;
        int gap = 10;
        int blocksPerRow = 5;

        int totalWidth = blocksPerRow * blockSize + (blocksPerRow - 1) * gap;
        int panelWidth = 600; // larghezza fissa del boardPanel
        int startX = (panelWidth - totalWidth) / 2; // centratura orizzontale
        int startY = 60;

        for (int i = 0; i < blockCount; i++) {
            int row = i / blocksPerRow;
            int col = i % blocksPerRow;

            int x = startX + col * (blockSize + gap);
            int y = startY + row * (blockSize + gap);

            positions[i] = new Rectangle(x, y, blockSize, blockSize);
        }

        return positions;
    }

    public Rectangle[] getCurrentBlockPositions() {
        Component[] components = boardPanel.getComponents();
        java.util.List<Rectangle> positions = new java.util.ArrayList<>();

        for (Component c : components) {
            if (c instanceof JButton) {
                positions.add(c.getBounds());
            }
        }

        return positions.toArray(new Rectangle[0]);
    }

    public void addButtonsListeners(Object restartCommand, Object saveCommand, Object loadCommand) {
        if (restartButton == null || saveButton == null) {
            logger.severe("❌ Bottoni non inizializzati: chiama prima initGame()");
            throw new IllegalStateException("Bottoni non inizializzati: chiama prima initGame()");
        }

        if (restartCommand instanceof MouseListener) {
            restartButton.addMouseListener((MouseListener) restartCommand);
        }

        if (saveCommand instanceof MouseListener) {
            saveButton.addMouseListener((MouseListener) saveCommand);
        }

        // Se in futuro serve un listener per Load, aggiungilo qui
    }

    public void restart() {
        if (restartUsed) return; // usabile solo una volta
        restartUsed = true;

        removeAll();
        repaint();
        revalidate();

        // Ricalcola il numero di blocchi dal model
        int blockCount = puzzlemasterModel.getInitialPositions().length;

        // Riavvia il gioco con lo stesso listener
        initGame(blockCount, 0, blockListener);

        // Disabilita visivamente il pulsante restart
        if (restartButton != null) {
            restartButton.setEnabled(false);
        }

        logger.info("🔁 Gioco riavviato.");
    }

    public boolean showAuthenticationDialog() {
        return true;
    }

    public void selectBlock(Component c) {
        logger.info("Blocco selezionato: " + c);
    }

    public void logout() {
        logger.info("🚪 Logout effettuato.");
    }

    public void animateBlock(Block block, int dx, int dy) {
        final int steps = 20;
        final int delay = 15;
        final int stepX = dx / steps;
        final int stepY = dy / steps;

        Timer timer = new Timer(delay, null);
        final int[] currentStep = {0};
        timer.addActionListener(e -> {
            if (currentStep[0] < steps) {
                block.move(stepX, stepY);
                repaint();
                currentStep[0]++;
            } else {
                timer.stop();
            }
        });

        timer.start();
    }

    public void animateBlockInThread(Block block, int dx, int dy) {
        new Thread(() -> {
            int steps = 20;
            int stepX = dx / steps;
            int stepY = dy / steps;

            try {
                for (int i = 0; i < steps; i++) {
                    Thread.sleep(20);
                    block.move(stepX, stepY);
                    repaint();
                }
            } catch (InterruptedException e) {
                logger.severe("❌ Errore durante l'animazione del blocco: " + e.getMessage());
            }
        }).start();
    }


    /**
     * Inizializza il gioco recuperando la lista dei salvataggi dal database.
     * L'operazione viene eseguita in un thread separato per evitare il blocco dell'interfaccia utente.
     * I salvataggi trovati vengono stampati nel logger, e in caso di errore viene mostrato un messaggio all'utente.
     */
    public void initStart() {
        logger.info("📂 Salvataggi disponibili:");

        new Thread(() -> {
            try {
                // Recupero salvataggi dal database in background
                List<String> savedGames = puzzlemasterModel.getSavedGameList();

                // Logging dei salvataggi trovati
                savedGames.forEach(game -> logger.info("Salvataggio trovato: " + game));

                // Eventuale aggiornamento della GUI (es. lista salvataggi)
                SwingUtilities.invokeLater(() -> {
                    // showSavedGames(savedGames); // Sbloccabile se hai una GUI da aggiornare
                });

            } catch (it.model.GamePersistenceException e) {
                logger.severe("❌ Errore durante la lettura dei salvataggi: " + e.getMessage());

                // Mostra un messaggio grafico di errore all’utente
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null,
                            "Errore nel caricamento dei salvataggi.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }



    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public void attachBlockListeners(MouseListener listener) {
        for (Component c : boardPanel.getComponents()) {
            if (c instanceof JButton button) {
                button.addMouseListener(listener);
                button.addMouseMotionListener((MouseMotionListener) listener);
            }
        }
    }

    public void showVictoryAnimation() {
        JDialog dialog = new JDialog((Frame) null, "VITTORIA!", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel() {
            private final Color[] colors = {
                    Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.ORANGE
            };

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // sfondo nero
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, width, height);

                // scritta centrale
                g2d.setFont(new Font("Arial", Font.BOLD, 50));
                g2d.setColor(Color.WHITE);
                g2d.drawString("HAI VINTO!", width / 2 - 150, 100);

                // fuochi d'artificio semplici
                for (int i = 0; i < 30; i++) {
                    g2d.setColor(colors[i % colors.length]);
                    int x = (int) (Math.random() * width);
                    int y = (int) (Math.random() * height);
                    int size = 10 + (int) (Math.random() * 20);
                    g2d.fillOval(x, y, size, size);
                }
            }
        };

        dialog.setContentPane(panel);

        Timer timer = new Timer(150, e -> panel.repaint());
        timer.start();

        // chiusura al click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                dialog.dispose();
                logger.info("🎉 Animazione di vittoria chiusa.");
            }
        });

        dialog.setVisible(true);
        logger.info("🎉 Animazione di vittoria avviata.");
    }


    public List<BlockGoal> getTargetBlocks() {
        return targetBlocks;
    }

    public void checkVictory() {
        if (!(boardPanel instanceof BoardPanel panel)) return;

        List<BlockGoal> goals = panel.getTargetBlocks();
        Component[] blocks = boardPanel.getComponents();

        int matched = 0;

        for (BlockGoal goal : goals) {
            boolean found = false;

            for (Component c : blocks) {
                if (c instanceof JButton b) {
                    Rectangle br = b.getBounds();
                    Color bc = b.getBackground();

                    if (isClose(goal.bounds, br, 5) && isSameColor(goal.color, bc)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) matched++;
        }

        // Evita attivazione immediata: verifica che ci siano state mosse
        if (matched == goals.size() && getMoveCounter() > 0) {
            showVictoryAnimation();
        }
    }

    public int getMoveCounter() {
        return counter;
    }

    private boolean isSameColor(Color c1, Color c2) {
        return c1.getRed() == c2.getRed() &&
                c1.getGreen() == c2.getGreen() &&
                c1.getBlue() == c2.getBlue();
    }

    private boolean isClose(Rectangle r1, Rectangle r2, int tolerance) {
        return Math.abs(r1.x - r2.x) <= tolerance &&
                Math.abs(r1.y - r2.y) <= tolerance;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(6, 1, 5, 5));

        JButton restart = new JButton("Restart");
        JButton save = new JButton("Save");
        JButton next = new JButton("Next");
        JButton undo = new JButton("Undo");
        JButton home = new JButton("Home");
        JButton load = new JButton("Load");

        restart.addMouseListener(new RestartCommand(puzzlemasterModel, this));
        save.addMouseListener(new SaveCommand(puzzlemasterModel, this));
        next.addMouseListener(new NextCommand(puzzlemasterModel, this));
        undo.addMouseListener(new UndoCommand(puzzlemasterModel, this));
        home.addMouseListener(new HomeCommand(puzzlemasterModel, this, new SaveCommand(puzzlemasterModel, this)));
        load.addMouseListener(new SavedGamesListener(puzzlemasterModel, this, new SaveCommand(puzzlemasterModel, this)));

        controlPanel.add(restart);
        controlPanel.add(save);
        controlPanel.add(next);
        controlPanel.add(undo);
        controlPanel.add(home);
        controlPanel.add(load);

        return controlPanel;
    }

    private JPanel createTargetPanel() {
        return new JPanel();
    }

    public static class BlockGoal {
        public Rectangle bounds;
        public Color color;

        public BlockGoal(Rectangle bounds, Color color) {
            this.bounds = bounds;
            this.color = color;
        }
    }

    /**
     * Gestisce le operazioni da eseguire in caso di vittoria.
     * Mostra l'animazione di vittoria.
     */
    public void winHandler() {
        showVictoryAnimation(); // oppure altra logica di fine partita
    }

}











