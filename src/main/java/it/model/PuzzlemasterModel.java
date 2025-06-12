package it.model;

import it.composite.Block;
import it.composite.Board;
import it.strategy.EasyStrategy;
import it.strategy.MoveStrategy;
import it.util.DBConnection;
import it.util.GameLogger;
import it.util.PasswordUtil;
import it.view.BoardPanel;
import it.view.PuzzlemasterUI;
import it.future_features.BlockConfig;


import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PuzzlemasterModel {

    private static final Logger logger = GameLogger.getLogger();
    private final List<Move> moveHistory = new ArrayList<>();
    private Point selectedPiece;
    private int moveCounter;
    private int maxMoves;
    private Block targetBlock;
    private PuzzlemasterModel puzzlemasterModel;
    private String name;
    private int counter;
    private Color[] targetColors;
    private MoveStrategy strategy = new EasyStrategy();
    private String currentUser = null;
    private Board board = new Board();
    private PuzzlemasterUI ui;
    private Rectangle exitArea;
    private int level = 1;
    private Color[] initialColors = {
            new Color(66, 135, 245),
            new Color(40, 200, 100),
            new Color(255, 255, 0),
            new Color(255, 165, 0),
            new Color(255, 0, 255)
    };
    private Rectangle[] initialPositions;

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUI(PuzzlemasterUI ui) {
        this.ui = ui;
    }

    public void initDatabase() {
        logger.info("\uD83D\uDCBE Inizializzazione del database.");
        logClassMethods(Move.class);
    }

    private void logClassMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Stream.of(methods)
                .map(Method::getName)
                .distinct()
                .forEach(m -> logger.info("\uD83D\uDD0D Metodo disponibile in " + clazz.getSimpleName() + ": " + m));
    }

    public void closeDatabaseConnection() {
        logger.info("\uD83D\uDD0C Connessione al database chiusa.");
    }

    public boolean hasWin() {
        if (targetBlock == null || exitArea == null) return false;
        return exitArea.intersects(targetBlock.getBounds());
    }

    public void setInitialState(Rectangle[] positions, Color[] colors) {
        this.initialPositions = positions;
        this.initialColors = colors;
    }

    public Rectangle[] getInitialPositions() {
        return initialPositions;
    }

    public Color[] getInitialColors() {
        return initialColors;
    }

    public void setInitialColors(Color[] colors) {
        this.initialColors = colors;
    }


    public void initState(int configuration) {
        switch (configuration) {
            case 1 -> {
                exitArea = new Rectangle(220, 500, 40, 40); // 🎯 uscita livello facile
                loadEasyLevel();
            }
            case 2 -> {
                exitArea = new Rectangle(220, 500, 40, 40); // 🎯 uscita livello medio
                loadMediumLevel();
            }
            case 3 -> {
                exitArea = new Rectangle(220, 500, 40, 40); // 🎯 uscita livello difficile
                loadHardLevel();
            }
            default -> throw new IllegalArgumentException("Configurazione non supportata");
        }
        logger.info("🎮 Livello " + configuration + " inizializzato");
    }

    public void loadEasyLevel() {
        board.clear();
        moveCounter = 0;
        maxMoves = 60;
        exitArea = new Rectangle(100, 500, 80, 10);

        if (ui != null && ui.getBoardPanel() instanceof BoardPanel panel) {
            List<Color> combination = List.of(
                    new Color(66, 135, 245),
                    new Color(40, 200, 100),
                    new Color(255, 255, 0),
                    new Color(255, 165, 0),
                    new Color(255, 0, 255)
            );
            panel.setTargetCombination(combination);
        }

        List<BlockConfig> blocks = List.of(
                new BlockConfig(Color.RED, Block.Shape.MEDIUM, null, new Rectangle(100, 400, 80, 80), true),
                new BlockConfig(Color.BLUE, Block.Shape.SMALL, null, new Rectangle(0, 0, 40, 40), false),
                new BlockConfig(Color.GREEN, Block.Shape.SMALL, null, new Rectangle(50, 0, 40, 40), false),
                new BlockConfig(Color.YELLOW, Block.Shape.MEDIUM, null, new Rectangle(150, 0, 80, 80), false),
                new BlockConfig(Color.ORANGE, Block.Shape.SMALL, null, new Rectangle(200, 0, 40, 40), false)
        );

        for (BlockConfig cfg : blocks) {
            Block.Direction dir = (cfg.direction != null) ? cfg.direction : Block.Direction.HORIZONTAL;
            Block block = new Block(cfg.bounds, cfg.color, dir, cfg.shape);
            board.add(block);
            if (cfg.isTargetBlock) targetBlock = block;
        }
    }

    public void loadMediumLevel() {
        board.clear();
        moveCounter = 0;
        maxMoves = 50;

        List<BlockConfig> blocks = List.of(
                new BlockConfig(Color.RED, Block.Shape.MEDIUM, Block.Direction.HORIZONTAL, new Rectangle(100, 400, 80, 80), true),
                new BlockConfig(Color.BLUE, Block.Shape.SMALL, Block.Direction.VERTICAL, new Rectangle(0, 0, 40, 40), false),
                new BlockConfig(Color.GREEN, Block.Shape.SMALL, Block.Direction.HORIZONTAL, new Rectangle(50, 0, 40, 40), false),
                new BlockConfig(Color.YELLOW, Block.Shape.SMALL, Block.Direction.VERTICAL, new Rectangle(100, 0, 40, 40), false),
                new BlockConfig(Color.ORANGE, Block.Shape.MEDIUM, Block.Direction.HORIZONTAL, new Rectangle(150, 0, 80, 40), false),
                new BlockConfig(Color.CYAN, Block.Shape.SMALL, Block.Direction.VERTICAL, new Rectangle(200, 0, 40, 40), false)
        );

        for (BlockConfig cfg : blocks) {
            Block block = new Block(cfg.bounds, cfg.color, cfg.direction, cfg.shape);
            board.add(block);
            if (cfg.isTargetBlock) targetBlock = block;
        }
    }

    public void loadHardLevel() {
        board.clear();
        moveCounter = 0;
        maxMoves = 40;

        List<BlockConfig> blocks = List.of(
                new BlockConfig(Color.RED, Block.Shape.LARGE, Block.Direction.HORIZONTAL, new Rectangle(100, 400, 120, 80), true),
                new BlockConfig(Color.BLUE, Block.Shape.MEDIUM, Block.Direction.VERTICAL, new Rectangle(0, 0, 40, 80), false),
                new BlockConfig(Color.GREEN, Block.Shape.MEDIUM, Block.Direction.HORIZONTAL, new Rectangle(50, 0, 80, 40), false),
                new BlockConfig(Color.YELLOW, Block.Shape.SMALL, Block.Direction.VERTICAL, new Rectangle(130, 0, 40, 40), false),
                new BlockConfig(Color.ORANGE, Block.Shape.LARGE, Block.Direction.HORIZONTAL, new Rectangle(180, 0, 120, 40), false),
                new BlockConfig(Color.CYAN, Block.Shape.MEDIUM, Block.Direction.VERTICAL, new Rectangle(310, 0, 40, 80), false),
                new BlockConfig(Color.MAGENTA, Block.Shape.SMALL, Block.Direction.HORIZONTAL, new Rectangle(360, 0, 40, 40), false),
                new BlockConfig(Color.PINK, Block.Shape.SMALL, Block.Direction.VERTICAL, new Rectangle(400, 0, 40, 40), false)
        );

        for (BlockConfig cfg : blocks) {
            Block block = new Block(cfg.bounds, cfg.color, cfg.direction, cfg.shape);
            board.add(block);
            if (cfg.isTargetBlock) targetBlock = block;
        }
    }


