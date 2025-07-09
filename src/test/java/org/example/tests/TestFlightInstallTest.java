package org.example.tests;

import io.appium.java_client.ios.IOSDriver;
import org.example.helpers.data.JsonDataReader;
import org.json.JSONObject;
import org.example.config.DriverFactory;
import org.example.pages.testFlightPages.TestFlightInstallPage;
import org.testng.SkipException;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestFlightInstallTest extends BaseTest {

    @Test(description = "TestFlight install test for specific app")
    @Parameters({ "platform", "appKey" })
    public void installAndOpenTestFlightApp(@Optional("ios") String platform,
                                            @Optional("") String appKey) throws Exception {
        if (appKey == null || appKey.isEmpty()) {
            throw new SkipException("Bu test için appKey gerekli ama verilmedi. Atlanıyor.");
        }

        if (!platform.equalsIgnoreCase("ios")) {
            System.out.println("ℹ️ iOS dışı platform algılandı, bu test çalıştırılmayacak.");
            return;
        }

        JSONObject config = JsonDataReader.getAppData(appKey);
        String testFlightBundle = config.getString("bundleId");
        String appBundleId = config.getString("targetBundleId");
        String appName = config.getString("appName");
        TestFlightInstallPage testFlightInstallPage = new TestFlightInstallPage((IOSDriver) DriverFactory.getDriver());
        testFlightInstallPage.tfPAppInstall(testFlightBundle,appName,appBundleId);

        // TestFlightInstallPage tfPage = new TestFlightInstallPage((IOSDriver) DriverFactory.getDriver());
        // tfPage.tfPAppInstall(testFlightBundle , appName , appBundleId );
    }
}

