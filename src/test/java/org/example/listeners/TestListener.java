package org.example.listeners;

import org.example.config.DriverFactory;
import org.example.utils.reporting.ScreenshotHelper;
import org.example.utils.logging.LogLevel;
import org.testng.*;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "Test Failure Screenshot", LogLevel.ERROR);
        ScreenshotHelper.logToAllure("Test failed: " + result.getName(), LogLevel.ERROR);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "Test Skipped Screenshot", LogLevel.WARN);
        ScreenshotHelper.logToAllure("Test skipped: " + result.getName(), LogLevel.WARN);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Optional: capture on success
        // ScreenshotHelper.captureAndAttach(DriverFactory.getDriver(), "Test Passed", LogLevel.INFO);
        ScreenshotHelper.logToAllure("Test passed: " + result.getName(), LogLevel.INFO);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ScreenshotHelper.logToAllure("Test started: " + result.getName(), LogLevel.INFO);
    }

    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
}
