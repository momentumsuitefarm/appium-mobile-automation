package org.example.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigReader {

    private static final JsonObject config;
    private static final JsonObject testData;

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("test-settings.json")) {
            if (is == null) {
                throw new RuntimeException("❌ test-settings.json classpath içinde bulunamadı.");
            }
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            config = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println("✅ test-settings.json başarıyla yüklendi.");
        } catch (Exception e) {
            throw new RuntimeException("❌ test-settings.json okunamadı!", e);
        }

        JsonObject tempTestData = null;
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("test-data.json")) {
            if (is != null) {
                InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                tempTestData = JsonParser.parseReader(reader).getAsJsonObject();
                System.out.println("✅ test-data.json başarıyla yüklendi.");
            } else {
                System.out.println("⚠️ test-data.json bulunamadı. Bu dosya opsiyonel sayıldı.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ test-data.json okunamadı: " + e.getMessage());
        }
        testData = tempTestData;
    }

    public static String get(String... keys) {
        JsonElement current = config;
        for (String key : keys) {
            if (current == null || !current.isJsonObject()) return null;
            current = current.getAsJsonObject().get(key);
        }
        return current != null && current.isJsonPrimitive() ? current.getAsString() : null;
    }

    public static JsonObject getAsJsonObject(String... keys) {
        JsonElement current = config;
        for (String key : keys) {
            if (current == null || !current.isJsonObject()) return null;
            current = current.getAsJsonObject().get(key);
        }
        return current != null && current.isJsonObject() ? current.getAsJsonObject() : null;
    }

    public static String getTestData(String... keys) {
        if (testData == null) {
            System.out.println("⚠️ test-data.json yüklenmedi. getTestData null döner.");
            return null;
        }
        JsonElement current = testData;
        for (String key : keys) {
            if (current == null || !current.isJsonObject()) return null;
            current = current.getAsJsonObject().get(key);
        }
        return current != null && current.isJsonPrimitive() ? current.getAsString() : null;
    }

    public static JsonObject getTestDataAsJsonObject(String... keys) {
        if (testData == null) {
            System.out.println("⚠️ test-data.json yüklenmedi. getTestDataAsJsonObject null döner.");
            return null;
        }
        JsonElement current = testData;
        for (String key : keys) {
            if (current == null || !current.isJsonObject()) return null;
            current = current.getAsJsonObject().get(key);
        }
        return current != null && current.isJsonObject() ? current.getAsJsonObject() : null;
    }

    public static String getRunMode() {
        return get("runMode");
    }
}
