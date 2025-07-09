package org.example.tests.MadduckTests;

import org.example.config.DriverFactory;
import org.example.pages.MadduckApps.SpeedReadingoPage;
import org.example.tests.BaseTest;
import org.testng.annotations.Test;

public class SpeedReadingoTest extends BaseTest {

    @Test(description = "Sample test mapped from original JUnit flow")
    public void onboardAndProfile() throws Exception {
        SpeedReadingoPage onboardPage = new SpeedReadingoPage(DriverFactory.getDriver());
        onboardPage.onboardingFlow();
        onboardPage.createProfile();
        onboardPage.navigationAndProfile();
    }
}
