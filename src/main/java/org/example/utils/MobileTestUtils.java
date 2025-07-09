package org.example.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.example.helpers.auth.TwoFactorHandler;
import org.example.helpers.notifications.SlackReader;
import org.example.utils.logging.LogLevel;
import org.example.utils.logging.LoggerHelper;
import org.example.utils.reporting.ScreenshotHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.example.utils.reporting.ScreenshotHelper.captureAndAttach;

public class MobileTestUtils extends SlackReader {
    private static final Logger log = LoggerFactory.getLogger(MobileTestUtils.class);

    private final AppiumDriver driver;
    public MobileTestUtils(AppiumDriver driver) {
        this.driver = driver;
    }
    public String generateRandomUsername() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = new Random().nextInt(6) + 5;
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < length; i++) {
            username.append(chars.charAt(new Random().nextInt(chars.length())));
        }
        return username.toString();
    }
    private int getDeviceIndex() {
        try {
            String indexStr = System.getenv("device_index");
            return (indexStr != null) ? Integer.parseInt(indexStr) : 0;
        } catch (Exception e) {
            System.err.println("⚠️ device_index env değişkeni okunamadı. Varsayılan olarak 0 kabul ediliyor.");
            return 0;
        }
    }
    public void tapCenter(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Point location = element.getLocation();
            int centerX = location.getX() + element.getSize().getWidth() / 2;
            int centerY = location.getY() + element.getSize().getHeight() / 2;

            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(tap));
            captureAndAttach(driver, " tapCenter", LogLevel.INFO);
            System.out.println("Tap Success " + locator);
        } catch (Exception e) {
            System.err.println("❌ Tap failed: " + locator);
        }
    }
    public boolean clickIfExists(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            captureAndAttach(driver, "clickIfExists: " + locator.toString(), LogLevel.INFO);
            return true;
        } catch (TimeoutException e) {
            System.err.println("❌ Element not clickable within timeout: " + locator);
            captureAndAttach(driver, "clickIfExists: ", LogLevel.ERROR);
        } catch (NoSuchElementException e) {
            System.err.println("❌ Element not found: " + locator);
            captureAndAttach(driver, "clickIfExists: " , LogLevel.ERROR);
        } catch (Exception e) {
            System.err.println("❌ Unexpected error clicking element " + locator + ": " + e.getMessage());
            captureAndAttach(driver, "clickIfExists: ", LogLevel.ERROR);
        }
        return false;
    }
    public void waitAndClick(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("Tıklama İşlemi Başarılı"  + " : " + locator);
            captureAndAttach(driver, "...", LogLevel.INFO);

        } catch (Exception e) {
            throw new RuntimeException("❌ Element not clickable: " + locator, e);
        }
    }
    public void safeClickOneOf(List<By> locators) {
        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                waitAndClick(locator, 5);
                return;
            }
        }
        Assert.fail("❌ Hiçbir element bulunamadı veya görünür değil: " + locators.toString());
    }
    public boolean waitUntilVisible(By locator, int timeoutInSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("✅ Element bulundu: " + locator);
            return true;
        } catch (TimeoutException e) {
            System.err.println("⏰ Element görünmedi (timeout): " + locator);
        } catch (Exception e) {
            System.err.println("❌ Hata oluştu: " + locator + " | " + e.getMessage());
        }
        return false;
    }
    private final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    public enum SwipeDirection {
        RIGHT, LEFT, UP, DOWN
    }
    public void handle2FAIfPresent() {
        TwoFactorHandler.handleIfVisible(this.driver);
    }
    public void inputValue(By locator, String value, int timeoutInSeconds) throws Exception {
        WebElement element = null;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeoutInSeconds * 1000L;

        while (System.currentTimeMillis() < endTime) {
            try {
                element = driver.findElement(locator);
                if (element.isDisplayed() || element.isEnabled()) {
                    element.click(); // tıklanabilir olduğunda tıkla
                    break;
                }
            } catch (Exception ignored) {
                // Element henüz hazır değilse devam et
            }
            Thread.sleep(500);
        }
        captureAndAttach(driver, "Döngü Sonu Input Value", LogLevel.INFO);


        if (element == null || !(element.isDisplayed() || element.isEnabled())) {
            throw new Exception("❌ Element not found or not interactable within " + timeoutInSeconds + " seconds: " + locator);
        }

        try {
            element.clear();
            element.sendKeys(value);
            System.out.println("✅ inputValue: " + locator + " = " + value);
            captureAndAttach(driver, "inputValue-success", LogLevel.INFO);
        } catch (Exception e) {
            captureAndAttach(driver, "inputValue-failure", LogLevel.ERROR);
            throw new Exception("❌ Failed to send keys to: " + locator, e);
        }
    }
    public void waitForVisibility(By locator, int timeoutInSeconds) throws Exception {
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                if (driver.findElement(locator).isDisplayed()) {
                    System.out.println("✅ waitForVisibility: " + locator);
                    return;
                }
            } catch (Exception ignored) {}
            Thread.sleep(500);
        }
        throw new Exception("❌ Element not visible: " + locator);
    }
    public void waitForEnabled(By locator, int timeoutInSeconds) throws Exception {
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                if (driver.findElement(locator).isEnabled()) {
                    System.out.println("✅ waitForEnabled: " + locator);
                    return;
                }
            } catch (Exception ignored) {}
            Thread.sleep(500);
        }
        throw new Exception("❌ Element not enabled: " + locator);
    }
    public void swipeUntilFound( By locator, String direction, int maxSwipes ) throws Exception {
        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    element.click();
                    System.out.println("✅ swipeUntilFound: " + locator);
                    return;
                }
            } catch (Exception ignored) {}

            swipe(SwipeDirection.valueOf(direction),maxSwipes);
            Thread.sleep(1000);
        }
        throw new Exception("❌ Element not found after swiping: " + locator);
    }
    public WebElement waitForElement(By locator, int timeoutInSeconds) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(driver -> {
                WebElement el = driver.findElement(locator);
                return (el.isDisplayed() || el.isEnabled()) ? el : null;
            });
            System.out.println("✅ waitForElement: " + locator);
            captureAndAttach(driver, "WaitFor Element" , LogLevel.INFO);

            return element;
        } catch (TimeoutException e) {
            throw new Exception("❌ Element not ready: " + locator, e);
        }
    }
    public void clickWithDelay(By locator, int delayTimeInMillis, int timeoutInMillis) throws Exception {
        int deviceIndex = 0;
        try {
            String indexStr = System.getenv("device_index");
            if (indexStr != null) {
                deviceIndex = Integer.parseInt(indexStr);
            }
        } catch (Exception e) {
            System.err.println("⚠️ device_index env değişkeni okunamadı. Varsayılan olarak 0 kabul ediliyor.");
        }

        long delay = (long) deviceIndex * delayTimeInMillis;
        Thread.sleep(delay);

        Instant endTime = Instant.now().plusMillis(timeoutInMillis);

        while (Instant.now().isBefore(endTime)) {
            try {
                WebElement el = driver.findElement(locator);
                if (el.isDisplayed() && el.isEnabled()) {
                    el.click();
                    captureAndAttach(driver, "Click With Delay" , LogLevel.INFO);

                    System.out.printf("[%s] ✅ Device %d clicked (with delay): %s\n", Instant.now(), deviceIndex, locator);
                    return;
                }
            } catch (NoSuchElementException | ElementClickInterceptedException ignored) {}
            Thread.sleep(500);
        }
        captureAndAttach(driver, "Click With Delay" , LogLevel.INFO);

        throw new Exception(String.format("❌ Device %d could not click within timeout: %s", deviceIndex, locator));
    }
    /*
    public void swipe(SwipeDirection direction, int count) {
        try {
            // Get screen dimensions using a more reliable method
            Dimension screenSize = null;
            int maxAttempts = 5;
            int attempt = 0;
            
            while (attempt < maxAttempts && screenSize == null) {
                try {
                    // Try multiple methods to get screen dimensions
                    try {
                        screenSize = driver.manage().window().getSize();
                    } catch (Exception e) {
                        log.warn("Failed to get window size, trying alternative method: {}", e.getMessage());
                        // Try alternative method using JavaScript
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        Object result = js.executeScript(
                            "return {width: window.innerWidth, height: window.innerHeight};"
                        );
                        
                        if (result instanceof Map) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> dimensions = (Map<String, Object>) result;
                            Object widthObj = dimensions.get("width");
                            Object heightObj = dimensions.get("height");
                            
                            if (widthObj instanceof Number && heightObj instanceof Number) {
                                screenSize = new Dimension(
                                    ((Number) widthObj).intValue(),
                                    ((Number) heightObj).intValue()
                                );
                            }
                        }
                    }
                    
                    if (screenSize != null && screenSize.getWidth() > 0 && screenSize.getHeight() > 0) {
                        break;
                    }
                } catch (Exception e) {
                    log.warn("Attempt {} failed to get screen dimensions: {}", attempt + 1, e.getMessage());
                }
                
                attempt++;
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted while waiting for screen dimensions", ie);
                    }
                }
            }
            
            if (screenSize == null || screenSize.getWidth() <= 0 || screenSize.getHeight() <= 0) {
                throw new RuntimeException("Could not get valid screen dimensions after " + maxAttempts + " attempts");
            }

            log.info("Successfully got screen dimensions: {}x{}", screenSize.getWidth(), screenSize.getHeight());
            
            // Calculate start and end points based on screen dimensions
            int startX, startY, endX, endY;
            int width = screenSize.getWidth();
            int height = screenSize.getHeight();
            
            switch (direction) {
                case UP:
                    startX = width / 2;
                    startY = (int) (height * 0.8);
                    endX = width / 2;
                    endY = (int) (height * 0.2);
                    break;
                case DOWN:
                    startX = width / 2;
                    startY = (int) (height * 0.2);
                    endX = width / 2;
                    endY = (int) (height * 0.8);
                    break;
                case LEFT:
                    startX = (int) (width * 0.8);
                    startY = height / 2;
                    endX = (int) (width * 0.2);
                    endY = height / 2;
                    break;
                case RIGHT:
                    startX = (int) (width * 0.2);
                    startY = height / 2;
                    endX = (int) (width * 0.8);
                    endY = height / 2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction: " + direction);
            }
            
            log.info("Performing swipe {} from ({},{}) to ({},{})", direction, startX, startY, endX, endY);
            
            // Perform the swipe count times
            for (int i = 0; i < count; i++) {
                // Create W3C Actions for swipe
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);
                
                // Move to start position
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                // Press down
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                // Wait
                swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                // Move to end position
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
                // Release
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                
                // Perform the swipe
                driver.perform(Collections.singletonList(swipe));
                
                if (i < count - 1) {
                    try {
                        Thread.sleep(500); // Wait between swipes
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted while waiting between swipes", ie);
                    }
                }
            }
            
            log.info("Swipe completed successfully {} times", count);
            
        } catch (Exception e) {
            log.error("Failed to perform swipe: {}", e.getMessage());
            throw new RuntimeException("Failed to perform swipe: " + e.getMessage(), e);
        }
    }
    */

    public void swipe(SwipeDirection direction, int count) {
        final int maxAttempts = 5;
        Dimension screenSize = null;

        // Ekran boyutunu stabilize şekilde almaya çalış
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                screenSize = driver.manage().window().getSize();
                if (screenSize.getWidth() > 0 && screenSize.getHeight() > 0) {
                    break;
                }
            } catch (Exception e) {
                log.warn("Attempt {}: Cannot get screen size, retrying...", attempt);
            }

            if (attempt < maxAttempts) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while retrying to get screen size", ie);
                }
            }
        }

        if (screenSize == null || screenSize.getWidth() <= 0 || screenSize.getHeight() <= 0) {
            throw new RuntimeException("Failed to get valid screen dimensions after retries");
        }

        log.info("Screen dimensions: {} x {}", screenSize.getWidth(), screenSize.getHeight());

        int width = screenSize.getWidth();
        int height = screenSize.getHeight();
        int startX, startY, endX, endY;

        switch (direction) {
            case UP:
                startX = width / 2;
                startY = (int) (height * 0.8);
                endX = width / 2;
                endY = (int) (height * 0.2);
                break;
            case DOWN:
                startX = width / 2;
                startY = (int) (height * 0.2);
                endX = width / 2;
                endY = (int) (height * 0.8);
                break;
            case LEFT:
                startX = (int) (width * 0.8);
                startY = height / 2;
                endX = (int) (width * 0.2);
                endY = height / 2;
                break;
            case RIGHT:
                startX = (int) (width * 0.2);
                startY = height / 2;
                endX = (int) (width * 0.8);
                endY = height / 2;
                break;
            default:
                throw new IllegalArgumentException("Unknown swipe direction: " + direction);
        }

        log.info("Swipe {} from ({}, {}) to ({}, {})", direction, startX, startY, endX, endY);

        for (int i = 0; i < count; i++) {
            try {
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);

                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));

                if (i < count - 1) {
                    Thread.sleep(300);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Swipe interrupted", ie);
            } catch (Exception e) {
                log.error("Swipe failed at attempt {}: {}", i + 1, e.getMessage());
                throw new RuntimeException("Swipe failed: " + e.getMessage(), e);
            }
        }

        log.info("Swipe {} completed {} time(s)", direction, count);
    }

    public void clickRandomElement(List<By> locators) throws Exception {
        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty()) {
                    WebElement randomElement = elements.get((int) (Math.random() * elements.size()));
                    if (randomElement.isDisplayed() && randomElement.isEnabled()) {
                        randomElement.click();
                        ScreenshotHelper.captureAndAttach(driver, "clickRandomElement", LogLevel.INFO);
                        System.out.println("✅ Random element clicked: " + locator);
                        return;
                    }
                }
            } catch (Exception ignored) {}
        }
        throw new Exception("No clickable random element found from list.");
    }
    public void assertElementText(By locator, String expectedText) throws Exception {
        WebElement element = driver.findElement(locator);
        String actualText = element.getText();
        System.out.println("Actual Text: " + actualText);

        if (!actualText.contains(expectedText)) {
            throw new AssertionError("Text assertion failed. Expected to contain: " + expectedText + ", but got: " + actualText);
        }
        captureAndAttach(driver, "Assertion" , LogLevel.INFO);

        System.out.println("✅ Text assertion passed: " + actualText);
    }
    public void restartApp(IOSDriver driver, String bundleId) {
        try {
            driver.terminateApp(bundleId);
            System.out.println("✅ Uygulama kapatıldı: " + bundleId);
            Thread.sleep(1000);
            captureAndAttach(driver, "App Kapatıldı", LogLevel.INFO);
            driver.activateApp(bundleId);
            System.out.println("✅ Uygulama yeniden başlatıldı: " + bundleId);
            Thread.sleep(2000);
            captureAndAttach(driver, "App Yeniden Başlatıldı", LogLevel.INFO);

        } catch (Exception e) {
            System.err.println("❌ restartApp hatası: " + e.getMessage());
        }
    }
    public void performSubscription() {
        try {
            int deviceIndex = 0;
            try {
                String indexStr = System.getenv("device_index");
                if (indexStr != null) {
                    deviceIndex = Integer.parseInt(indexStr);
                    System.out.printf("ℹ️ Device index set to %d from env.\n", deviceIndex);
                }
            } catch (Exception e) {
                System.err.println("⚠️ device_index env değişkeni okunamadı. Varsayılan olarak 0 kabul ediliyor.");
            }

            By subscribeBtn = AppiumBy.accessibilityId("Subscribe");
            By purchaseBtn = AppiumBy.accessibilityId("Purchase");

            By buttonToUse = waitUntilVisible(subscribeBtn, 5) ? subscribeBtn
                    : waitUntilVisible(purchaseBtn, 5) ? purchaseBtn
                    : null;

            if (buttonToUse != null) {
                System.out.println("🟢 'Subscribe/Purchase' butonu bulundu ve tıklanıyor...");
                waitAndClick(buttonToUse, 5);

                By pwdFieldLocator = AppiumBy.xpath("//*[contains(@type,'XCUIElementTypeSecureTextField')]");
                WebElement pwdField = driver.findElement(pwdFieldLocator);
                pwdField.sendKeys("S.tudio1234");
                System.out.println("🔐 Şifre alanı dolduruldu.");

                if (deviceIndex > 0) {
                    int delay = deviceIndex * 15000;
                    System.out.printf("⏱ Device %d için %d ms gecikme uygulanıyor.\n", deviceIndex, delay);
                    Thread.sleep(delay);
                }

                By signInLocator = AppiumBy.xpath("//XCUIElementTypeButton[@name='Sign In']");
                waitAndClick(signInLocator, 10);
                Thread.sleep(500);
                captureAndAttach(driver, "Sing In Tıklandı", LogLevel.INFO);
                System.out.println("📲 İki faktör doğrulama kontrol ediliyor...");
                this.handle2FAIfPresent();
            } else {
                System.out.printf("⚠️ Device %d: Subscribe veya Purchase elementi görünür değil.\n", deviceIndex);
            }
        } catch (Exception e) {
            System.err.printf("❌ performSubscription error: %s\n", e.getMessage());
        }
    }
    public void logDuration(String stepName, Runnable action) {
        long start = System.currentTimeMillis();
        try {
            action.run();
            long duration = System.currentTimeMillis() - start;
            System.out.printf("🕒 Step: %s -> Tamamlandı (%d ms)\n", stepName, duration);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            System.err.printf("❌ Step: %s -> HATA (%d ms): %s\n", stepName, duration, e.getMessage());
        }
    }
    public void handlePopups(String[] popupSelectors,int timeoutMillis, int intervalMillis) {



        for (String selector : popupSelectors) {
            long startTime = System.currentTimeMillis();
            boolean elementFound = false;

            while (System.currentTimeMillis() - startTime < timeoutMillis) {
                try {
                    WebElement element;

                    if (selector.startsWith("~")) {
                        element = driver.findElement(AppiumBy.accessibilityId(selector.substring(1)));
                    } else {
                        element = driver.findElement(AppiumBy.xpath(selector));
                    }

                    if (element.isDisplayed() && element.isEnabled()) {
                        element.click();
                        Reporter.log("Popup clicked: " + selector);
                        elementFound = true;
                        break;
                    }
                } catch (NoSuchElementException ignored) {
                    // Retry
                }

                try {
                    Thread.sleep(intervalMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Reporter.log("Interrupted while waiting: " + e.getMessage());
                }
            }
            captureAndAttach(driver, "Handle Pop Up", LogLevel.INFO);

            if (!elementFound) {
                Reporter.log("Popup not found within " + timeoutMillis + "ms: " + selector);
            }
        }
    }

    public void tap(By locator, int duration) {
        try {
            WebElement element = driver.findElement(locator);
            Point location = element.getLocation();
            Dimension size = element.getSize();
            
            // Calculate center point of the element
            int centerX = location.getX() + (size.getWidth() / 2);
            int centerY = location.getY() + (size.getHeight() / 2);
            
            log.info("Performing tap at ({},{})", centerX, centerY);
            
            // Create W3C Actions for tap
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            
            // Move to element center
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
            // Press down
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            // Wait for specified duration
            tap.addAction(new Pause(finger, Duration.ofMillis(duration)));
            // Release
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            
            // Perform the tap
            driver.perform(Collections.singletonList(tap));
            
            log.info("Tap completed successfully");
            
        } catch (Exception e) {
            log.error("Failed to perform tap: {}", e.getMessage());
            throw new RuntimeException("Failed to perform tap: " + e.getMessage(), e);
        }
    }

}
