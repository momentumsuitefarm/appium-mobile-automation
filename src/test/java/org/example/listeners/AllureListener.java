package org.example.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

import static org.example.config.DriverFactory.getDriver;

public class AllureListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot();  // ekran görüntüsü başarılı testte de alınır
        attachLog("Test passed: " + result.getName());
    }


    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot();
        attachLog(result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachScreenshot();
        attachLog("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private void attachScreenshot() {
        try {
            WebDriver driver = getDriver();
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshot));
            }
        } catch (Exception e) {
            Allure.addAttachment("Screenshot error", e.getMessage());
        }
    }

    private void attachLog(String message) {
        Allure.addAttachment("Log", message);
    }
}
