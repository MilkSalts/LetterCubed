package com.lettercubed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WatchList {
    private int watchId;
    private int movieId;
    private String status; // "to watch", "watching", "watched"

    public WatchList(int watchId, int movieId, String status) {
        this.watchId = watchId;
        this.movieId = movieId;
        this.status = status;
    }

    public WatchList(int movieId, String status) {
        this.movieId = movieId;
        this.status = status;
    }

    // Getters
    public int getWatchId() {
        return watchId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getStatus() {
        return status;
    }

    // Add movie to watch list
    public void addToWatchList() {
        String sql = "INSERT INTO watchlist (movie_id, status) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.movieId);
            pstmt.setString(2, this.status != null ? this.status : "to watch");
            pstmt.executeUpdate();
            CLIFormatter.printSuccess("Movie added to watch list!");
        } catch (SQLException e) {
            CLIFormatter.printError("Error adding to watch list: " + e.getMessage());
        }
    }

    // Get all watch list items with movie details
    public static List<Object[]> getAllWatchList() {
        List<Object[]> watchList = new ArrayList<>();
        String sql = "SELECT w.watch_id, m.movie_id, m.title, m.director, m.genre, m.year, w.status FROM watchlist w JOIN movies m ON w.movie_id = m.movie_id";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                watchList.add(new Object[]{
                        rs.getInt("watch_id"),
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getInt("year"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error retrieving watch list: " + e.getMessage());
        }
        return watchList;
    }

    // Edit watch list status
    public static void editWatchListStatus(int watchId, String newStatus) {
        String sql = "UPDATE watchlist SET status = ? WHERE watch_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, watchId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                CLIFormatter.printSuccess("Watch list status updated to: " + newStatus);
            } else {
                CLIFormatter.printError("No watch list entry found with ID: " + watchId);
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error updating watch list: " + e.getMessage());
        }
    }

    // Delete from watch list
    public static void deleteFromWatchList(int watchId) {
        String sql = "DELETE FROM watchlist WHERE watch_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, watchId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                CLIFormatter.printSuccess("Removed from watch list!");
            } else {
                CLIFormatter.printError("No watch list entry found with ID: " + watchId);
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error deleting from watch list: " + e.getMessage());
        }
    }
}
