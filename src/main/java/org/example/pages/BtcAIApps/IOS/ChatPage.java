package org.example.pages.BtcAIApps.IOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Step;
import org.example.utils.logging.LogLevel;
import org.example.utils.MobileTestUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

import static org.example.utils.reporting.ScreenshotHelper.captureAndAttach;

public class ChatPage {

    private final AppiumDriver driver;
    private final MobileTestUtils utils;

    public ChatPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver);

    }
    private final By volume = AppiumBy.accessibilityId("Volume");
    private final By allowBtn = AppiumBy.accessibilityId("Allow");
    private final By chatText = AppiumBy.xpath("//XCUIElementTypeStaticText[@name='ben yapay zeka asistanınız Blocky']");
    private final String chatMessage = "Hoş geldiniz, ben yapay zeka asistanınız Blocky";
    private final By micBtn = AppiumBy.accessibilityId("Microphone");
    private final By micBtn2 = AppiumBy.accessibilityId("Mic");

    List<By> micOptions = List.of(
            micBtn,
            micBtn2
    );
    private final By keyboard = AppiumBy.accessibilityId("Keyboard");
    private final By inputField = AppiumBy.accessibilityId("İleti");
    private final By inputField_2 = AppiumBy.xpath("XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextView");
    private final By sendBtn = AppiumBy.accessibilityId("Send");
    private final By voiceReturn = AppiumBy.accessibilityId("Sesli sohbete dön");
    private final By closeBtn = AppiumBy.accessibilityId("Close");
    private final By closeBtn2 = AppiumBy.accessibilityId("close");
    private final By logoutBtn = AppiumBy.xpath("//XCUIElementTypeButton[@name='Çıkış']");
    // Text Chat Locators
    public static final By SEND_BTN = AppiumBy.accessibilityId("Send");
    public static final By AKBANK = AppiumBy.xpath("//XCUIElementTypeButton[@name='Akbank']");
    public static final By YENI_HESAP_TANIM = AppiumBy.xpath("//XCUIElementTypeButton[@name='Yeni Hesap Tanımla']");
    public static final By COPY_IBAN_BTN = AppiumBy.xpath("(//XCUIElementTypeButton[@name='Kopyala'])[1]");
    public static final By COPY_ALICI_BTN = AppiumBy.xpath("(//XCUIElementTypeButton[@name='Kopyala'])[2]");
    public static final By FIYAT_INPUT = AppiumBy.accessibilityId("Fiyat");
    public static final By ONAYLA_BTN = AppiumBy.accessibilityId("approve_button");
    public static final By SEND_BTN_2 = AppiumBy.xpath("(//XCUIElementTypeButton[@name='Send'])[1]");
    public static final By PARA_CEKME_MSG = AppiumBy.accessibilityId("Hesabınızdan para çekme işlemi gerçekleşti.");
    public static final By HESAP_TNM_INPUT_TXT = AppiumBy.xpath("//XCUIElementTypeTextView[1]");
    public static final By HESAP_TNM_INPUT_TXT2 = AppiumBy.xpath("//XCUIElementTypeTextView[2]");
    public static final By CHECKBOX = AppiumBy.xpath("//XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeButton[1]");
    public static final By RETURN_VOICE_CHAT = AppiumBy.accessibilityId("Sesli sohbete dön");
    public static final By HESAP_TNM_MSG = AppiumBy.accessibilityId("İşleminiz başarıyla gerçekleştirildi. Yardımcı olmamı istediğiniz başka bir konu var mı?");
    public static final By HESAP_TNM_MSG2 = AppiumBy.accessibilityId("Yeni hesap bilgileriniz onaylandı.");


    public static final By MESSAGE_INPUT_AREA = AppiumBy.xpath("XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]");
    public static final By MESSAGE_FIELD = AppiumBy.accessibilityId("İleti");
    public static final By SEND_BUTTON = AppiumBy.accessibilityId("Send");
    public static final By BTC_TRY_OPTION = AppiumBy.xpath("//XCUIElementTypeButton[@name='BTC/TRY']");
    public static final By VOICE_CHAT_BACK = AppiumBy.xpath("//*[contains(@name,'Sesli sohbete ')]");
    public static final By MARKET_BUTTON = AppiumBy.xpath("//XCUIElementTypeButton[@name='Piyasa']");
    public static final By STOP_MARKET_BUTTON = AppiumBy.xpath("//XCUIElementTypeButton[@name='Stop Piyasa']");
    public static final By LIMIT_BUTTON = AppiumBy.xpath("//XCUIElementTypeButton[@name='Limit']");
    public static final By STOP_LIMIT_BUTTON = AppiumBy.xpath("//XCUIElementTypeButton[@name='Stop Limit']");
    public static final By AMOUNT_FIELD = AppiumBy.xpath("//XCUIElementTypeApplication[@name='BTC-Ai']/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextView");
    public static final By APPROVE_BUTTON = AppiumBy.accessibilityId("approve_button");
    public static final By DONE_BUTTON = AppiumBy.xpath("//XCUIElementTypeButton[@name='Tamam']");
    public static final By SUCCESS_MESSAGE = AppiumBy.xpath("(//*[contains(@name,'İşleminizi başarıyla gerçekleştirdim')])[1]");
    public static final By FINAL_SUCCESS_LABEL = AppiumBy.accessibilityId("İşlem başarıyla gerçekleştirildi");

    @Step("Ses kapalı şekilde sesli sohbet başlatılıyor")
    public void voiceChatVolumeClosed() throws Exception {
        utils.waitForElement(volume,5);
        utils.clickIfExists(volume, 5);
        //utils.assertElementText(driver,chatText, chatMessage);
        utils.safeClickOneOf(micOptions);
        utils.clickIfExists(allowBtn, 5);

        utils.waitUntilVisible(micBtn,5);
        utils.safeClickOneOf(micOptions);
        utils.waitUntilVisible(volume,5);
        utils.clickIfExists(volume, 5);
    }
    @Step("Boş sesli sohbet testi")
    public void voiceChatEmpty() throws Exception {
        utils.safeClickOneOf(micOptions);
        Thread.sleep(10000);
        System.out.println("10 sn beklendi...");
        utils.safeClickOneOf(micOptions);
        Thread.sleep(5000);
        System.out.println("5 sn beklendi...");
    }
    @Step("Yazılı sohbet testi")
    public void textChat() throws Exception {
        utils.waitAndClick(keyboard, 5);
        utils.waitUntilVisible(inputField,5);
        utils.tapCenter(inputField);
        utils.inputValue(inputField_2, "Bugün Bitcoin fiyatı ne kadar?",15);
        utils.waitAndClick(sendBtn, 5);
        utils.inputValue(inputField_2, "Ethereum son 7 günde nasıl bir performans gösterdi?",5);
        utils.waitAndClick(sendBtn, 5);
        utils.inputValue(inputField_2, "Bitcoin'in son haftalardaki fiyat hareketlerine baktığımızda düşen bir trendin oluştuğu görülüyor...",5);
        utils.waitAndClick(sendBtn, 5);
        Thread.sleep(3000);
    }
    @Step("Yazılıdan sesliye geçiş")
    public void textToVoiceTransition() throws Exception {
        utils.clickIfExists(voiceReturn, 5);
        Thread.sleep(500);
        captureAndAttach(driver, "...", LogLevel.INFO);
    }
    @Step("Sesliden Yazılıya geçiş")
    public void voiceToTextTransition() throws Exception {
        utils.clickIfExists(keyboard, 5);
        Thread.sleep(500);
    }
    @Step("Sohbet kapatma")
    public void closeChat() throws Exception {
        utils.clickIfExists(closeBtn2, 5);
        Thread.sleep(500);
        utils.clickIfExists(closeBtn, 5);
        Thread.sleep(500);
        captureAndAttach(driver, "...", LogLevel.INFO);
        utils.clickIfExists(logoutBtn, 5);
        Thread.sleep(500);
        captureAndAttach(driver, "...", LogLevel.INFO);

        // Ana sayfa kontrolü
        try {
            utils.assertElementText(chatText, "BtcTurk Ana Sayfa");
        } catch (Exception ignored) {}
    }
    @Step("Para Yatırma Akışı")
    public void paraYatirmaFlow(String input) throws Exception {
        if (utils.waitUntilVisible(keyboard, 5)) {
            voiceToTextTransition();
        }else{
            voiceToTextTransition();
        }
        utils.tapCenter(inputField);
        utils.inputValue(inputField_2, input, 5);
        utils.waitAndClick(SEND_BTN, 5);
        utils.waitUntilVisible(AKBANK, 5);
        utils.waitAndClick(AKBANK, 5);
        Thread.sleep(5000);
    }
    @Step("Sesli Sohbet Akışı")
    public void voiceChatFlowProcess() throws Exception {
        if (utils.waitUntilVisible(voiceReturn, 5)) {
            textToVoiceTransition();
        }else{
            textToVoiceTransition();
        }
        utils.safeClickOneOf(micOptions);
        Thread.sleep(5000);
        utils.safeClickOneOf(micOptions);
        Thread.sleep(5000);
    }
    @Step("Sesli Sohbette Sessiz Sohbete Geçiş")
    public void voiceChatFlowVolumeCloseProcess() throws Exception {
        if (utils.waitUntilVisible(micBtn, 5)) {
            textToVoiceTransition();
        }else{
            textToVoiceTransition();
        }
        utils.waitAndClick(volume, 5);
        utils.safeClickOneOf(micOptions);
        Thread.sleep(5000);
        utils.safeClickOneOf(micOptions);
        Thread.sleep(5000);
    }
    @Step("Para Çekme Akışı")
    public void paraCekmeFlow(String input) throws Exception {
        if (utils.waitUntilVisible(micBtn, 10)) {
            voiceToTextTransition();
        }else{
            voiceToTextTransition();
        }

        utils.tapCenter(inputField);
        utils.waitUntilVisible(inputField_2, 5);
        utils.inputValue(inputField_2, input, 5);
        utils.waitAndClick(SEND_BTN, 5);
        utils.waitUntilVisible(By.xpath("(//XCUIElementTypeStaticText[@name='naim yapi kredi'])[1]"), 5);
        utils.waitAndClick(By.xpath("(//XCUIElementTypeStaticText[@name='naim yapi kredi'])[1]"), 5);
        utils.inputValue(By.xpath("XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextView"), "50", 5);

        if (utils.waitUntilVisible(SEND_BTN, 15)) {
            utils.tapCenter(SEND_BTN);
        } else {
            utils.tapCenter(SEND_BTN_2);
        }
        utils.waitUntilVisible(ONAYLA_BTN,5);
        utils.tapCenter(ONAYLA_BTN);
        utils.waitUntilVisible(PARA_CEKME_MSG,5);
        utils.assertElementText(PARA_CEKME_MSG,"Hesabınızdan para çekme işlemi gerçekleşti.");
    }
    @Step("Para Çekme İşlemi - Yeni Hesap Tanımlama İşlemi")
    public void paraCekmeYeniHesapTanimlaFlow(String input) throws Exception {
        utils.safeClickOneOf(micOptions);
        utils.tapCenter(inputField);
        utils.waitUntilVisible(inputField_2,5);
        utils.inputValue(inputField_2, input, 5);
        utils.waitAndClick(SEND_BTN, 5);
        utils.waitAndClick(YENI_HESAP_TANIM, 15);
        utils.waitUntilVisible(HESAP_TNM_INPUT_TXT,5);
        utils.inputValue(HESAP_TNM_INPUT_TXT, "Momentum Yeni Hesap", 5);
        utils.inputValue(HESAP_TNM_INPUT_TXT2, "330006100519786457841326", 5);
        utils.waitAndClick(ONAYLA_BTN, 5);
        utils.waitUntilVisible(CHECKBOX,5);
        utils.waitAndClick(CHECKBOX, 5);
        utils.waitAndClick(ONAYLA_BTN, 5);
        utils.waitUntilVisible(RETURN_VOICE_CHAT,5);
        utils.waitAndClick(RETURN_VOICE_CHAT, 5);
        utils.waitAndClick(HESAP_TNM_MSG, 5);
        utils.waitAndClick(keyboard, 5);
        utils.waitAndClick(HESAP_TNM_MSG2, 5);
    }
    @Step("Piyasa tipi Bitcoin Alım İşlemi")
    public void buyBitcoinWithMarketOrder(String amount) throws Exception {
        // Klavyeyi aç
        utils.waitAndClick(keyboard,5);
        Thread.sleep(1000);

        // Mesaj alanına tıkla ve mesaj yaz
        utils.waitAndClick(MESSAGE_INPUT_AREA,5);
        utils.inputValue(MESSAGE_INPUT_AREA, "BTC almak istiyorum",5);

        // Gönder
        utils.waitAndClick(SEND_BUTTON,5);

        // BTC/TRY seçimi yap
        utils.waitAndClick(BTC_TRY_OPTION,5);
        Thread.sleep(5000);

        // Sesli sohbete geri dön
        utils.waitAndClick(VOICE_CHAT_BACK,5);
        utils.waitAndClick(keyboard,5);

        // Emir tipi seçeneklerini kontrol et
        utils.waitUntilVisible(MARKET_BUTTON,5);
        utils.waitUntilVisible(STOP_MARKET_BUTTON,5);
        utils.waitUntilVisible(LIMIT_BUTTON,5);
        utils.waitUntilVisible(STOP_LIMIT_BUTTON,5);

        // Piyasa emrini seç
        utils.waitAndClick(MARKET_BUTTON,5);

        // Geri dön
        utils.waitAndClick(VOICE_CHAT_BACK,5);
        utils.waitAndClick(keyboard,5);

        // Tutar gir
        utils.inputValue(AMOUNT_FIELD, amount,5);

        // Onayla
        utils.waitAndClick(DONE_BUTTON,5);

        // Geri dön
        utils.waitAndClick(VOICE_CHAT_BACK,5);

        // Başarı mesajını bekle
        utils.waitUntilVisible(SUCCESS_MESSAGE,5);
        utils.waitAndClick(keyboard,5);

        // Başarı mesajını doğrula
        String actualMessage = driver.findElement(FINAL_SUCCESS_LABEL).getAttribute("name");
        Assert.assertEquals(actualMessage, "İşleminiz başarıyla gerçekleştirildi");

    }
}
