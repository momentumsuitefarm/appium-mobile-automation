package org.example.core.drivers;

import org.example.config.ConfigReader;

import java.util.Optional;

public class DeviceManager {
    private final String platform;
    private final String udid;
    private final String appPath;
    private final String runMode;
    private final String deviceId;
    private final String appKey; // ✅ opsiyonel parametre
/*
    // Yeni constructor: appKey opsiyonel
    public DeviceManager(String platform, String udid, String appPath, String runMode, String deviceId) {
        this(platform, udid, appPath, runMode, deviceId, null);
    }
*/
    // Tam parametreli constructor: appKey dahil
    public DeviceManager(String platform, String udid, String appPath, String runMode, String deviceId, String appKey) {
        this.platform = (platform != null && !platform.trim().isEmpty())
                ? platform.trim().toLowerCase()
                : "android";

        this.runMode = Optional.ofNullable(runMode).filter(r -> !r.isEmpty())
                .orElse(ConfigReader.getRunMode()).toUpperCase();
        this.deviceId = deviceId;
        this.appKey = appKey;

        System.out.println("[DeviceManager] Gelen parametre platform: '" + platform + "' -> Atanan: '" + this.platform + "'");
        System.out.println("[DeviceManager] runMode: " + this.runMode + ", deviceId: " + this.deviceId);
        System.out.println("[DeviceManager] appKey: " + this.appKey);

        if (isLocal()) {
            this.udid = Optional.ofNullable(udid).filter(u -> !u.isEmpty())
                    .orElse(ConfigReader.get("LOCAL", this.platform, "udid"));
            this.appPath = Optional.ofNullable(appPath).filter(a -> !a.isEmpty())
                    .orElse(ConfigReader.get("LOCAL", this.platform, "appPath"));
        } else {
            this.udid = "";
            this.appPath = Optional.ofNullable(appPath).filter(a -> !a.isEmpty())
                    .orElse(ConfigReader.get("CLOUD", this.platform, "app"));
        }

        System.out.println("[DeviceManager] Final platform: " + this.platform);
        System.out.println("[DeviceManager] Final appPath: " + this.appPath);
        System.out.println("[DeviceManager] Final udid: " + this.udid);
    }

    // Getter
    public boolean isLocal() {
        return "LOCAL".equalsIgnoreCase(this.runMode);
    }

    public boolean isCloud() {
        return "CLOUD".equalsIgnoreCase(this.runMode);
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getUdid() {
        return this.udid;
    }

    public String getAppPath() {
        return this.appPath;
    }

    public String getRunMode() {
        return this.runMode;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getAutomationName() {
        return "ios".equalsIgnoreCase(platform) ? "XCUITest" : "UIAutomator2";
    }

    public String getHostUrl() {
        return isLocal() ? ConfigReader.get("LOCAL", "host") : ConfigReader.get("CLOUD", "host");
    }

    public String getDeviceName() {
        return isLocal() ? ConfigReader.get("LOCAL", platform, "deviceName") : "";
    }

    public String getCloudUser() {
        return ConfigReader.get("CLOUD", "momentum", "user");
    }

    public String getCloudToken() {
        return ConfigReader.get("CLOUD", "momentum", "token");
    }

    public boolean isAppResetEnabled() {
        // ✅ Önce appKey üzerinden TestFlight match dene
        if (this.appKey != null && !this.appKey.isEmpty()) {
            if (ConfigReader.getTestDataAsJsonObject("testFlightApps") != null &&
                    ConfigReader.getTestDataAsJsonObject("testFlightApps").has(this.appKey)) {
                System.out.println("[DeviceManager] ✅ appKey üzerinden TestFlight app match edildi: " + this.appKey);
                return false; // TestFlight app için reset gerekmez
            }
        }

        // Eski fallback: appPath match
        String targetBundleId = getTargetBundleIdFromTestData();
        if (targetBundleId != null && !targetBundleId.isEmpty()) {
            System.out.println("[DeviceManager] ✅ appPath üzerinden TestFlight match: " + targetBundleId);
            return false;
        }

        System.out.println("[DeviceManager] 🚀 Normal app yükle/sil senaryosu. Reset aktif.");
        return true;
    }

    private String getTargetBundleIdFromTestData() {
        var testFlightApps = ConfigReader.getTestDataAsJsonObject("testFlightApps");
        if (testFlightApps == null) return null;
        for (String key : testFlightApps.keySet()) {
            var appInfo = testFlightApps.getAsJsonObject(key);
            String appName = appInfo.has("appName") ? appInfo.get("appName").getAsString() : "";
            String targetBundleId = appInfo.has("targetBundleId") ? appInfo.get("targetBundleId").getAsString() : "";
            if (this.appPath != null && this.appPath.toLowerCase().contains(appName.toLowerCase())) {
                return targetBundleId;
            }
        }
        return null;
    }
}
