package org.example.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.example.core.drivers.DeviceManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static void initDriver(DeviceManager deviceManager) throws Exception {
        if (isDriverInitialized()) return;

        DesiredCapabilities caps = new DesiredCapabilities();
        fillCommonCapabilities(deviceManager, caps);
        fillPlatformCapabilities(deviceManager, caps);

        URL serverUrl = new URL(deviceManager.getHostUrl());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String capsJson = gson.toJson(caps.asMap());
        logger.info("🚀 Final Capabilities:\n{}", capsJson);
        AppiumDriver driver = createDriverByPlatform(deviceManager.getPlatform(), serverUrl, caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);

        logger.info("✅ Driver initialized for platform: {}", deviceManager.getPlatform());
    }
    private static void fillCommonCapabilities(DeviceManager dm, DesiredCapabilities caps) {

        boolean reset = dm.isAppResetEnabled();
        caps.setCapability("appium:platformName", dm.getPlatform());
        caps.setCapability("appium:automationName", dm.getAutomationName());
        caps.setCapability("appium:app", dm.getAppPath());
        caps.setCapability("appium:fullReset", reset);
        caps.setCapability("appium:noReset", !reset);
    }

    private static void fillPlatformCapabilities(DeviceManager dm, DesiredCapabilities caps) {
        String platform = dm.getPlatform();
        String runMode = dm.getRunMode();

        // ✅ Dinamik reset flag yönetimi (her platformda geçerli)
        if (dm.isAppResetEnabled()) {
            caps.setCapability("appium:noReset", false);
            caps.setCapability("appium:fullReset", true);
        } else {
            caps.setCapability("appium:noReset", true);
            caps.setCapability("appium:fullReset", false);
        }

        if ("android".equalsIgnoreCase(platform)) {
            caps.setCapability("appium:autoGrantPermissions", true);
            caps.setCapability("appium:locale", "TR");
            caps.setCapability("appium:language", "tr");

        } else if ("ios".equalsIgnoreCase(platform)) {
            caps.setCapability("appium:autoAcceptAlerts", true);
            caps.setCapability("appium:autoDismissAlerts", true);
            caps.setCapability("appium:locale", "tr_TR");
            caps.setCapability("appium:language", "tr");

            // iOS özel port ve cihaz yönetimi
            Map<String, String> ports = calculateIosPorts(dm, runMode);
            caps.setCapability("appium:wdaLocalPort", ports.get("wdaLocalPort"));
            caps.setCapability("appium:webkitDebugProxyPort", ports.get("webkitDebugProxyPort"));
            logger.debug("📱 iOS Port Config -> wdaLocalPort: {}, webkitDebugProxyPort: {}",
                    ports.get("wdaLocalPort"), ports.get("webkitDebugProxyPort"));

            if ("CLOUD".equalsIgnoreCase(runMode)) {
                requireCloudValues(dm);
                Map<String, Object> momentumOpts = new HashMap<>();
                momentumOpts.put("user", dm.getCloudUser());
                momentumOpts.put("token", dm.getCloudToken());
                momentumOpts.put("gw", dm.getDeviceId());
                caps.setCapability("momentum:options", momentumOpts);

                caps.setCapability("appium:deviceName", "");
                caps.setCapability("appium:udid", "");
            } else {
                caps.setCapability("appium:deviceName", dm.getDeviceName());
                caps.setCapability("appium:udid", dm.getUdid());
            }
        } else {
            throw new IllegalArgumentException("❌ Unsupported platform: " + platform);
        }
    }

    private static Map<String, String> calculateIosPorts(DeviceManager dm, String runMode) {
        Map<String, String> ports = new HashMap<>();
        int portOffset = 2000;
        int localPort;
        int proxyPort;

        if ("CLOUD".equalsIgnoreCase(runMode)) {
            int deviceId = Integer.parseInt(dm.getDeviceId());
            localPort = deviceId + portOffset;
            proxyPort = deviceId + portOffset;
        } else {
            int basePort = 8100;
            int baseProxy = 9100;
            try {
                int spread = Integer.parseInt(dm.getUdid().replaceAll("\\D", "")) % 100;
                basePort += spread;
                baseProxy += spread;
            } catch (Exception e) {
                basePort = 8101;
                baseProxy = 9101;
            }
            localPort = basePort + portOffset;
            proxyPort = baseProxy + portOffset;
        }

        ports.put("wdaLocalPort", String.valueOf(localPort));
        ports.put("webkitDebugProxyPort", String.valueOf(proxyPort));
        return ports;
    }

    private static AppiumDriver createDriverByPlatform(String platform, URL url, DesiredCapabilities caps) {
        if ("android".equalsIgnoreCase(platform)) {
            return new AndroidDriver(url, caps);
        } else if ("ios".equalsIgnoreCase(platform)) {
            return new IOSDriver(url, caps);
        } else {
            throw new IllegalArgumentException("❌ Unsupported platform: " + platform);
        }
    }

    private static void requireCloudValues(DeviceManager d) {
        requireNonEmpty(d.getCloudUser(), "cloudUser");
        requireNonEmpty(d.getCloudToken(), "cloudToken");
        requireNonEmpty(d.getDeviceId(), "deviceId");
    }

    private static void requireNonEmpty(String value, String name) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("❌ Required value missing or empty: " + name);
        }
    }

    public static AppiumDriver getDriver() {
        if (!isDriverInitialized()) {
            throw new IllegalStateException("❌ Driver is not initialized. Call initDriver() first.");
        }
        return driverThread.get();
    }

    public static void quitDriver() {
        if (isDriverInitialized()) {
            driverThread.get().quit();
            driverThread.remove();
            logger.info("🛑 Driver quit successfully.");
        }
    }

    public static boolean isDriverInitialized() {
        return driverThread.get() != null;
    }

}
