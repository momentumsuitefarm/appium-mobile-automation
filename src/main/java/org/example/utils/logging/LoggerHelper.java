package org.example.utils.logging;

import io.qameta.allure.Allure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerHelper {

    private static final String LOG_BASE_DIR = "logs";
    private static final ThreadLocal<String> logFileName = new ThreadLocal<>();

    public static void startTestLog(String testName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filePath = LOG_BASE_DIR + File.separator + testName + "_" + timestamp + ".log";
        logFileName.set(filePath);
        log(LogLevel.INFO, "[START] Test: " + testName);
    }

    public static void endTestLog(String testName) {
        log(LogLevel.INFO, "[END] Test: " + testName);
        logFileName.remove();
    }

    public static void log(LogLevel level, String message) {
        String fullMessage = String.format("[%s] %s", level.name(), message);

        // Allure'a ekle
        Allure.step(fullMessage);
        Allure.addAttachment("[" + level.name() + "]", message);

        // Konsola yaz
        if (level == LogLevel.ERROR) {
            level.printErr(message);
        } else {
            level.print(message);
        }

        // Dosyaya yaz
        writeToFile(fullMessage, level);
    }

    private static void writeToFile(String message, LogLevel level) {
        try {
            String path = logFileName.get();
            if (path == null) return;

            File file = new File(path);
            file.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                writer.write(String.format("%s [%s] %s%n", timestamp, level.name(), message));
            }
        } catch (IOException e) {
            System.err.println("[LOGGER ERROR] Failed to write log: " + e.getMessage());
        }
    }
}