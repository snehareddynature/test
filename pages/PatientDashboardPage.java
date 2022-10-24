package in.at.pages;

import in.at.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientDashboardPage extends BasePage {

    public PatientDashboardPage(WebDriver driver) {
        super(driver);
    }

    private By userNameText = By.cssSelector(".avatar h2");

    public String getPatientName() {
        return getElementText(userNameText);
    }
}
