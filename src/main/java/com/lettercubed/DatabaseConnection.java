package com.lettercubed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:lettercubed.db";
    private static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static void initializeTables() {
        try (Statement statement = connection.createStatement()) {
            // Create Movie table
            String createMovieTable = "CREATE TABLE IF NOT EXISTS movies (" +
                    "movie_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "director TEXT," +
                    "genre TEXT," +
                    "year INTEGER)";
            statement.execute(createMovieTable);

            // Create WatchList table
            String createWatchListTable = "CREATE TABLE IF NOT EXISTS watchlist (" +
                    "watch_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "movie_id INTEGER NOT NULL," +
                    "status TEXT DEFAULT 'to watch'," +
                    "FOREIGN KEY(movie_id) REFERENCES movies(movie_id))";
            statement.execute(createWatchListTable);

            // Create Review table
            String createReviewTable = "CREATE TABLE IF NOT EXISTS reviews (" +
                    "review_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "movie_id INTEGER NOT NULL," +
                    "rating INTEGER," +
                    "review_text TEXT," +
                    "FOREIGN KEY(movie_id) REFERENCES movies(movie_id))";
            statement.execute(createReviewTable);

            System.out.println("Tables initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Error initializing tables: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}
