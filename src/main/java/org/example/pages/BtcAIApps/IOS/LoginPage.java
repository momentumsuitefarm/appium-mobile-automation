package org.example.pages.BtcAIApps.IOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.example.utils.logging.LogLevel;
import org.example.utils.MobileTestUtils;
import org.example.utils.reporting.ScreenshotHelper;
import org.openqa.selenium.By;

public class LoginPage {

    private final AppiumDriver driver;
    private final MobileTestUtils utils;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver);
    }
    By inputUsername = AppiumBy.xpath("//*[contains(@type,'XCUIElementTypeTextField')]");
    By girisBtn = AppiumBy.accessibilityId("BtcTurk Ai Assistant");

    public void loginWithRandomUsername() throws Exception {
        String username = utils.generateRandomUsername();
        System.out.println("Kullanıcı adı: " + username);
        utils.inputValue(inputUsername,username,15);
        utils.waitAndClick(girisBtn,15);
        ScreenshotHelper.captureAndAttach(driver,"Login", LogLevel.INFO);
        Thread.sleep(500); // Gerekirse WebDriverWait ile değiştirin
    }
}
