package in.at.util;

import in.at.basetest.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserUtil {

    protected static ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static GeckoDriverService FFService;
    public static ChromeDriverService GCService;

    public static String logFolder = null;

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void launchChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver","Z:\\JarsNDrivers\\chromedriver.exe");
        logFolder = Constants.LOG_DIR + "GCDriveLogs_" + CommonUtil.getCurrentDateTime("MMMMM_dd_yyyy");
        CommonUtil.createFolder(logFolder);

        File logsFile = new File(logFolder + "/" + "ChromeDriver_"
                + CommonUtil.getCurrentDateTime("dd_HH_mm") + "_Logs.log");
        if (logsFile.exists()) {
            logsFile.delete();
        }
        GCService = new ChromeDriverService.Builder().usingAnyFreePort().withLogFile(logsFile).build();
        try {
            GCService.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDriver(new ChromeDriver(GCService, getChromeOptions()));
        //setDriver(new ChromeDriver(options));
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().get(BaseTest.properties.getProperty("appURL"));
    }

    public static void launchFirefoxBrowser() {

        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");

        logFolder = Constants.LOG_DIR + "FFDriver_Logs_" + CommonUtil.getCurrentDateTime("MMMMM_dd_yyyy");
        CommonUtil.createFolder(logFolder);

        File logsFile = new File(logFolder + "/" + "GeckoDriver_"
                + CommonUtil.getCurrentDateTime("dd_HH_mm") + "_Logs.log");
        if (logsFile.exists()) {
            logsFile.delete();
        }
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, logsFile.toString());
        FFService = new GeckoDriverService.Builder().usingAnyFreePort().withLogFile(logsFile).build();
        try {
            FFService.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDriver(new FirefoxDriver(FFService, getFirefoxOptions()));
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().get(BaseTest.properties.getProperty("appURL"));
    }

    public static void closeBrowser() {
        if (getDriver() != null)
            getDriver().close();
    }

    public static void quitBrowser() {
        if (getDriver() != null)
            getDriver().quit();
    }

    public static void launchRemoteWebDriver(String browserName) {

        DesiredCapabilities desiredCapabilities;
        if (browserName.equalsIgnoreCase("GC")) {
            desiredCapabilities = DesiredCapabilities.chrome();
        } else {
            desiredCapabilities = DesiredCapabilities.firefox();
            //desiredCapabilities.setBrowserName("chrome");
        }
        desiredCapabilities.setPlatform(Platform.ANY);
        try {
            setDriver(new RemoteWebDriver(new URL("http://localhost:4444/"), desiredCapabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().get(BaseTest.properties.getProperty("appURL"));
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //options.addArguments("--headless");
        chromeOptions.addArguments("chrome.switches", "--disable-extensions");
        chromeOptions.addArguments("chrome.switches", "test-type");
        chromeOptions.addArguments("chrome.switches", "start-maximized");
        chromeOptions.addArguments("chrome.switches", "no-sandbox");
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        return chromeOptions;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setAcceptUntrustedCertificates(true);
        //fp.setPreference("network.automatic-ntlm-auth.trusted-uris", "Proxy URL");
        firefoxProfile.setPreference("browser.tabs.remote.autostart", false);
        firefoxProfile.setPreference("browser.tabs.remote.autostart.1", false);
        firefoxProfile.setPreference("browser.tabs.remote.autostart.2", false);
        firefoxProfile.setPreference("browser.tabs.remote.force-enable", false);
        firefoxProfile.setPreference("security.insecure_field_warning.contextual.enabled", false);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(firefoxProfile);
        firefoxOptions.setAcceptInsecureCerts(true);
        firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        firefoxOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        firefoxOptions.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1);
        firefoxOptions.setCapability("marionette", true);
        return firefoxOptions;
    }

    public static void createDockerEnvironment(String browser) {
        // logic to create HUB

        if (browser.equalsIgnoreCase("GC")) {
            // logic to create GC Node
        } else {
            // logic to create FF Node
        }
    }

    public static void launchBrowser(String browserName, String executionMode) {
        if (executionMode.equalsIgnoreCase(ExecutionType.LOCAL.toString())) {
            if (browserName.equalsIgnoreCase(BrowserName.GC.toString())) {
                launchChromeBrowser();
            } else if (browserName.equalsIgnoreCase(BrowserName.FF.toString())) {
                launchFirefoxBrowser();
            }
        } else if (executionMode.equalsIgnoreCase(ExecutionType.GRID.toString())) {
            launchRemoteWebDriver(browserName);
        } else if (executionMode.equalsIgnoreCase(ExecutionType.DOCKER.toString())) {
            createDockerEnvironment(browserName);
        }
    }

    enum BrowserName {
        GC,
        FF
    }

    enum ExecutionType {
        LOCAL,
        GRID,
        DOCKER
    }
}