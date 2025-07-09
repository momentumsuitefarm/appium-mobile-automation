package org.example.tests.BtcAITests.IOS;

import org.example.config.DriverFactory;
import org.example.pages.BtcAIApps.IOS.ChatPage;
import org.example.pages.BtcAIApps.IOS.LoginPage;
import org.example.pages.BtcAIApps.IOS.OnboardingPage;
import org.example.tests.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Login test with random username")
    public void loginTest() throws Exception {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.loginWithRandomUsername();
    }
}
