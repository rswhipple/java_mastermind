package org.rws.mastermind.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MastermindDB} class provides methods to interact with the database
 * for the Mastermind game. It includes functionality to manage player data,
 * track statistics, and retrieve leaderboards.
 */
public class MastermindDB {
    private Connection conn;

    /**
     * Constructs a {@code MastermindDB} object with the specified database file.
     *
     * @param dbFile The path to the database file.
     */
    public MastermindDB(String dbFile) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    /**
     * Closes the database connection.
     */
    public void closeDB() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database: " + e.getMessage());
            }
        }
    }

    /**
     * Creates a table in the database with the specified SQL statement.
     *
     * @param createTableSQL The SQL statement to create the table.
     */
    public void createTable(String createTableSQL) {
        executeUpdate(createTableSQL);
    }

    /**
     * Adds a player to the database with the specified name.
     *
     * @param name The name of the player.
     * @return The ID of the player added to the database, or -1 if the operation failed.
     */
    public int addPlayer(String name) {
        String sql = "INSERT INTO players(name) VALUES(?)";
        return executeUpdateWithParams(sql, name);
    }

    /**
     * Increments the win count for the player with the specified ID.
     *
     * @param playerId The ID of the player.
     */
    public void incrementWins(int playerId) {
        String sql = "UPDATE players SET wins = wins + 1 WHERE id = ?";
        executeUpdateWithParams(sql, playerId);
    }

    /**
     * Increments the loss count for the player with the specified ID.
     *
     * @param playerId The ID of the player.
     */
    public int incrementLosses(int playerId) {
        String sql = "UPDATE players SET losses = losses + 1 WHERE id = ?";
        return executeUpdateWithParams(sql, playerId);
    }

    /**
     * Finds a player with the specified name.
     *
     * @param name The name of the player.
     * @return A list of player data.
     */
    public List<String> findPlayer(String name) {
        String sql = "SELECT * FROM players WHERE name = ?";
        return executeQuery(sql, name);
    }

    /**
     * Gets the win count for the player with the specified ID.
     *
     * @param playerId The ID of the player.
     * @return The win count for the player, or 0 if not found.
     */
    public int getWinCount(int playerId) {
        String sql = "SELECT wins FROM players WHERE player_id = ?";
        return executeScalarQuery(sql, playerId);
    }

    /**
     * Gets the loss count for the player with the specified ID.
     *
     * @param playerId The ID of the player.
     * @return The loss count for the player, or 0 if not found.
     */
    public int getLossCount(int playerId) {
        String sql = "SELECT wins FROM players WHERE player_id = ?";
        return executeScalarQuery(sql, playerId);
    }

    /**
     * Retrieves the leaderboard with the specified number of top players.
     *
     * @param limit The maximum number of players to include in the leaderboard.
     * @return A list of strings containing player names and their statistics.
     */
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

    // Private helper methods

    /**
     * Executes an update query with the specified SQL statement and parameters.
     *
     * @param sql    The SQL statement to execute.
     * @param params The parameters for the query.
     * @return The ID of the generated key, or -1 if the operation failed.
     */
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

    /**
     * Executes a query with the specified SQL statement and parameters.
     *
     * @param sql    The SQL statement to execute.
     * @param params The parameters for the query.
     * @return A list of strings representing the results of the query.
     */
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

    /**
     * Executes a scalar query with the specified SQL statement and parameters.
     *
     * @param sql    The SQL statement to execute.
     * @param params The parameters for the query.
     * @return An integer representing the first column of the first row in the result set, or 0 if none.
     */
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

    /**
     * Sets the parameters for a prepared statement.
     *
     * @param pstmt  The prepared statement.
     * @param params The parameters to set.
     * @throws SQLException If an error occurs while setting the parameters.
     */
    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * Executes a SQL update query.
     *
     * @param sql The SQL statement to execute.
     */
    private void executeUpdate(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
}

