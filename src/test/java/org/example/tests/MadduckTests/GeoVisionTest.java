package org.example.tests.MadduckTests;

import org.example.pages.BtcAIApps.IOS.OnboardingPage;
import org.example.pages.MadduckApps.GeoVisionPage;
import org.example.config.DriverFactory;
import org.example.tests.BaseTest;
import org.junit.Before;
import org.testng.annotations.Test;

public class GeoVisionTest extends BaseTest {

    @Test(priority = 1, description = "GeoVision Onboarding")
    public void onboardingFlow() {
        GeoVisionPage geoPage = new GeoVisionPage(DriverFactory.getDriver());
        geoPage.completeInitialOnboarding();

    }
    @Test(priority = 2, description = "GeoVision Ask More Guide Feature")
    public void askMoreToGuideFlow() throws Exception {
        GeoVisionPage geoPage = new GeoVisionPage(DriverFactory.getDriver());
        geoPage.askMoreToGuide();

    }
    @Test(priority = 3, description = "Subscribe")
    public void subscribe(){
        GeoVisionPage geoPage = new GeoVisionPage(DriverFactory.getDriver());
        geoPage.subscribe();

    }
    @Test(priority = 4, description = "Ask More Spy Feature")
    public void askMoreToSpyFlow() throws Exception {
        GeoVisionPage geoPage = new GeoVisionPage(DriverFactory.getDriver());
        geoPage.askMoreToSpy();
    }

    @Test(priority = 5, description = "History Delete Feature")
    public void historyDeleteCase() throws Exception {
        GeoVisionPage geoPage = new GeoVisionPage(DriverFactory.getDriver());
        geoPage.historyDeleteCase();
        geoPage.historyDeleteCase();
    }
}
