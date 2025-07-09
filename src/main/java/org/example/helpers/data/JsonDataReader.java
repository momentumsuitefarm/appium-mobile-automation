package org.example.helpers.data;

import org.example.config.ConfigReader;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonDataReader {

    public static JSONObject getAppData(String appKey) {
        try {
            InputStream is = JsonDataReader.class.getClassLoader().getResourceAsStream("test-data.json");
            System.out.println("📁 test-settings.json bulundu mu? " +
                    (ConfigReader.class.getClassLoader().getResource("test-settings.json") != null));
            System.out.println("📁 testdata.json bulundu mu? " +
                    (ConfigReader.class.getClassLoader().getResource("testdata.json") != null));

            if (is == null) {
                throw new RuntimeException("test-data.json dosyası bulunamadı!");
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(content);
            return json.getJSONObject("testFlightApps").getJSONObject(appKey);
        } catch (Exception e) {
            throw new RuntimeException("❌ test-data.json okunamadı ya da key hatalı: " + e.getMessage());
        }
    }
}
