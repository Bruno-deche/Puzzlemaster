package test;

import java.sql.*;

public class DBConnectionTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/puzzlemaster";
        String user = "root";
        String password = "";

        try {
            // ✅ Registra esplicitamente il driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connessione al database riuscita!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, moves, level FROM testsetpositions");

            System.out.println("📂 Salvataggi trovati nel database:");
            while (rs.next()) {
                int id = rs.getInt("id");
                int moves = rs.getInt("moves");
                String level = rs.getString("level");
                System.out.println("🟢 ID: " + id + " | Mosse: " + moves + " | Livello: " + level);
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC non trovato: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Errore connessione o query: " + e.getMessage());
        }
    }
}


