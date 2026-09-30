package com.lettercubed;

public class CLIFormatter {
    // ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    // Text formatting
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    // Clear screen
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\u001B[H\u001B[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }

    // Print header with title
    public static void printHeader(String title) {
        int width = 50;
        String border = "=".repeat(width);
        System.out.println(CYAN + border + RESET);
        int padding = (width - title.length()) / 2;
        System.out.println(CYAN + " ".repeat(padding) + BOLD + title + RESET + CYAN + " ".repeat(padding) + RESET);
        System.out.println(CYAN + border + RESET);
    }

    // Print menu options
    public static void printMenu(String[] options) {
        System.out.println();
        for (int i = 0; i < options.length; i++) {
            System.out.println(BLUE + (i + 1) + ". " + RESET + options[i]);
        }
        System.out.println();
    }

    // Color text
    public static String colorText(String text, String color) {
        return color + text + RESET;
    }

    // Print success message
    public static void printSuccess(String message) {
        System.out.println(GREEN + "✓ " + message + RESET);
    }

    // Print error message
    public static void printError(String message) {
        System.out.println(RED + "✗ " + message + RESET);
    }

    // Print info message
    public static void printInfo(String message) {
        System.out.println(YELLOW + "ℹ " + message + RESET);
    }

    // Print table
    public static void printTable(String[] headers, String[][] data) {
        int[] columnWidths = new int[headers.length];

        // Calculate column widths
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = Math.min(headers[i].length(), getMaxColumnWidth(data, i));
        }

        // Print header
        printTableRow(headers, columnWidths, BOLD + BLUE);
        printTableSeparator(columnWidths);

        // Print data rows
        for (String[] row : data) {
            printTableRow(row, columnWidths, "");
        }
    }

    private static int getMaxColumnWidth(String[][] data, int columnIndex) {
        int maxWidth = 10;
        for (String[] row : data) {
            if (columnIndex < row.length && row[columnIndex] != null) {
                maxWidth = Math.max(maxWidth, Math.min(row[columnIndex].length(), 20));
            }
        }
        return maxWidth;
    }

    private static void printTableRow(String[] row, int[] columnWidths, String color) {
        System.out.print(color);
        for (int i = 0; i < row.length; i++) {
            String cell = row[i] != null ? truncateText(row[i], columnWidths[i]) : "";
            System.out.print("| " + String.format("%-" + columnWidths[i] + "s", cell) + " ");
        }
        System.out.println("|" + RESET);
    }

    private static void printTableSeparator(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+" + "-".repeat(width + 2));
        }
        System.out.println("+");
    }

    private static String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
}
