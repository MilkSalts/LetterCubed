# Letter Cubed - Movie Tracker CLI

A console-based movie tracker application inspired by Letterboxd. Built with Java, Maven, and SQLite.

## Features

### Core Features (Part 1)
- ✅ Add movies with title, director, genre, and year
- ✅ Manage a watch list of movies
- ✅ Write and rate reviews (1-5 stars)
- ✅ View all movies
- ✅ View watch list
- ✅ View reviews
- ✅ SQLite database with proper schema and relationships

### Enhanced Features (Part 2)
- ✅ **Edit Movies**: Update movie details
- ✅ **Delete Movies**: Remove movies from database
- ✅ **Search Functionality**: 
  - Search by title
  - Search by director
  - Search by genre
  - Filter by year
- ✅ **Improved UI**: 
  - Better text rendering with ANSI colors
  - Clear screen functionality
  - Table formatting for data display
  - Color-coded messages (success, error, info)
- ✅ **Watch List Management**:
  - Change watch list status: "to watch" → "watching" → "watched"
  - Delete from watch list

## Project Structure

```
LetterCubed/
├── src/main/java/com/lettercubed/
│   ├── Main.java                 # Main menu and CLI interface
│   ├── DatabaseConnection.java   # Database initialization and connection
│   ├── Movie.java                # Movie class with CRUD operations
│   ├── WatchList.java            # Watch list management
│   ├── Review.java               # Review management
│   ├── MovieSearch.java          # Search functionality
│   └── CLIFormatter.java         # UI formatting utility
├── pom.xml                       # Maven configuration
└── README.md                     # This file
```

## Technology Stack

- **Language**: Java 22
- **Build Tool**: Maven
- **Database**: SQLite with JDBC
- **UI**: ANSI color codes for terminal formatting

## Getting Started

### Prerequisites
- Java 22 or higher
- Maven 3.6 or higher

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/MilkSalts/LetterCubed.git
   cd LetterCubed
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.lettercubed.Main"
   ```

## Database Schema

### Movies Table
```sql
CREATE TABLE movies (
  movie_id INTEGER PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  director TEXT,
  genre TEXT,
  year INTEGER
);
```

### WatchList Table
```sql
CREATE TABLE watchlist (
  watch_id INTEGER PRIMARY KEY AUTOINCREMENT,
  movie_id INTEGER NOT NULL,
  status TEXT DEFAULT 'to watch',
  FOREIGN KEY(movie_id) REFERENCES movies(movie_id)
);
```

### Reviews Table
```sql
CREATE TABLE reviews (
  review_id INTEGER PRIMARY KEY AUTOINCREMENT,
  movie_id INTEGER NOT NULL,
  rating INTEGER,
  review_text TEXT,
  FOREIGN KEY(movie_id) REFERENCES movies(movie_id)
);
```

## Usage

The application features an interactive CLI menu with the following options:

1. **Add Movie** - Create a new movie entry
2. **Add Movie to Watch List** - Add a movie to your watch list
3. **Add Review** - Write a review for a movie
4. **Search Movies** - Search by title, director, genre, or year
5. **View All Movies** - Display all movies in a table
6. **View Watch List** - Display your watch list
7. **View Reviews** - Display all reviews
8. **Edit Movie** - Update movie details
9. **Delete Movie** - Remove a movie from database
10. **Edit Watch List Status** - Change movie status (to watch/watching/watched)
11. **Exit** - Close the application

## Object-Oriented Principles

This project demonstrates key OOP concepts:

- **Encapsulation**: Private fields with getter methods
- **Separation of Concerns**: Each class has a single responsibility
  - `Movie`: Movie data management
  - `WatchList`: Watch list tracking
  - `Review`: Review management
  - `MovieSearch`: Search functionality
  - `CLIFormatter`: UI formatting
  - `DatabaseConnection`: Database operations
- **Modularity**: Reusable methods and components
- **Inheritance & Polymorphism**: Foundation for extensibility

## Future Enhancements

- User authentication system
- Movie ratings and recommendations
- Export watch list to CSV
- User profiles and social sharing
- API integration for movie data
- Pagination for large datasets
- Advanced filtering options

## License

MIT License - Feel free to use this project for learning and development.

## Author

Created as a tutorial project by Gabe and enhanced by Yan.
