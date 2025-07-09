package org.example.tests.BtcAITests.IOS;

import org.example.config.DriverFactory;
import org.example.pages.BtcAIApps.IOS.ChatPage;
import org.example.pages.BtcAIApps.IOS.LoginPage;
import org.example.pages.BtcAIApps.IOS.OnboardingPage;
import org.example.tests.BaseTest;
import org.testng.annotations.*;

public class OnboardingTest extends BaseTest {


    @Test(priority = 1,description = "Onboarding and Assertion")
    public void onboardingRetry() throws Exception {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        ChatPage chatPage = new ChatPage(DriverFactory.getDriver());
        OnboardingPage onboardingPage = new OnboardingPage(DriverFactory.getDriver());

        loginPage.loginWithRandomUsername();
        onboardingPage.onboardingRetryFlow();
        chatPage.closeChat();
    }
    @Test(priority = 2,description = "Onboarding Skip")
    public void onboarding() throws Exception {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        ChatPage chatPage = new ChatPage(DriverFactory.getDriver());
        OnboardingPage onboardingPage = new OnboardingPage(DriverFactory.getDriver());

        loginPage.loginWithRandomUsername();
        onboardingPage.onboardingFlow();
        chatPage.closeChat();
    }
}
