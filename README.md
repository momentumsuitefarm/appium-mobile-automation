# Madduck Mobile Automation Projesi

## Genel Bakış

Bu proje, **Java + TestNG** tabanlı, hem **cloud** (ör. Momentum Suite) hem de **local** ortamda, **Android** ve **iOS** cihazlarda, **tekli (single)** ve **paralel** test koşumlarını destekleyen gelişmiş bir mobil otomasyon altyapısı sunar. Proje, modern Page Object Model (POM) mimarisi, Allure raporlama, Slack entegrasyonu ve iki faktörlü doğrulama (2FA) desteği ile kurumsal seviyede test otomasyonu sağlar.

---

## İçerik

- [Proje Mimarisi](#proje-mimarisi)
- [Kurulum](#kurulum)
- [Konfigürasyon](#konfigürasyon)
- [Test Koşumları](#test-koşumları)
- [Raporlama ve Allure](#raporlama-ve-allure)
- [Docker ile Allure UI](#docker-ile-allure-ui)
- [Örnek Test ve Page Object](#örnek-test-ve-page-object)
- [Yardımcı Scriptler](#yardımcı-scriptler)
- [Gelişmiş Özellikler](#gelişmiş-özellikler)
- [Klasör Yapısı](#klasör-yapısı)

---

## Proje Mimarisi

- **Çoklu Platform:** Android ve iOS desteği.
- **Çoklu Ortam:** Local ve Cloud (Momentum Suite) desteği.
- **Paralel Test:** TestNG ile paralel test koşumu (thread-count, parallel="tests").
- **Page Object Model:** Her uygulama ve platform için ayrı page class'ları.
- **Config ve Test Data:** JSON tabanlı merkezi ayar ve test verisi yönetimi.
- **Allure Raporlama:** Otomatik ve zengin test raporları.
- **Slack Entegrasyonu:** 2FA kodlarını Slack üzerinden otomatik çekme.
- **Yardımcı Sınıflar:** Loglama, screenshot, mobil yardımcılar, 2FA handler vb.

---

## Kurulum

1. **Java 11+** ve **Maven** kurulu olmalı.
2. Gerekli bağımlılıklar için:

```bash
mvn clean install
```

3. **Allure CLI** kurulu olmalı:

```bash
brew install allure  # MacOS için
# veya
npm install -g allure-commandline --save-dev
```

4. (Opsiyonel) Allure UI için Docker gereklidir.

---

## Konfigürasyon

### test-settings.json

Test ortamı, cihaz ve uygulama bilgileri `src/test/resources/test-settings.json` dosyasında tutulur:

```json
{
  "runMode": "LOCAL",
  "LOCAL": {
    "host": "http://127.0.0.1:4723/wd/hub",
    "android": {
      "platform": "android",
      "deviceName": "your_Device_name",
      "udid": "your_device_udid",
      "appPath": "../../sample.apk"
    },
    "ios": {
      "platform": "ios",
      "deviceName": "your_Device_name",
      "udid": "your_device_udid",
      "appPath": "../../sample.ipa"
    }
  },
  "CLOUD": {
    "host": "https://console.momentumsuite.com/gateway/wd/hub",
    "momentum": {
      "user": "kullanici@ornek.com",
      "token": "..."
    },
    "android": {
      "app": "ms://...",
      "deviceList": [4034,4035,4039],
      "deviceName": ""
    },
    "ios": {
      "app": "",
      "deviceList":[],
      "deviceName": ""
    }
  }
}
```

### test-data.json

TestFlight uygulama ve test verileri için örnek:

```json
{
  "testFlightApps": {
    "Speed": {
      "bundleId": "com.apple.TestFlight",
      "appName": "Speed Readingo: Read 3x Faster",
      "targetBundleId": "wabyilmaz.speedReadingo"
    }
  }
}
```

---

## Test Koşumları

### Local Single Test

```bash
mvn test -DsuiteXmlFile=local-android.xml
```

### Local Paralel Test

```bash
mvn test -DsuiteXmlFile=local-android-parallel.xml
```

### Cloud Single Test

```bash
mvn test -DsuiteXmlFile=cloud-android.xml
```

### Cloud Paralel Test

```bash
mvn test -DsuiteXmlFile=cloud-android-parallel.xml
```

### iOS için de benzer şekilde `local-ios.xml`, `cloud-ios.xml`, `local-ios-parallel.xml`, `cloud-ios-parallel.xml` kullanılabilir.

#### Örnek Paralel Suite:

```xml
<suite name="CloudAndroidParallelSuite" parallel="tests" thread-count="3">
    <test name="Android-4034">
        <parameter name="platform" value="android"/>
        <parameter name="runMode" value="CLOUD"/>
        <parameter name="deviceId" value="4034"/>
        <classes>
            <class name="org.example.tests.BtcAITests.Android.LoginTest"/>
        </classes>
    </test>
    ...
</suite>
```

---

## Raporlama ve Allure

### Rapor Oluşturma

Testler koştuktan sonra Allure raporu oluşturmak için:

```bash
bash generate-allure-report.sh
# veya
allure generate allure-results --clean -o allure-report
```

Raporu görüntülemek için:

```bash
allure open allure-report
```

### Otomatik Rapor Gönderimi

Test suite sonunda `send_result.sh` scripti ile Allure Docker Service'e otomatik rapor gönderimi yapılır.

---

## Docker ile Allure UI

Allure raporlarını merkezi bir UI'da görmek için:

```bash
docker-compose up -d
```

- Allure API: [http://localhost:8181](http://localhost:8181)
- Allure UI: [http://localhost:8282](http://localhost:8282)

---

## Örnek Test ve Page Object

### Page Object (iOS):

```java
public class LoginPage {
    private final AppiumDriver driver;
    private final MobileTestUtils utils;
    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.utils = new MobileTestUtils(driver);
    }
    By inputUsername = AppiumBy.xpath("//*[contains(@type,'XCUIElementTypeTextField')]");
    By girisBtn = AppiumBy.accessibilityId("BtcTurk Ai Assistant");
    public void loginWithRandomUsername() throws Exception {
        String username = utils.generateRandomUsername();
        utils.inputValue(inputUsername,username,15);
        utils.waitAndClick(girisBtn,15);
    }
}
```

### Test Sınıfı:

```java
public class LoginTest extends BaseTest {
    @Test(description = "Login test with random username")
    public void loginTest() throws Exception {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.loginWithRandomUsername();
    }
}
```

---

## Yardımcı Scriptler

- **generate-allure-report.sh:** Allure raporu oluşturur.
- __send_result.sh:__ Sonuçları Allure Docker Service'e gönderir ve rapor URL'sini terminalde gösterir.

---

## Gelişmiş Özellikler

- **Slack Entegrasyonu:** 2FA kodları otomatik olarak Slack kanalından çekilir.
- **Otomatik Screenshot:** Her adımda ve hata durumunda ekran görüntüsü Allure'a eklenir.
- **Loglama:** Her test için ayrı log dosyası ve Allure entegrasyonu.
- **Test Data ve Config:** JSON dosyalarından dinamik veri okuma.
- **TestFlight Desteği:** TestFlight uygulamaları için özel reset ve bundleId yönetimi.
- **Paralel Test:** TestNG ile thread bazlı paralel koşum desteği.

---

## Paralel Test Mantığı ve Driver Yönetimi

### Paralel Test Koşumu

- Projede TestNG'nin `parallel="tests"` ve `thread-count` parametreleri ile paralel test desteği sağlanır.

- Her test için farklı cihaz/oturum parametreleri suite dosyalarında tanımlanır.

- Örnek:

```xml
<suite name="CloudAndroidParallelSuite" parallel="tests" thread-count="3">
    <test name="Android-4034"> ... </test>
    <test name="Android-4035"> ... </test>
    <test name="Android-4039"> ... </test>
</suite>
```

### Driver Yönetimi

- **DriverFactory** sınıfı, her test thread'i için ayrı bir AppiumDriver örneği oluşturur.
- Bunu sağlamak için `ThreadLocal<AppiumDriver>` kullanılır. Böylece paralel testlerde driver çakışması olmaz.
- DriverFactory:
   - `initDriver()` ile thread'e özel driver başlatır.
   - `getDriver()` ile o thread'e ait driver'ı döner.
   - `quitDriver()` ile thread bitiminde driver'ı kapatır.

- Cihaz ve ortam bilgileri (cloud/local, udid, deviceId, appPath vs.) parametre olarak veya config dosyasından alınır.

---

## BaseTest Sınıfı

- Tüm test sınıfları BaseTest'i extend eder.
- BaseTest:
   - Test başında (BeforeMethod) parametreleri alır, DeviceManager ve DriverFactory ile driver'ı başlatır.
   - Test sonunda (AfterMethod) testin sonucuna göre otomatik screenshot ve log ekler, Allure raporuna işler.
   - Suite sonunda (AfterSuite) driver'ı kapatır ve Allure raporunu otomatik olarak gönderir.

- Böylece setup/teardown, raporlama ve driver yönetimi merkezi ve güvenli şekilde sağlanır.

---

## Klasör Yapısı (Özet)

```ini
madduckAutomation/
├── src/
│   ├── main/java/org/example/
│   │   ├── config/           # ConfigReader, DriverFactory, DeviceManager
│   │   ├── core/drivers/     # Cihaz yönetimi
│   │   ├── helpers/          # Slack, 2FA, data helpers
│   │   ├── pages/            # Page Object'ler (uygulama/platform bazlı)
│   │   ├── utils/            # Log, screenshot, mobile utils
│   └── test/java/org/example/
│       ├── tests/            # Test sınıfları (uygulama/platform bazlı)
│       └── listeners/        # TestNG ve Allure listener'ları
├── projects/                 # Allure rapor ve sonuç klasörleri
├── docker-compose.yml        # Allure Docker Service için
├── generate-allure-report.sh # Allure rapor scripti
├── send_result.sh            # Sonuç gönderim scripti
├── testng.xml                # Ana TestNG suite
├── cloud-*.xml, local-*.xml  # Farklı ortam ve paralellik için suite dosyaları
└── src/test/resources/
    ├── test-settings.json    # Ortam ve cihaz ayarları
    └── test-data.json        # Test verileri
```

---

## Katkı ve Geliştirme

- Kodunuzu POM ve mevcut mimariye uygun şekilde yazınız.
- Yeni cihaz/ortam eklemek için sadece `test-settings.json` ve ilgili suite dosyasını güncellemeniz yeterlidir.
- Sorularınız için proje sahibi ile iletişime geçebilirsiniz.

---