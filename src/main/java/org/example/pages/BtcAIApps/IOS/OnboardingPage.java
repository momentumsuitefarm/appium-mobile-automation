package org.example.pages.BtcAIApps.IOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.example.utils.MobileTestUtils;
import org.openqa.selenium.By;

public class OnboardingPage {
    private final AppiumDriver driver;
    private final MobileTestUtils utils;

    public OnboardingPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver);

    }
    private final By checkboxBtn = AppiumBy.xpath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton[1]");
    private final By approveBtn = AppiumBy.accessibilityId("approve_button");
    private final By onaylaBtn = AppiumBy.accessibilityId("Onayla");
    private final By skipBtn = AppiumBy.accessibilityId("Geç");
    private final By sonrakiBtn = AppiumBy.accessibilityId("Sonraki");
    private final By yenidenBtn = AppiumBy.accessibilityId("Yeniden");
    private final By stepperTitle = AppiumBy.xpath("(//XCUIElementTypeStaticText)[2]");
    private final By stepperSubtitle = AppiumBy.xpath("(//XCUIElementTypeStaticText)[3]");
    String[] popupSelectors = {
            "//*[contains(@name,'Allow Once') or contains(@label,'Allow Once') or contains(@value,'Allow Once')]",
            "//*[contains(@name,'Allow') or contains(@label,'Allow') or contains(@value,'Allow')]",
            "~Allow",
            "~Allow Once",
            "(//*[contains(@name,'Allow') or contains(@label,'Allow') or contains(@value,'Allow')])[2]",
            "//*[contains(@name,'OK') or contains(@label,'OK') or contains(@value,'OK')]",
            "//*[contains(@name,'Ok') or contains(@label,'Ok') or contains(@value,'Ok')]",
            "~close",
            "~Close"
    };

    public void onboardingFlow() {
        utils.clickIfExists(checkboxBtn,5);
        utils.clickIfExists(approveBtn,5);
        utils.clickIfExists(onaylaBtn,5);
        utils.clickIfExists(skipBtn,5);
    }
    public void onboardingRetryFlow() throws Exception {
        utils.waitAndClick(checkboxBtn,5);
        utils.clickIfExists(approveBtn,5);
        utils.clickIfExists(onaylaBtn,5);

        utils.waitAndClick(sonrakiBtn,5);
        utils.waitAndClick(sonrakiBtn,5);
        utils.waitAndClick(sonrakiBtn,5);
        utils.waitAndClick(yenidenBtn,5);
        utils.assertElementText(stepperTitle,"Ses Kapatma");
        utils.assertElementText(stepperSubtitle,"Blocky’in cevapları ekranda yazılı olarak görüntülenir");
        utils.clickIfExists(skipBtn,5);
    }
    public void handleModal(){
        if(utils.waitUntilVisible(AppiumBy.accessibilityId("Lütfen daha sonra tekrar deneyiniz."),10)){
            utils.clickIfExists(AppiumBy.accessibilityId("close"),5);
            utils.clickIfExists(AppiumBy.accessibilityId("Close"),5);
            utils.clickIfExists(AppiumBy.xpath("//XCUIElementTypeButton[@name='Çıkış']"),5);
        }try{
            utils.handlePopups(popupSelectors,5,500);
        }catch (Exception e){
            e.getMessage();

        }
    }
}
