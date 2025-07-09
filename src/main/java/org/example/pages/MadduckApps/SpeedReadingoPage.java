package org.example.pages.MadduckApps;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.example.utils.MobileTestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class SpeedReadingoPage {

    private final AppiumDriver driver;
    private final MobileTestUtils utils;
    public SpeedReadingoPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver); // ✅ constructor içinde init
    }

    private final By btnGetStarted = AppiumBy.accessibilityId("GET STARTED");
    private final By btnContinue = AppiumBy.accessibilityId("CONTINUE");
    private final By optionPrepareForExam = By.xpath("//XCUIElementTypeStaticText[@name='Prepare for an exam']");
    private final By optionTikTok = AppiumBy.accessibilityId("TikTok");
    private final By fieldHowDidYouHear = AppiumBy.accessibilityId("How did you hear about us?");
    private final By optionSpeedRead = By.xpath("//XCUIElementTypeStaticText[@name='I can speed read any text (400-1200 wpm)']");
    private final By optionUnderstandAlmostEverything = By.xpath("//XCUIElementTypeStaticText[@name='I understand almost everything (75-100)%']");
    private final By option10MinPerDay = AppiumBy.accessibilityId("10 minutes per day");
    private final By optionCommitted = AppiumBy.accessibilityId("I'M COMMITTED");
    private final By btnAllow = AppiumBy.accessibilityId("Allow");
    private final By btnRemindMeToPractice = By.xpath("(//XCUIElementTypeButton[@name='Remind Me To Practice'])[1]");
    private final By btnStartFromScratch = AppiumBy.accessibilityId("Start from Scratch");
    private final By optionScanningParts = AppiumBy.accessibilityId("Scanning random parts of the text");
    private final By btnNext = AppiumBy.accessibilityId("NEXT");
    private final By btnStartExercise = AppiumBy.accessibilityId("START EXERCISE");
    private final By btnStartReading = AppiumBy.accessibilityId("START READING");
    private final By btnDone = AppiumBy.accessibilityId("I’M DONE");
    private final By optionNatureScene = AppiumBy.accessibilityId("A peaceful nature scene");
    private final By optionSaccades = AppiumBy.accessibilityId("saccades");
    private final By optionFixation = AppiumBy.accessibilityId("fixation");
    private final By optionVisual = AppiumBy.accessibilityId("visual");
    private final By optionGlance = AppiumBy.accessibilityId("glance");
    private final By btnCheckAnswers = AppiumBy.accessibilityId("Check Answers");
    private final By btnClaimReward = AppiumBy.accessibilityId("Claim Reward");
    private final By btnCommittedTag = By.xpath("//*[contains(@name,'COMMITTED')]");
    private final By btnCreateProfile = AppiumBy.accessibilityId("CREATE PROFILE");
    private final By btnContinueWithApple = By.xpath("//XCUIElementTypeStaticText[@name='Continue with Apple']");
    private final By btnContinueWithPassword = AppiumBy.accessibilityId("Continue with Password");
    private final By btnSIWAContinue = AppiumBy.accessibilityId("SIWA_CONTINUE_BUTTON");
    private final By inputSecurePassword = By.xpath("//*[contains(@type,'XCUIElementTypeSecureTextField')]");
    private final By btnSignIn = By.xpath("//XCUIElementTypeButton[@name='Sign In']");
    private final By btnStartFreeTrial = AppiumBy.accessibilityId("START FREE TRIAL");
    private final By btnMore = AppiumBy.accessibilityId("MORE");
    private final By btnGotIt = By.xpath("//XCUIElementTypeButton[@name='GOT IT']");
    private final By btnContinue3rd = By.xpath("(//XCUIElementTypeButton[@name='CONTINUE'])[3]");
    private final By btnStartWithPoints = AppiumBy.accessibilityId("START, +35 POINTS");
    private final By btnClose = AppiumBy.accessibilityId("Close");
    private final By btnLeave = AppiumBy.accessibilityId("Leave");
    private final By tabGames = AppiumBy.accessibilityId("Games");
    private final By tabMaster = AppiumBy.accessibilityId("Master");
    private final By tabProfile = AppiumBy.accessibilityId("Profile");
    private final By btnSeeAll = AppiumBy.accessibilityId("See All");
    private final By btnContinue2   = AppiumBy.xpath("XCUIElementTypeStaticText[@name='CONTINUE']");


    @Step("Onboarding Process")
    public void onboardingFlow() throws Exception {
        utils.clickIfExists(btnGetStarted,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(optionPrepareForExam,10);
        utils.clickIfExists(btnContinue,10);
        try {
            WebElement element = utils.waitForElement(fieldHowDidYouHear, 30);
            Assert.assertNotNull(element, "'How did you hear about us?' bulunamadı.");
            System.out.println("✅ 'How did you hear about us?' bulundu");
        } catch (Exception e) {
            System.out.println("❌ Element not found: How did you hear about us?");
        }
        utils.clickIfExists(optionTikTok,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(optionSpeedRead,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(optionUnderstandAlmostEverything,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(option10MinPerDay,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnCommittedTag,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnAllow,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnRemindMeToPractice,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnAllow,10);
        utils.clickIfExists(btnRemindMeToPractice,10);
        utils.clickIfExists(btnStartFromScratch,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(optionScanningParts,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnContinue,10);
        utils.clickIfExists(btnNext,10);
        utils.clickIfExists(btnNext,10);
        utils.clickIfExists(btnStartExercise,10);
        utils.clickIfExists(btnNext,10);
        utils.clickIfExists(btnNext,10);
        utils.clickIfExists(btnStartReading,10);
        utils.clickIfExists(btnNext,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnDone,10);
        utils.tapCenter(optionNatureScene);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnCheckAnswers,10);
        utils.clickIfExists(btnDone,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(optionSaccades,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(optionFixation,10);
        utils.clickIfExists(optionVisual,10);
        utils.clickIfExists(optionGlance,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnCheckAnswers,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnContinue,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(btnClaimReward,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
        utils.clickIfExists(optionCommitted,10);
        utils.swipe(MobileTestUtils.SwipeDirection.UP, 1);
    }
    @Step("Create Profile Process")
    public void createProfile() throws Exception {
        utils.clickIfExists(btnCreateProfile,10);
        utils.clickIfExists(btnContinueWithApple,10);
        utils.clickIfExists(btnContinueWithPassword,10);
        utils.clickIfExists(btnSIWAContinue,10);
        utils.clickIfExists(btnContinueWithPassword,10);
        utils.clickIfExists(btnSIWAContinue,10);
        utils.clickIfExists(inputSecurePassword,10);
        utils.clickIfExists(btnSignIn,10);
        try{
            utils.handle2FAIfPresent();
        }catch (Exception e){

        }

        try{
            utils.clickWithDelay(btnContinue, 10,30);

        }catch (Exception e){

        }
        try{
            utils.clickWithDelay(btnStartFreeTrial, 10,30);

        }catch (Exception e){

        }
        try{
            utils.performSubscription();

        }catch (Exception e){

        }
        utils.clickIfExists(btnContinue2,10);
        for (int i = 0; i < 5; i++) {
            utils.clickIfExists(btnMore,10);
        }
        utils.clickIfExists(btnContinue2,10);
        utils.clickIfExists(btnGotIt,10);
        utils.clickIfExists(btnStartWithPoints,10);
        utils.clickIfExists(btnClose,10);
        utils.clickIfExists(btnLeave,10);
    }
    @Step("Navigate Profile App Feature Process")
    public void navigationAndProfile() throws Exception {
        utils.clickIfExists(tabGames,10);
        utils.clickIfExists(btnGotIt,10);
        utils.clickIfExists(tabMaster,10);
        utils.clickIfExists(tabProfile,10);
        utils.clickIfExists(btnSeeAll,10);
    }
}
