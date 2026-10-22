package com.lettercubed;

public abstract class Formatter {
    private static Formatter activeFormatter = new CLIFormatter();

    public static void setActiveFormatter(Formatter formatter) {
        if (formatter != null) {
            activeFormatter = formatter;
        }
    }

    public static Formatter getActiveFormatter() {
        return activeFormatter;
    }

    public abstract String formatSuccess(String message);
    public abstract String formatError(String message);
    public abstract String formatInfo(String message);

    public static void printSuccess(String message) {
        System.out.println(getActiveFormatter().formatSuccess(message));
    }

    public static void printError(String message) {
        System.out.println(getActiveFormatter().formatError(message));
    }

    public static void printInfo(String message) {
        System.out.println(getActiveFormatter().formatInfo(message));
    }
}
