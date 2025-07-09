package org.example.tests.BtcAITests.IOS;

import org.example.config.DriverFactory;
import org.example.pages.BtcAIApps.IOS.ChatPage;
import org.example.pages.BtcAIApps.IOS.LoginPage;
import org.example.pages.BtcAIApps.IOS.OnboardingPage;
import org.example.tests.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ChatTest extends BaseTest {


        private LoginPage loginPage;
        private ChatPage chatPage;
        private OnboardingPage onboardingPage;

        @BeforeMethod(alwaysRun = true)
        public void prepareTest() throws Exception {
           // loginPage = new LoginPage(DriverFactory.getDriver());  // ✅ field'a atama
           // chatPage = new ChatPage(DriverFactory.getDriver());    // ✅ field'a atama
            loginPage = new LoginPage(driver);
            chatPage = new ChatPage(driver);
            chatPage.closeChat();
            loginPage.loginWithRandomUsername();
        }


        @Test(priority = 1,description = "Voice Chat Volume Closed")
        public void testVoiceChatVolumeClosed() throws Exception {
            chatPage.voiceChatVolumeClosed();
        }

        @Test(priority = 2,description = "Text Chat Flow")
        public void testTextChatFlow() throws Exception {
            chatPage.textChat();
            chatPage.textToVoiceTransition();
        }

        @Test(priority = 3,description = "Para Yatırma Akışı")
        public void testParaYatirmaFlow() throws Exception {
            chatPage.paraYatirmaFlow("Para Yatırma");
            chatPage.voiceChatFlowProcess();
        }

        @Test(priority = 4,description = "Para Çekme Yeni Hesap")
        public void testParaCekmeYeniHesapFlow() throws Exception {
            chatPage.paraCekmeFlow("Para Çekmek İstiyorum");
            chatPage.paraCekmeYeniHesapTanimlaFlow("Para Çekmek İstiyorum");
        }

        @Test(priority = 5,description = "Voice Chat Empty")
        public void testVoiceChatEmpty() throws Exception {
            chatPage.voiceChatEmpty();
        }

        @Test(priority = 6,description = "Voice Chat Volume Close")
        public void voiceChatVolumeClose() throws Exception {
            chatPage.voiceChatFlowVolumeCloseProcess();
        }

        @Test(priority = 7,description = "Piyasa emir tipinde Bitcoin Alma İşlemi")
        public void buyBitcoinWithMarketOrder() throws Exception {
            chatPage.buyBitcoinWithMarketOrder("100");
        }

    }



