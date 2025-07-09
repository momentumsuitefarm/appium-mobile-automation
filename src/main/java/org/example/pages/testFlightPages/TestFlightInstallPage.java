package org.example.pages.testFlightPages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.example.utils.logging.LogLevel;
import org.example.utils.logging.LoggerHelper;
import org.example.utils.MobileTestUtils;
import org.example.utils.reporting.ScreenshotHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.example.utils.MobileTestUtils.*;

public class TestFlightInstallPage {

    private final IOSDriver driver;
    private final MobileTestUtils utils;
    private final String appName = "";
    public TestFlightInstallPage(IOSDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver);
    }
    private final By allowOnce = AppiumBy.accessibilityId("Allow Once");
    private final By notNow = AppiumBy.accessibilityId("Not Now");
    private final By installBtn = AppiumBy.xpath("//*[contains(@name,'Install') or contains(@name,'INSTALL') or contains(@label,'YÜKLE') or contains(@label,'Yükle') ]");
    private final By openBtn = AppiumBy.xpath("//*[contains(@name,'Open') or contains(@name,'OPEN') or contains(@name,'Aç') or contains(@name,'AÇ') ]");
    private final By okBtn = AppiumBy.accessibilityId("Ok");
    private final By appNameLocator = AppiumBy.xpath("//*[contains(@name, '" + appName + "')]");
    public void removeAppPhones() {
        try {
            List<By> editVariants = Arrays.asList(
                    AppiumBy.xpath("(//*[contains(@name,'Edit') or contains(@label,'Edit') or contains(@value,'Edit')])[2]"),
                    AppiumBy.accessibilityId("Edit Button")
            );
            for (By by : editVariants) {
                if (utils.waitUntilVisible( by, 5)) {
                    utils.waitAndClick( by, 5);
                    break;
                }
            }

            List<By> removeVariants = Arrays.asList(
                    By.xpath("(//XCUIElementTypeImage[@name='minus.circle.fill'])[2]"),
                    By.xpath("(//XCUIElementTypeImage[@name='remove'])[2]"),
                    By.xpath("(//XCUIElementTypeCell)[6]"),
                    By.xpath("(//*[contains(@name,'iPhone ')])[2]")
            );
            for (By remove : removeVariants) {
                if (utils.waitUntilVisible( remove, 5)) {
                    utils.waitAndClick( remove, 5);
                    break;
                }
            }

            List<By> deleteVariants = Arrays.asList(
                    By.xpath("//XCUIElementTypeStaticText[@name='Delete']"),
                    AppiumBy.accessibilityId("Remove Button")
            );
            for (By delete : deleteVariants) {
                if (utils.waitUntilVisible( delete, 5)) {
                    utils.waitAndClick( delete, 5);
                    break;
                }
            }

            utils.waitAndClick(By.xpath("//XCUIElementTypeOther[@name='Dismiss-Button']"), 5);

            List<By> doneVariants = Arrays.asList(
                    AppiumBy.accessibilityId("Done"),
                    AppiumBy.accessibilityId("Done Button"),
                    AppiumBy.xpath("(//XCUIElementTypeButton[@name='Done'])[2]"),
                    AppiumBy.xpath("(//XCUIElementTypeButton[@name='Done'])[1]")
            );
            for (By done : doneVariants) {
                if (utils.waitUntilVisible( done, 5)) {
                    utils.waitAndClick( done, 5);
                    break;
                }
            }

            System.out.println("✅ removeAppPhones işlemi tamamlandı.");
        } catch (Exception e) {
            System.err.println("❌ removeAppPhones işlemi başarısız: " + e.getMessage());
        }
    }
    @Step("openTestFlight")
    public void openTestFlight(String bundleId) {
        int maxRetries = 3;
        int retryCount = 0;
        long retryDelay = 2000; // 2 seconds initial delay

        while (retryCount < maxRetries) {
            try {
                System.out.println("Attempt " + (retryCount + 1) + " to open TestFlight...");
                
                // First try to terminate the app
                try {
                    driver.terminateApp(bundleId);
                    Thread.sleep(1000); // Short wait after termination
                } catch (Exception e) {
                    System.out.println("Warning: Could not terminate app: " + e.getMessage());
                }

                // Try to activate the app with retry
                boolean activated = false;
                int activationRetries = 3;
                for (int i = 0; i < activationRetries && !activated; i++) {
                    try {
                        driver.activateApp(bundleId);
                        activated = true;
                        System.out.println("Successfully activated app on attempt " + (i + 1));
                    } catch (Exception e) {
                        System.out.println("Activation attempt " + (i + 1) + " failed: " + e.getMessage());
                        if (i < activationRetries - 1) {
                            Thread.sleep(1000);
                        }
                    }
                }

                if (!activated) {
                    throw new RuntimeException("Failed to activate app after " + activationRetries + " attempts");
                }

                // Wait for app to be ready
                Thread.sleep(2000);

                // Verify app is running
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                wait.until(d -> {
                    try {
                        String pageSource = d.getPageSource();
                        return pageSource != null && !pageSource.isEmpty();
                    } catch (Exception e) {
                        return false;
                    }
                });

                ScreenshotHelper.captureAndAttach(driver, "openTestFlight", LogLevel.INFO);
                return; // Success, exit the method

            } catch (Exception e) {
                retryCount++;
                System.err.println("❌ openTestFlight error (attempt " + retryCount + "): " + e.getMessage());
                
                if (retryCount < maxRetries) {
                    System.out.println("Retrying in " + (retryDelay/1000) + " seconds...");
                    try {
                        Thread.sleep(retryDelay);
                        retryDelay *= 2; // Exponential backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    ScreenshotHelper.captureAndAttach(driver, "openTestFlight Error", LogLevel.ERROR);
                    throw new RuntimeException("Failed to open TestFlight after " + maxRetries + " attempts: " + e.getMessage(), e);
                }
            }
        }
    }
    @Step("handlePermissions")
    public void handlePermissions() {
        if (utils.waitUntilVisible(allowOnce, 5)) {
            utils.waitAndClick(allowOnce, 5);
        }if (utils.waitUntilVisible( notNow, 5)) {
            utils.waitAndClick(notNow, 5);
        }if (utils.waitUntilVisible(okBtn, 5)) {
            utils.waitAndClick(okBtn, 5);
        }
    }
    @Step("searchAndTapApp")
    public void searchAndTapApp(String appName) throws Exception {
        By appLocator = AppiumBy.accessibilityId(appName);
        int maxScrolls = 5;
        int currentScroll = 0;

        SwipeDirection direction = SwipeDirection.UP;

        while (currentScroll < maxScrolls * 2) {
            if (utils.waitUntilVisible(appLocator, 2)) {
                utils.waitAndClick(appLocator, 5);
                System.out.println("✅ App bulundu ve TIKLANDI (" + direction + " swipe): " + appName);
                return;
            }

            utils.swipe(direction, 3);
            currentScroll++;

            // UP swipe bitince DOWN swipe'a geç
            if (currentScroll == maxScrolls) {
                direction = SwipeDirection.DOWN;
                System.out.println("🔄 Yön değiştiriliyor: DOWN swipe");
            }
        }

        ScreenshotHelper.captureAndAttach(driver, "App not found in TestFlight", LogLevel.ERROR);
        throw new Exception("❌ App not found in TestFlight after swipes: " + appName);
    }
    @Step("uninstallAppIfPresent")
    public void uninstallAppIfPresent(String targetBundleId) {
        if (driver.isAppInstalled(targetBundleId)) {
            driver.removeApp(targetBundleId);
            System.out.println("✅ Uninstall App: " + LogLevel.INFO);
        }
    }
    @Step("restartTf")
    public void restartTf(String bundleId) {
        try {
            // First try to terminate the app
            try {
                driver.terminateApp(bundleId);
                System.out.println("✅ App terminated: " + bundleId);
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("⚠️ App termination failed, continuing anyway: " + e.getMessage());
            }

            // Wait for WDA to be ready
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(d -> {
                try {
                    return d.getPageSource() != null;
                } catch (Exception e) {
                    return false;
                }
            });

            // Try to activate the app with retry mechanism
            int maxRetries = 3;
            int retryCount = 0;
            boolean success = false;

            while (!success && retryCount < maxRetries) {
                try {
                    driver.activateApp(bundleId);
                    Thread.sleep(2000);
                    
                    // Verify app is actually running
                    if (driver.getPageSource() != null) {
                        success = true;
                        System.out.println("✅ App activated successfully: " + bundleId);
                    } else {
                        throw new RuntimeException("App activation verification failed");
                    }
                } catch (Exception e) {
                    retryCount++;
                    System.err.println("❌ Activation attempt " + retryCount + " failed: " + e.getMessage());
                    if (retryCount == maxRetries) {
                        throw new RuntimeException("Failed to activate app after " + maxRetries + " attempts", e);
                    }
                    Thread.sleep(2000);
                }
            }

            ScreenshotHelper.captureAndAttach(driver, "App Restarted", LogLevel.INFO);
        } catch (Exception e) {
            System.err.println("❌ restartApp error: " + e.getMessage());
            ScreenshotHelper.captureAndAttach(driver, "App Restart Error", LogLevel.ERROR);
            throw new RuntimeException("Failed to restart app: " + e.getMessage(), e);
        }
    }
    @Step("clickInstall")
    public void clickInstall() {
        utils.waitAndClick(installBtn, 5);
        try {
            By editButton = AppiumBy.xpath("//*[contains(@name,'Edit') or contains(@label,'Edit') or contains(@value,'Edit')]");
            if (utils.waitUntilVisible( editButton, 5)) {
                removeAppPhones();
                utils.waitAndClick(installBtn, 5);
                System.out.println("Install bulundu tıklandı" + installBtn);
            }
        } catch (Exception ignored) {}
    }
    @Step("waitForAndClickOpen")
    public void waitForAndClickOpen() throws Exception {
        if (utils.waitUntilVisible( openBtn, 180)) {
            utils.waitAndClick( openBtn, 2);
            System.out.println("Open bulundu tıklandı" + openBtn);
        } else {
            throw new Exception("❌ OPEN button not found after waiting 180 seconds.");
        }
    }
    @Step("Verify Open Apps")
    public void verifyApp(String appName){
        LoggerHelper.log(LogLevel.INFO, "Oepn App Verify...");
        utils.waitUntilVisible(AppiumBy.xpath("//*[contains(@name, '" + appName + "')]"),5);

    }
    @Step("Perform full install flow")
    public void tfPAppInstall(String bundleId, String appName, String targetBundleId) throws Exception {
        LoggerHelper.log(LogLevel.INFO, "TestFlight with App Install flow...");
        openTestFlight(bundleId);
        handlePermissions();
        searchAndTapApp(appName);
        uninstallAppIfPresent(targetBundleId);
        clickInstall();
        waitForAndClickOpen();
    }
}
