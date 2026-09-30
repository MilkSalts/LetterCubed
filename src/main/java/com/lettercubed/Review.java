package com.lettercubed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Review {
    private int reviewId;
    private int movieId;
    private int rating;
    private String reviewText;

    public Review(int reviewId, int movieId, int rating, String reviewText) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Review(int movieId, int rating, String reviewText) {
        this.movieId = movieId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    // Getters
    public int getReviewId() {
        return reviewId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    // Save review
    public void saveReview() {
        String sql = "INSERT INTO reviews (movie_id, rating, review_text) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.movieId);
            pstmt.setInt(2, this.rating);
            pstmt.setString(3, this.reviewText);
            pstmt.executeUpdate();
            CLIFormatter.printSuccess("Review added successfully!");
        } catch (SQLException e) {
            CLIFormatter.printError("Error saving review: " + e.getMessage());
        }
    }

    // Get all reviews with movie details
    public static List<Object[]> getAllReviews() {
        List<Object[]> reviews = new ArrayList<>();
        String sql = "SELECT r.review_id, r.movie_id, m.title, r.rating, r.review_text FROM reviews r JOIN movies m ON r.movie_id = m.movie_id";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reviews.add(new Object[]{
                        rs.getInt("review_id"),
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getInt("rating"),
                        rs.getString("review_text")
                });
            }
        } catch (SQLException e) {
            CLIFormatter.printError("Error retrieving reviews: " + e.getMessage());
        }
        return reviews;
    }
}
