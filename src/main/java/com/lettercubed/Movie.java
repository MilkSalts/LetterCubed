package com.lettercubed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private String director;
    private String genre;
    private int year;

    public Movie(int movieId, String title, String director, String genre, int year) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
    }

    public Movie(String title, String director, String genre, int year) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    // Save movie to database
    public void saveMovie() {
        String sql = "INSERT INTO movies (title, director, genre, year) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.title);
            pstmt.setString(2, this.director);
            pstmt.setString(3, this.genre);
            pstmt.setInt(4, this.year);
            pstmt.executeUpdate();
            CLIFormatter.printSuccess("Movie added successfully!");
        } catch (SQLException e) {
            CLIFormatter.printError("Error saving movie: " + e.getMessage());
        }
    }

    // Get all movies
    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
            CLIFormatter.printError("Error retrieving movies: " + e.getMessage());
        }
        return movies;
    }

    // Edit movie
    public static void editMovie(int movieId, String title, String director, String genre, int year) {
        String sqlSelect = "SELECT title, director, genre, year FROM movies WHERE movie_id = ?";
        String sqlUpdate = "UPDATE movies SET title = ?, director = ?, genre = ?, year = ? WHERE movie_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(sqlSelect)) {
            selectStmt.setInt(1, movieId);
            ResultSet rs = selectStmt.executeQuery();

            String finalTitle = title;
            String finalDirector = director;
            String finalGenre = genre;
            int finalYear = year;

            if (rs.next()) {
                if (title == null || title.isEmpty()) finalTitle = rs.getString("title");
                if (director == null || director.isEmpty()) finalDirector = rs.getString("director");
                if (genre == null || genre.isEmpty()) finalGenre = rs.getString("genre");
                if (year == 0) finalYear = rs.getInt("year");
            }

            try (PreparedStatement updateStmt = connection.prepareStatement(sqlUpdate)) {
                updateStmt.setString(1, finalTitle);
                updateStmt.setString(2, finalDirector);
                updateStmt.setString(3, finalGenre);
                updateStmt.setInt(4, finalYear);
                updateStmt.setInt(5, movieId);
                int affectedRows = updateStmt.executeUpdate();

                if (affectedRows > 0) {
                    CLIFormatter.printSuccess("Movie updated successfully!");
                } else {
                    CLIFormatter.printError("No movie found with ID: " + movieId);
                }
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error updating movie: " + e.getMessage());
        }
    }

    // Delete movie
    public static void deleteMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                CLIFormatter.printSuccess("Movie deleted successfully!");
            } else {
                CLIFormatter.printError("No movie found with ID: " + movieId);
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error deleting movie: " + e.getMessage());
        }
    }
}
