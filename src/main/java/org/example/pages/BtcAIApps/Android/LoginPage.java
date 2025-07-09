// --- LoginPage.java ---
package org.example.pages.BtcAIApps.Android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.example.utils.*;
import org.example.utils.logging.LogLevel;
import org.example.utils.logging.LoggerHelper;
import org.example.utils.reporting.ScreenshotHelper;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final AppiumDriver driver;
    private final MobileTestUtils utils;
    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver);
    }
    @Step("Enter username: {username}")
    public void enterUsername(String username) throws Exception {
        LoggerHelper.log(LogLevel.INFO, "Enter username: " + username);
        ScreenshotHelper.captureAndAttach(driver, "Enter username", LogLevel.INFO);
        utils.inputValue(AppiumBy.className("android.widget.EditText"), username, 10);
    }

    @Step("Tap 'Send' button")
    public void tapSend() throws Exception {
        utils.waitAndClick(AppiumBy.accessibilityId("Send"),5);
        LoggerHelper.log(LogLevel.INFO, "Tap 'Send' button");
        ScreenshotHelper.captureAndAttach(driver, "Tap Send", LogLevel.INFO);
    }

    @Step("Tap view instance 1")
    public void tapSecondView() throws Exception {
        utils.waitAndClick(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(1)"), 10);
        LoggerHelper.log(LogLevel.INFO, "Tap view instance 1");
        ScreenshotHelper.captureAndAttach(driver, "Tap View 1", LogLevel.INFO);
    }

    @Step("Tap button instance 1")
    public void tapSecondButton() throws Exception {
        utils.waitAndClick(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)"), 10);
        LoggerHelper.log(LogLevel.INFO, "Tap button instance 1");
        ScreenshotHelper.captureAndAttach(driver, "Tap Button 1", LogLevel.INFO);
    }

    @Step("Tap 'Sonraki' buttons 3 times")
    public void tapSonrakiButtons() throws Exception {
        for (int i = 0; i < 3; i++) {
            utils.waitAndClick(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sonraki\")"), 10);
            LoggerHelper.log(LogLevel.INFO, "Tap 'Sonraki' buttons 3 times");
            ScreenshotHelper.captureAndAttach(driver, "Tap Sonraki " + (i + 1), LogLevel.INFO);
        }
    }
    @Step("Tap 'Kapat' button")
    public void tapKapat() {
        utils.clickIfExists(AppiumBy.androidUIAutomator("new UiSelector().text(\"Kapat\")"),10);
        LoggerHelper.log(LogLevel.INFO, "Tap 'Kapat' button");
        ScreenshotHelper.captureAndAttach(driver, "Tap Kapat", LogLevel.INFO);
    }
    @Step("Tap keyboard button")
    public void openKeyboard() {
        LoggerHelper.log(LogLevel.INFO, "Tap keyboard button");
        ScreenshotHelper.captureAndAttach(driver, "Tap Keyboard", LogLevel.INFO);
        driver.findElement(AppiumBy.accessibilityId("Keyboard")).click();
    }
    @Step("Type in scroll view: {text}")
    public void typeInScrollView(String text) {
        LoggerHelper.log(LogLevel.INFO, "Type in scroll view: " + text);
        ScreenshotHelper.captureAndAttach(driver, "Type in ScrollView", LogLevel.INFO);
        WebElement scrollView = driver.findElement(AppiumBy.className("android.widget.ScrollView"));
        scrollView.sendKeys(text);
    }
    @Step("Tap view instance 3")
    public void tapViewInstance3() {
        LoggerHelper.log(LogLevel.INFO, "Tap view instance 3");
        ScreenshotHelper.captureAndAttach(driver, "Tap View 3", LogLevel.INFO);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(3)"))
                .click();
    }
    @Step("Type amount: {amount}")
    public void typeAmount(String amount) {
        LoggerHelper.log(LogLevel.INFO, "Type amount: " + amount);
        ScreenshotHelper.captureAndAttach(driver, "Type Amount", LogLevel.INFO);
        driver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys(amount);
    }
    @Step("Tap send by description")
    public void tapSendByDescription() {
        LoggerHelper.log(LogLevel.INFO, "Tap send by description");
        ScreenshotHelper.captureAndAttach(driver, "Tap Send Description", LogLevel.INFO);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Send\").instance(0)"))
                .click();
    }
    @Step("Confirm buttons")
    public void confirmButtons() {
        LoggerHelper.log(LogLevel.INFO, "Confirm buttons");
        for (int i = 0; i < 2; i++) {
            ScreenshotHelper.captureAndAttach(driver, "Confirm Button " + (i + 1), LogLevel.INFO);
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(0)"))
                    .click();
        }
    }
    @Step("Perform full login flow")
    public void fullLoginFlow() throws Exception {
        LoggerHelper.log(LogLevel.INFO, "Perform full login flow");
        enterUsername("muhtek");
        tapSend();
        tapSecondView();
        tapSecondButton();
        tapSonrakiButtons();
        tapKapat();
        openKeyboard();
        typeInScrollView("Para Çekme");
        tapSendByDescription();
        typeAmount("10");
        tapSendByDescription();
        confirmButtons();
    }
}
