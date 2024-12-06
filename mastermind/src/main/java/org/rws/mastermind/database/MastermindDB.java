package org.rws.mastermind.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MastermindDB {
    private Connection conn;

    public MastermindDB(String dbFile) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
            stmt.close();
            System.out.println("Connected to SQLite database at " + dbFile);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void closeDB() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database: " + e.getMessage());
            }
        }
        closeDB();
    }

    public void createTable(String createTableSQL) {
        executeUpdate(createTableSQL);
    }

    public int addPlayer(String name) {
        String sql = "INSERT INTO players(name) VALUES(?)";
        return executeUpdateWithParams(sql, name);
    }

    public int addGame(int playerId, String duration, int score) {
        String sql = "INSERT INTO games(player_id, duration, score) VALUES(?,?,?)";
        return executeUpdateWithParams(sql, playerId, duration, score);
    }

    public void incrementWins(int playerId) {
        String sql = "UPDATE players SET wins = wins + 1 WHERE id = ?";
        executeUpdateWithParams(sql, playerId);
    }

    public int incrementLosses(int playerId) {
        String sql = "UPDATE players SET losses = losses + 1 WHERE id = ?";
        return executeUpdateWithParams(sql, playerId);
    }

    public List<String> findPlayer(String name) {
        String sql = "SELECT * FROM players WHERE name = ?";
        return executeQuery(sql, name);
    }

    public int getWinCount(int playerId) {
        String sql = "SELECT wins FROM players WHERE player_id = ?";
        return executeScalarQuery(sql, playerId);
    }

    public int getLossCount(int playerId) {
        String sql = "SELECT wins FROM players WHERE player_id = ?";
        return executeScalarQuery(sql, playerId);
    }

    public List<String> getLeaderboard(int limit) {
        String sql = "SELECT name, wins, losses FROM players ORDER BY wins DESC, losses ASC LIMIT ?";
        List<String> leaderboard = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String playerData = String.format("Name: %s, Wins: %d, Losses: %d",
                                                      rs.getString("name"),
                                                      rs.getInt("wins"),
                                                      rs.getInt("losses"));
                    leaderboard.add(playerData);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return leaderboard;
    }

    private int executeUpdateWithParams(String sql, Object... params) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(pstmt, params);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
        }
        return -1;
    }

    private List<String> executeQuery(String sql, Object... params) {
        List<String> results = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(rs.getString(1)); // Adjust based on column indexes
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        return results;
    }

    private int executeScalarQuery(String sql, Object... params) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing scalar query: " + e.getMessage());
        }
        return 0;
    }

    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    private void executeUpdate(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
}

