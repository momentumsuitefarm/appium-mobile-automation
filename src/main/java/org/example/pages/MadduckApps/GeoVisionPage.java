package org.example.pages.MadduckApps;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.example.utils.MobileTestUtils;
import org.openqa.selenium.By;

public class GeoVisionPage {

    private final AppiumDriver driver;
    private final MobileTestUtils utils;
    public GeoVisionPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver); // ✅ constructor içinde init
    }
    private final By nextBtn = AppiumBy.xpath("(//XCUIElementTypeButton[@name=\"Next\"])[2]");
    private final By startTestingBtn = AppiumBy.xpath("(//XCUIElementTypeButton[@name=\"Start Testing\"])[2]");
    private final By continueBtn = AppiumBy.accessibilityId("Continue");
    private final By chooseTestPhoto = AppiumBy.accessibilityId("Photo 1");
    private final By selectPicture = AppiumBy.accessibilityId("Select a picture");
    private final By chooseFromGallery = AppiumBy.accessibilityId("Choose From Gallery");
    private final By photoSelect = AppiumBy.xpath("(//*[contains(@name,'Photo,')])[3]");
    private final By gotIt = AppiumBy.accessibilityId("Got it");
    private final By AppImageSelect = AppiumBy.xpath("XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeImage[1]");
    private final By analyzeImage = AppiumBy.accessibilityId("Analyze Image");
    private final By askMore = AppiumBy.accessibilityId("Ask more to Spy");
    private final By whatsNearby = AppiumBy.accessibilityId("🧭 What's nearby?");
    private final By localShops = AppiumBy.accessibilityId("🛍️ Local shops?");
    private final By history = AppiumBy.accessibilityId("History");
    private final By deleteIf = AppiumBy.iOSClassChain("**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther");
    private final By deleteConfirm = AppiumBy.accessibilityId("~Delete");
    private final By backBtn    = AppiumBy.iOSClassChain("**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeButton");
    private final By spyModeChange = AppiumBy.iOSClassChain("**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeButton");
    private final By ImageSelect = AppiumBy.iOSClassChain("**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeImage[1]");
    private final By askGuide = AppiumBy.accessibilityId("Ask more to Guide");
    private final By cancelButton = AppiumBy.accessibilityId("Cancel");
    private final By cancelBtn = AppiumBy.xpath("//*[contains(@name,'Cancel') or contains(@label,'Cancel')]");
    private final By okBtn = AppiumBy.accessibilityId("OK");
    private final By weeklyPlan = AppiumBy.accessibilityId("Weekly Premium\n₺229.99\n/week");
    private final By startSubscription = AppiumBy.accessibilityId("Start No-Commitment Subscription");
    private final By ucnoktaliMenu = AppiumBy.iOSClassChain("**/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeButton[2]");
    private final By sortBtn    =   AppiumBy.accessibilityId("Sort by date (descending)");
    private final By photo2 = AppiumBy.xpath("(//*[contains(@label,'Screenshot, ')])[2]");






    private final By spyMode = AppiumBy.xpath("//*[contains(@name,'Spy Mode')]");
    private final By doneBtn = AppiumBy.xpath("//*[contains(@name,'Done')]");
    private final By tourGuideMode = AppiumBy.xpath("//*[contains(@name,'Tour Guide Mode')]");
    private final By genericButton = AppiumBy.className("XCUIElementTypeButton");
    private final By imageList = AppiumBy.iOSClassChain("**/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeImage[1]");



    @Step("Onboarding Process...")
    public void completeInitialOnboarding() {

        utils.logDuration("Wait & Click 'Next'", () -> {
            if (utils.waitUntilVisible(nextBtn, 5)) {
                utils.waitAndClick(nextBtn, 5);
            }
        });
        utils.logDuration("Wait & Click 'Start Testing'", () -> {
            if (utils.waitUntilVisible(startTestingBtn, 5)) {
                utils.waitAndClick(startTestingBtn, 5);
            }
        });
        utils.logDuration("Wait & Click 'Continue' (Intro)", () -> {
            if (utils.waitUntilVisible(continueBtn, 5)) {
                utils.waitAndClick(continueBtn, 5);
            }
            if (utils.waitUntilVisible(continueBtn, 5)) {
                utils.waitAndClick(continueBtn, 5);
            }
        });
        utils.logDuration("Click 'Choose Test Photo'", () -> {
            if (utils.waitUntilVisible(chooseTestPhoto, 5)) {
                utils.waitAndClick(chooseTestPhoto, 5);
            }
        });
        utils.swipe(MobileTestUtils.SwipeDirection.UP,2);
        utils.logDuration("Wait & Click 'Continue' (Intro)", () -> {
            if (utils.waitUntilVisible(continueBtn, 5)) {
                utils.waitAndClick(continueBtn, 5);
            }
            if (utils.waitUntilVisible(continueBtn, 5)) {
                utils.waitAndClick(continueBtn, 5);
            }
        });
    }
    public boolean isElementPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }
    @Step("Subscribe & Paywall Process...")
    public void subscribe() {

        utils.logDuration("Wait & Click 'Continue' Button", () -> {
            if (utils.waitUntilVisible(continueBtn, 5)) {
                utils.waitAndClick(continueBtn, 5);
            }
        });

        utils.logDuration("Wait & Click 'Weekly Plan'", () -> {
            if (utils.waitUntilVisible(weeklyPlan, 5)) {
                utils.waitAndClick(weeklyPlan, 5);
            }
        });

        utils.logDuration("Wait & Click 'Start Subscription'", () -> {
            utils.waitAndClick(startSubscription, 5);
        });

        utils.logDuration("Perform Subscription", () -> {
            utils.performSubscription();
        });

        utils.logDuration("Wait & Click 'OK' Button", () -> {
            utils.waitAndClick(okBtn, 5);
        });
    }
    @Step("App Feature Process...")
    public void askMoreToSpy() throws Exception {
        utils.logDuration("Click 'Select a picture'", () -> {
            utils.clickIfExists(selectPicture, 5);
        });
        utils.logDuration("Click 'Choose From Gallery'", () -> {
            utils.clickIfExists(chooseFromGallery, 5);
        });
        utils.logDuration("Screenshot Photo Select visible & click", () -> {
            if (isElementPresent(photoSelect)) {
                utils.clickIfExists(photoSelect, 5);
            }
        });
        utils.logDuration("Screenshot Photo Select visible & click", () -> {
            if (isElementPresent(photo2)) {
                utils.clickIfExists(photo2, 5);
            }
        });
        utils.logDuration("Cancel visible & click", () -> {
            if(utils.waitUntilVisible(cancelBtn,5)){
                utils.clickIfExists(cancelBtn, 5);
                utils.clickIfExists(cancelButton,5);
            }
        });
        utils.logDuration("Click 'Got It'", () -> {
            utils.clickIfExists(gotIt, 5);
        });
        utils.logDuration("Click 'Gallery Image Select'", () -> {
            utils.clickIfExists(AppImageSelect, 5);
        });
        utils.logDuration("Click 'Analyze Image'", () -> {
            utils.clickIfExists(analyzeImage, 5);
        });
        utils.swipe(MobileTestUtils.SwipeDirection.UP,3);

        utils.logDuration("Click 'Ask More'", () -> {
            utils.clickIfExists(askMore, 5);
        });
        utils.logDuration("Click 'Whats Nearby Button'", () -> {
            utils.clickIfExists(whatsNearby, 5);
        });
        utils.logDuration("Restart app", () -> {
            utils.restartApp((IOSDriver) driver, "com.madduck.geovs");
        });
    }
    @Step("Ask Guide Process...")
    public void askMoreToGuide() throws Exception {
        utils.logDuration("Click 'Spy Mode Change'", () -> {
            utils.clickIfExists(spyModeChange, 5);
        });
        utils.logDuration("Click 'Choose From Gallery'", () -> {
            utils.clickIfExists(chooseTestPhoto, 5);
        });
        utils.logDuration("Click 'Analyze Image'", () -> {
            utils.clickIfExists(analyzeImage, 5);
        });
        utils.swipe(MobileTestUtils.SwipeDirection.UP,1);
        utils.logDuration("Click 'Ask More Guide'", () -> {
            utils.clickIfExists(askGuide, 5);
        });
        utils.logDuration("Click 'Whats Nearby Button'", () -> {
            utils.clickIfExists(whatsNearby, 5);
        });
        utils.logDuration("Restart app", () -> {
            utils.restartApp((IOSDriver) driver, "com.madduck.geovs");
        });
    }
    @Step("History Delete Process...")
    public void historyDeleteCase() throws Exception {
        utils.logDuration("Click 'History' button", () -> {
            utils.clickIfExists(history, 5);
        });

        utils.logDuration("Click 'Delete If' element", () -> {
            utils.clickIfExists(deleteIf, 5);
        });

        utils.logDuration("Click 'Delete Confirm' button", () -> {
            utils.clickIfExists(deleteConfirm, 5);
        });

        utils.logDuration("Click 'Back' button", () -> {
            utils.clickIfExists(backBtn, 5);
        });
    }
}
