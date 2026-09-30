package com.lettercubed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieSearch {
    // Search movies by title
    public static List<Movie> searchByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE LOWER(title) LIKE LOWER(?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error searching by title: " + e.getMessage());
        }
        return movies;
    }

    // Search movies by director
    public static List<Movie> searchByDirector(String director) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE LOWER(director) LIKE LOWER(?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + director + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error searching by director: " + e.getMessage());
        }
        return movies;
    }

    // Search movies by genre
    public static List<Movie> searchByGenre(String genre) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE LOWER(genre) LIKE LOWER(?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + genre + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error searching by genre: " + e.getMessage());
        }
        return movies;
    }

    // Get movies by year
    public static List<Movie> getMoviesByYear(int year) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE year = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getString("genre"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error searching by year: " + e.getMessage());
        }
        return movies;
    }
}
