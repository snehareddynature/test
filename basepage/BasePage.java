package in.at.basepage;

import com.github.javafaker.Faker;
import in.at.util.ExtentUtil;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class BasePage {

    public WebDriver driver;
    public WebDriverWait webDriverWait;
    public Faker faker;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 60);
        faker = new Faker(new Locale("en-IND"));
    }

    public WebElement Find(By element) {
        return driver.findElement(element);
    }

    public List<WebElement> FindMultiple(By element) {
        return driver.findElements(element);
    }

    public void highlightElement(By element) {
        for (int i = 0; i < 3; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style',arguments[1]);", driver.findElement(element), "color: red; border: 3px solid red;");
            js.executeScript("arguments[0].setAttribute('style',arguments[1]);", driver.findElement(element), "");
        }
    }

    public BasePage typeText(By selector, String value) {
        WebElement element = Find(selector);
        scrollToElement(selector);
        waitUntilElementVisible(selector);
        //highlightElement(element);
        element.sendKeys(value);
        return this;
    }

    public void typeTextUsingJS(By element, String value) {
        WebElement inputField = driver.findElement(element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", inputField);
    }

    public BasePage clickUsingActionsClass(By element) {
        new Actions(driver).click(driver.findElement(element)).perform();
        return this;
    }

    public BasePage click(By selector) {
        WebElement element = Find(selector);
        scrollToElement(selector);
        waitUtilElementClickable(selector);
        //highlightElement(element);
        element.click();
        return this;
    }

    public BasePage selectValue(By element, String text) {
        waitUntilElementVisible(element);
        //highlightElement(element);
        Select select = new Select(driver.findElement(element));
        select.selectByVisibleText(text);
        return this;
    }

    public void scrollToElement(By selector) {
        WebElement element = Find(selector);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public BasePage waitUntilElementVisible(By element) {
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(element)));
        return this;
    }

    public BasePage waitUtilElementClickable(By element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public String getElementText(By element) {
        //highlightElement(element);
        String text = driver.findElement(element).getText();
        ExtentUtil.logTest("Capturing element text: " + text);
        return text;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getAttributeValue(By element, String attribute) {
        //highlightElement(element);
        return driver.findElement(element).getAttribute(attribute);
    }

    public BasePage fileUploadUsingWinAppDriver(String filePath) {
        // Start the WinAppDriver Programmatically
        String command = "C:/Program Files (x86)/Windows Application Driver/WinAppDriver.exe";
        ProcessBuilder builder = new ProcessBuilder(command).inheritIO();
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // WinAppDriver code starts here
        DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
        desktopCapabilities.setCapability("app", "Root");
        WindowsDriver session = null;
        try {
            session = new WindowsDriver(new URL("http://127.0.0.1:4723"), desktopCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // uploading a file which is stored at filepath
        WebElement windowsPopupFileNameTextBox = session.findElementByXPath("//Edit[@Name='File name:']");
        windowsPopupFileNameTextBox.sendKeys(filePath);

        WebElement windowsPopupOpenButton = session.findElementByXPath("//Button[@Name='Open'][@AutomationId='1']");
        windowsPopupOpenButton.click();

        process.destroy();

        return this;
    }

    public BasePage fileUploadUsingRobotClass(String filePath) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        robot.setAutoDelay(1000);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        robot.setAutoDelay(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        return this;
    }

    public BasePage typeTextUsingActionsClass(By element, String value) {
        new Actions(driver).sendKeys(driver.findElement(element), value).perform();
        return this;
    }

    public BasePage log(String message) {
        ExtentUtil.logTest(message);
        return this;
    }
}
