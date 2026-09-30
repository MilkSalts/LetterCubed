package com.lettercubed;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    public static void main(String[] args) {
        // Initialize database
        DatabaseConnection.connect();
        DatabaseConnection.initializeTables();

        // Main menu loop
        while (running) {
            CLIFormatter.clearScreen();
            CLIFormatter.printHeader("Letter Cubed - Movie Tracker");

            String[] mainMenuOptions = {
                    "Add Movie",
                    "Add Movie to Watch List",
                    "Add Review",
                    "Search Movies",
                    "View All Movies",
                    "View Watch List",
                    "View Reviews",
                    "Edit Movie",
                    "Delete Movie",
                    "Edit Watch List Status",
                    "Exit"
            };

            CLIFormatter.printMenu(mainMenuOptions);
            System.out.print("Choose an option: ");
            int choice = getIntInput();
            scanner.nextLine(); // Consume the leftover newline

            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    addToWatchList();
                    break;
                case 3:
                    addReview();
                    break;
                case 4:
                    searchMovies();
                    break;
                case 5:
                    viewAllMovies();
                    break;
                case 6:
                    viewWatchList();
                    break;
                case 7:
                    viewReviews();
                    break;
                case 8:
                    editMovie();
                    break;
                case 9:
                    deleteMovie();
                    break;
                case 10:
                    editWatchListStatus();
                    break;
                case 11:
                    running = false;
                    break;
                default:
                    CLIFormatter.printError("Invalid option. Please try again.");
            }

            if (choice != 11) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        // Close database connection
        DatabaseConnection.disconnect();
        scanner.close();
        CLIFormatter.printSuccess("Thank you for using Letter Cubed!");
    }

    private static void addMovie() {
        CLIFormatter.printHeader("Add New Movie");
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        System.out.print("Enter director: ");
        String director = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = getIntInput();

        Movie movie = new Movie(title, director, genre, year);
        movie.saveMovie();
    }

    private static void addToWatchList() {
        CLIFormatter.printHeader("Add Movie to Watch List");
        System.out.print("Enter movie ID: ");
        int movieId = getIntInput();

        WatchList watchList = new WatchList(movieId, "to watch");
        watchList.addToWatchList();
    }

    private static void addReview() {
        CLIFormatter.printHeader("Add Review");
        System.out.print("Enter movie ID: ");
        int movieId = getIntInput();
        System.out.print("Enter rating (1-5): ");
        int rating = getIntInput();
        System.out.print("Enter review text: ");
        String reviewText = scanner.nextLine();

        Review review = new Review(movieId, rating, reviewText);
        review.saveReview();
    }

    private static void searchMovies() {
        CLIFormatter.printHeader("Search Movies");
        String[] searchOptions = {
                "Search by Title",
                "Search by Director",
                "Search by Genre",
                "Get Movies by Year",
                "Back to Main Menu"
        };

        CLIFormatter.printMenu(searchOptions);
        System.out.print("Choose search option: ");
        int choice = getIntInput();
        List<Movie> results = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.print("Enter title to search: ");
                String title = scanner.nextLine();
                results = MovieSearch.searchByTitle(title);
                break;
            case 2:
                System.out.print("Enter director name: ");
                String director = scanner.nextLine();
                results = MovieSearch.searchByDirector(director);
                break;
            case 3:
                System.out.print("Enter genre: ");
                String genre = scanner.nextLine();
                results = MovieSearch.searchByGenre(genre);
                break;
            case 4:
                System.out.print("Enter year: ");
                int year = getIntInput();
                results = MovieSearch.getMoviesByYear(year);
                break;
            case 5:
                return;
            default:
                CLIFormatter.printError("Invalid option.");
                return;
        }

        displaySearchResults(results);
    }

    private static void displaySearchResults(List<Movie> movies) {
        if (movies.isEmpty()) {
            CLIFormatter.printInfo("No movies found.");
            return;
        }

        String[] headers = {"ID", "Title", "Director", "Genre", "Year"};
        String[][] data = new String[movies.size()][5];
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            data[i][0] = String.valueOf(m.getMovieId());
            data[i][1] = m.getTitle();
            data[i][2] = m.getDirector();
            data[i][3] = m.getGenre();
            data[i][4] = String.valueOf(m.getYear());
        }
        CLIFormatter.printTable(headers, data);
    }

    private static void viewAllMovies() {
        CLIFormatter.printHeader("All Movies");
        List<Movie> movies = Movie.getAllMovies();

        if (movies.isEmpty()) {
            CLIFormatter.printInfo("No movies found.");
            return;
        }

        String[] headers = {"ID", "Title", "Director", "Genre", "Year"};
        String[][] data = new String[movies.size()][5];
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            data[i][0] = String.valueOf(m.getMovieId());
            data[i][1] = m.getTitle();
            data[i][2] = m.getDirector();
            data[i][3] = m.getGenre();
            data[i][4] = String.valueOf(m.getYear());
        }
        CLIFormatter.printTable(headers, data);
    }

    private static void viewWatchList() {
        CLIFormatter.printHeader("Watch List");
        List<Object[]> watchList = WatchList.getAllWatchList();

        if (watchList.isEmpty()) {
            CLIFormatter.printInfo("Watch list is empty.");
            return;
        }

        String[] headers = {"Watch ID", "Movie ID", "Title", "Director", "Genre", "Year", "Status"};
        String[][] data = new String[watchList.size()][7];
        for (int i = 0; i < watchList.size(); i++) {
            Object[] item = watchList.get(i);
            data[i][0] = String.valueOf(item[0]);
            data[i][1] = String.valueOf(item[1]);
            data[i][2] = (String) item[2];
            data[i][3] = (String) item[3];
            data[i][4] = (String) item[4];
            data[i][5] = String.valueOf(item[5]);
            data[i][6] = (String) item[6];
        }
        CLIFormatter.printTable(headers, data);
    }

    private static void viewReviews() {
        CLIFormatter.printHeader("Reviews");
        List<Object[]> reviews = Review.getAllReviews();

        if (reviews.isEmpty()) {
            CLIFormatter.printInfo("No reviews found.");
            return;
        }

        String[] headers = {"Review ID", "Movie ID", "Title", "Rating", "Review"};
        String[][] data = new String[reviews.size()][5];
        for (int i = 0; i < reviews.size(); i++) {
            Object[] item = reviews.get(i);
            data[i][0] = String.valueOf(item[0]);
            data[i][1] = String.valueOf(item[1]);
            data[i][2] = (String) item[2];
            data[i][3] = String.valueOf(item[3]) + " ⭐";
            data[i][4] = (String) item[4];
        }
        CLIFormatter.printTable(headers, data);
    }

    private static void editMovie() {
        CLIFormatter.printHeader("Edit Movie");
        System.out.print("Enter movie ID to edit: ");
        int movieId = getIntInput();

        System.out.print("Enter new title (leave blank to keep current): ");
        String title = scanner.nextLine();
        System.out.print("Enter new director (leave blank to keep current): ");
        String director = scanner.nextLine();
        System.out.print("Enter new genre (leave blank to keep current): ");
        String genre = scanner.nextLine();
        System.out.print("Enter new year (0 to keep current): ");
        int year = getIntInput();

        Movie.editMovie(movieId, title, director, genre, year);
    }

    private static void deleteMovie() {
        CLIFormatter.printHeader("Delete Movie");
        System.out.print("Enter movie ID to delete: ");
        int movieId = getIntInput();

        Movie.deleteMovie(movieId);
    }

    private static void editWatchListStatus() {
        CLIFormatter.printHeader("Edit Watch List Status");
        System.out.print("Enter watch list ID to edit: ");
        int watchId = getIntInput();

        String[] statusOptions = {"to watch", "watching", "watched"};
        System.out.println("\nSelect new status:");
        for (int i = 0; i < statusOptions.length; i++) {
            System.out.println((i + 1) + ". " + statusOptions[i]);
        }
        System.out.print("Enter status option: ");
        int statusChoice = getIntInput();

        if (statusChoice >= 1 && statusChoice <= statusOptions.length) {
            WatchList.editWatchListStatus(watchId, statusOptions[statusChoice - 1]);
        } else {
            CLIFormatter.printError("Invalid status option.");
        }
    }

    private static int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}
