// File: ScreenshotHelper.java
package org.example.utils.reporting;

import io.qameta.allure.Allure;
import org.example.utils.logging.LogLevel;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotHelper {

    public static void captureAndAttach(WebDriver driver, String stepName, LogLevel level) {
        if (driver == null) {
            Allure.addAttachment("[" + level + "] " + stepName + " - Screenshot", "❌ WebDriver null, screenshot alınamadı.");
            return;
        }
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("[" + level + "] " + stepName + " - Screenshot", new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            Allure.addAttachment("[" + level + "] " + stepName + " - Screenshot Error", "❌ Hata: " + e.getMessage());
        }
    }

    public static void captureAndSave(WebDriver driver, String testName) {
        if (driver == null) {
            System.err.println("❌ WebDriver null, dosyaya screenshot kaydedilemedi.");
            return;
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "screenshots/" + testName + "_" + timestamp + ".png";
        try {
            byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            try (FileOutputStream fos = new FileOutputStream(new File(filename))) {
                fos.write(imageBytes);
            }
        } catch (Exception e) {
            System.err.println("❌ Screenshot dosyası yazılamadı: " + e.getMessage());
        }
    }

    public static String captureBase64(WebDriver driver) {
        if (driver == null) return "";
        try {
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            return "data:image/png;base64," + base64Screenshot;
        } catch (Exception e) {
            System.err.println("❌ Base64 screenshot alınamadı: " + e.getMessage());
            return "";
        }
    }

    public static void attachBase64ToAllure(WebDriver driver, String stepName) {
        if (driver == null) {
            Allure.addAttachment(stepName, "❌ WebDriver null, base64 screenshot eklenemedi.");
            return;
        }
        try {
            String base64 = captureBase64(driver);
            if (!base64.isEmpty()) {
                Allure.addAttachment(stepName, "image/png", new ByteArrayInputStream(Base64.getDecoder().decode(base64.split(",")[1])), ".png");
            }
        } catch (Exception e) {
            Allure.addAttachment(stepName, "❌ Base64 ekran görüntüsü hatası: " + e.getMessage());
        }
    }

    public static void logToAllure(String message, LogLevel level) {
        Allure.addAttachment("[" + level + "] Log", message);
    }
}