// Metodi di salvataggio e caricamento esistenti

    public void saveGame(String name, boolean resumed) throws GamePersistenceException {
        Rectangle[] rects = ui.getCurrentBlockPositions();
        int counter = getCounter();

        int[][] positions = new int[rects.length][4];
        for (int i = 0; i < rects.length; i++) {
            positions[i][0] = rects[i].x;
            positions[i][1] = rects[i].y;
            positions[i][2] = rects[i].width;
            positions[i][3] = rects[i].height;
        }

        SavedGame game = new SavedGame(name, positions, counter, 0);
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("saves/" + name + ".dat"))) {
            out.writeObject(game);
            logger.info("✅ Partita salvata con successo: " + name);
        } catch (IOException e) {
            logger.severe("❌ Errore durante il salvataggio di '" + name + "': " + e.getMessage());
            throw new GamePersistenceException("Errore durante il salvataggio della partita", e);
        }
    }

    public void resumeState(String name) throws GamePersistenceException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT state, moves, level FROM testsetpositions WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String state = rs.getString("state");
                int moves = rs.getInt("moves");
                String level = rs.getString("level");

                // 🔁 Ricostruisci Rectangle[] dallo state
                String[] parts = state.split("],");
                List<Rectangle> rectangles = new ArrayList<>();
                for (String part : parts) {
                    part = part.replace("[", "").replace("]", "").trim();
                    if (!part.isEmpty()) {
                        String[] coords = part.split(",");
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        int w = Integer.parseInt(coords[2]);
                        int h = Integer.parseInt(coords[3]);
                        rectangles.add(new Rectangle(x, y, w, h));
                    }
                }

                // Imposta le posizioni nel model
                Rectangle[] loaded = rectangles.toArray(new Rectangle[0]);
                Color[] colors = getInitialColors(); // Puoi mantenere quelli esistenti
                setInitialState(loaded, colors);

                this.counter = moves;
                this.level = switch (level) {
                    case "easy" -> 1;
                    case "medium" -> 2;
                    case "hard" -> 3;
                    default -> 1;
                };

            } else {
                throw new GamePersistenceException("Nessun salvataggio trovato con nome: " + name);
            }

        } catch (Exception e) {
            logger.severe("❌ Errore nel caricamento: " + e.getMessage());
            throw new GamePersistenceException("Errore nel caricamento dello stato", e);
        }
    }

    public List<String> getSavedGameList() throws GamePersistenceException {
        List<String> savedGames = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name FROM testsetpositions WHERE name IS NOT NULL ORDER BY id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                savedGames.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            logger.severe("❌ Errore nel recupero dei salvataggi: " + e.getMessage());
            throw new GamePersistenceException("Errore nel recupero dei salvataggi", e);
        }

        return savedGames;
    }

    public void logout() {
        logger.info("🚪 Logout dell’utente: " + currentUser);
        currentUser = null;
        // Eventuale pulizia stato o notifiche aggiuntive
    }

    public void delUser() throws AuthenticationException {
        logger.info("🗑️ Eliminazione utente richiesta.");
        if (currentUser == null) {
            throw new AuthenticationException("Nessun utente loggato");
        }
        // Logica per eliminare l’utente dal DB
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, currentUser);
            int deleted = stmt.executeUpdate();
            if (deleted > 0) {
                logger.info("🗑️ Utente " + currentUser + " eliminato dal DB");
                currentUser = null;
            } else {
                throw new AuthenticationException("Utente non trovato nel DB");
            }
        } catch (SQLException e) {
            logger.severe("❌ Errore eliminazione utente DB: " + e.getMessage());
            throw new AuthenticationException("Errore durante eliminazione utente", e);
        }
    }


    public void login(String username, String password) throws AuthenticationException {
        logger.info("🔐 Tentativo di login per: " + username);
        this.currentUser = username;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                logger.warning("❌ Utente non trovato: " + username);
                throw new AuthenticationException("Utente non registrato");
            }

            String storedHash = rs.getString("password");
            String inputHash = PasswordUtil.hashPassword(password);

            if (!storedHash.equals(inputHash)) {
                logger.warning("❌ Password errata per utente: " + username);
                throw new AuthenticationException("Password errata");
            }

            logger.info("✅ Login riuscito per: " + username);

        } catch (SQLException e) {
            logger.severe("❌ Errore login DB: " + e.getMessage());
            throw new AuthenticationException("Errore durante il login", e);
        }
    }

    public void registration(String username, String password) throws AuthenticationException {
        logger.info("📝 Tentativo di registrazione: " + username);

        try (Connection conn = DBConnection.getConnection()) {
            String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                logger.warning("⚠️ Username già esistente: " + username);
                throw new AuthenticationException("Nome utente già registrato");
            }

            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            String hashed = PasswordUtil.hashPassword(password);

            insertStmt.setString(1, username);
            insertStmt.setString(2, hashed);
            insertStmt.executeUpdate();

            logger.info("✅ Registrazione completata per: " + username);

        } catch (SQLException e) {
            logger.severe("❌ Errore registrazione DB: " + e.getMessage());
            throw new AuthenticationException("Errore durante la registrazione", e);
        }
    }

    public void deleteSavedGame(String name) {
        // TODO
    }

    public void deleteAll() {
        // TODO
    }

    public void restartState() {
        logger.info("🔄 Stato riavviato");
    }

    public void restart(Rectangle[] rects) {
        logger.info("🔁 Riavvio board con " + rects.length + " blocchi");
        moveCounter = 0;
        restartState();
    }

    public Point getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Point point) {
        this.selectedPiece = point;
    }

    public Board getBoard() {
        return board;
    }

    public Rectangle[] getCurrentPositions() {
        return ui.getCurrentBlockPositions();
    }

    public Move generateMove() {
        return nextBestMove();
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public int getCounter() {
        return moveCounter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void incrementCounter() {
        moveCounter++;
    }

    public void makeMove(Point from, Point to) {
        moveHistory.add(MoveFactory.createMove("user", from, to));
        incrementCounter();
        selectedPiece = null;

        // ✅ Controllo vittoria dopo la mossa
        if (ui != null) {
            List<PuzzlemasterUI.BlockGoal> targets = ui.getTargetBlocks();
            Component[] currentBlocks = ui.getBoardPanel().getComponents();

            if (checkVictory(targets, currentBlocks)) {
                ui.showVictoryAnimation();
            }
        }
    }


    public Move undo() {
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.remove(moveHistory.size() - 1);
            moveCounter = Math.max(0, moveCounter - 1);
            return MoveFactory.createMove("user", lastMove.getTo(), lastMove.getFrom());
        }
        return null;
    }

    public void setStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public Move nextBestMove() {
        return strategy.nextMove(getCurrentPositions(), selectedPiece);
    }

    public void moveSelectedPiece(Point p) {
        this.selectedPiece = p;
    }

    public java.util.Iterator<Move> getMoveIterator() {
        return new MoveIterator(moveHistory);
    }

    public void saveGameToDB(String state, int moves, String level, String name) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO testsetpositions (state, moves, level, name) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, state);
            stmt.setInt(2, moves);
            stmt.setString(3, level);
            stmt.setString(4, name);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("❌ Errore salvataggio DB: " + e.getMessage());
        }
    }

    public void loadGameFromDB(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT state, moves, level FROM testsetpositions WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                logger.info("📂 Stato: " + rs.getString("state"));
                logger.info("🎯 Mosse: " + rs.getInt("moves"));
                logger.info("⚙️ Livello: " + rs.getString("level"));
            } else {
                logger.warning("⚠️ Nessuna partita trovata con ID: " + id);
            }

        } catch (Exception e) {
            logger.severe("❌ Errore caricamento DB: " + e.getMessage());
        }
    }

    public List<String> listSavedGames() {
        List<String> saved = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, level FROM testsetpositions ORDER BY id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                saved.add("ID: " + rs.getInt("id") + " | Livello: " + rs.getString("level"));
            }
        } catch (Exception e) {
            logger.severe("❌ Errore lettura DB: " + e.getMessage());
        }
        return saved;
    }

    public void deleteSavedGame(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM testsetpositions WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("🗑️ Salvataggio eliminato.");
            } else {
                logger.warning("⚠️ Nessun salvataggio con ID: " + id);
            }
        } catch (Exception e) {
            logger.severe("❌ Errore eliminazione DB: " + e.getMessage());
        }
    }

    public void sendGameToServer(String state, int moves, String level) {
        try {
            URL url = new URL(System.getProperty("server.url", "https://example.com/api/save"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format("{\"state\":\"%s\", \"moves\":%d, \"level\":\"%s\"}", state, moves, level);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                logger.info("🌍 Partita inviata al server con successo.");
            } else {
                logger.warning("⚠️ Errore risposta server: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            logger.severe("❌ Errore invio server: " + e.getMessage());
        }
    }


    public List<Move> getUserMoves() {
        return moveHistory.stream()
                .filter(m -> "user".equals(m.getType()))
                .collect(Collectors.toList());
    }

    public void setModel(PuzzlemasterModel model) {
        this.puzzlemasterModel = model;
    }

    public boolean checkVictory(List<PuzzlemasterUI.BlockGoal> targetBlocks, Component[] playerBlocks) {
        for (PuzzlemasterUI.BlockGoal goal : targetBlocks) {
            boolean matched = false;

            for (Component c : playerBlocks) {
                if (c instanceof JButton button) {
                    Rectangle playerBounds = button.getBounds();
                    Color playerColor = button.getBackground();

                    // Se la posizione e il colore coincidono
                    if (playerBounds.intersects(goal.bounds) && playerColor.equals(goal.color)) {
                        matched = true;
                        break;
                    }
                }
            }

            if (!matched) return false; // Se anche solo uno non coincide, non è vittoria
        }

        return true; // Tutti i blocchi combaciano
    }

    public Color[] getTargetColors() {
        return targetColors;  // ← usa qui il nome corretto della tua variabile
    }

    public void setTargetColors(Color[] colors) {
        this.targetColors = colors;
    }

}














