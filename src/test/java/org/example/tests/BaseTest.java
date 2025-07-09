// File: BaseTest.java
package org.example.tests;

import io.appium.java_client.AppiumDriver;
import org.example.config.DriverFactory;
import org.example.core.drivers.DeviceManager;
import org.example.pages.BtcAIApps.IOS.OnboardingPage;
import org.example.utils.logging.LogLevel;
import org.example.utils.logging.LoggerHelper;
import org.example.utils.reporting.ScreenshotHelper;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class BaseTest {
    protected static AppiumDriver driver;
    private static DeviceManager deviceManager;
    @BeforeMethod(alwaysRun = true)
    @Parameters({"platform", "udid", "appPath", "runMode", "deviceId", "appKey"})
    public void beforeMethod(@Optional("") String platform,
                             @Optional("") String udid,
                             @Optional("") String appPath,
                             @Optional("LOCAL") String runMode,
                             @Optional("") String deviceId,
                             @Optional("") String appKey,
                             Method method) throws Exception {

        if (!DriverFactory.isDriverInitialized()) {
            deviceManager = new DeviceManager(platform, udid, appPath, runMode, deviceId, appKey);
            DriverFactory.initDriver(deviceManager);
        }
        driver = DriverFactory.getDriver();

        LoggerHelper.startTestLog(method.getName());
        ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "📸 Test Başlangıcı", LogLevel.INFO);
        LogLevel.INFO.print("BEFORE METHOD : [BaseTest] driver instance: " + DriverFactory.getDriver());
    }
    /*
    @BeforeMethod(alwaysRun = true)
    public void setupBeforeEachTest(Method method)   {
        // Modal işlemleri
        OnboardingPage onboardingPage = new OnboardingPage(driver);
        onboardingPage.handleModal();
        LoggerHelper.startTestLog(method.getName());

        // Screenshot ve log
        ScreenshotHelper.captureAndAttach(driver, "📸 Test Başlangıcı", LogLevel.INFO);
        LogLevel.INFO.print("BEFORE METHOD : [BaseTest] driver instance: " + driver);

    }
    */
    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(ITestResult result) {
        String methodName = result.getMethod().getMethodName();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "🟢 Test Başarılı: " + methodName, LogLevel.INFO);
                ScreenshotHelper.logToAllure("Test başarıyla tamamlandı: " + methodName, LogLevel.INFO);
                LoggerHelper.log(LogLevel.INFO, "✅ TEST PASSED: " + methodName);
                break;

            case ITestResult.FAILURE:
                ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "🔴 Test Hatalı: " + methodName, LogLevel.ERROR);
                ScreenshotHelper.captureAndSave(DriverFactory.getDriver(), methodName); // fiziksel dosyaya da kaydet
                ScreenshotHelper.logToAllure("Test başarısız oldu: " + methodName + "\n" +
                        "Hata mesajı: " + result.getThrowable().getMessage(), LogLevel.ERROR);
                LoggerHelper.log(LogLevel.ERROR, "❌ TEST FAILED: " + methodName);
                break;

            case ITestResult.SKIP:
                ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "⚠️ Test Atlandı: " + methodName, LogLevel.WARN);
                ScreenshotHelper.logToAllure("Test atlandı: " + methodName, LogLevel.WARN);
                LoggerHelper.log(LogLevel.WARN, "⚠️ TEST SKIPPED: " + methodName);
                break;
        }
        LoggerHelper.endTestLog(methodName);
        System.out.println("AFTER METHOD : [BaseTest] driver instance: " + DriverFactory.getDriver());
    }
    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException, InterruptedException {
        DriverFactory.quitDriver();
        System.out.println("AFTER SUITE : Driver başarıyla kapatıldı ...");
        ProcessBuilder pb = new ProcessBuilder("bash", "send_result.sh");
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("✅ Allure raporu başarıyla gönderildi.");
        } else {
            System.err.println("❌ Allure script başarısız çıktı kodu: " + exitCode);
        }

        Files.list(Paths.get("allure-results")).forEach(System.out::println);
    }
}
