package org.example.utils.logging;

public enum LogLevel {
    INFO("\u001B[32m"),    // Yeşil
    WARN("\u001B[33m"),    // Sarı
    ERROR("\u001B[31m"),   // Kırmızı
    DEBUG("\u001B[34m");   // Mavi

    private final String colorCode;
    private static final String RESET = "\u001B[0m";

    LogLevel(String colorCode) {
        this.colorCode = colorCode;
    }

    public void print(String message) {
        System.out.println(colorCode + "[" + this.name() + "] " + message + RESET);
    }

    public void printErr(String message) {
        System.err.println(colorCode + "[" + this.name() + "] " + message + RESET);
    }

    public String format(String message) {
        return colorCode + "[" + this.name() + "] " + message + RESET;
    }
}