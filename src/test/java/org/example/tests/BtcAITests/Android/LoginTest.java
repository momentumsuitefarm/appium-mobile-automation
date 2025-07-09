package org.example.tests.BtcAITests.Android;

import org.example.config.DriverFactory;
import org.example.tests.BaseTest;
import org.testng.annotations.Test;
import org.example.pages.BtcAIApps.Android.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Sample test mapped from original JUnit flow")
    public void testLoginFlowAdaptedFromJUnit() throws Exception {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.fullLoginFlow();
    }
}
