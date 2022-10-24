package in.at.pages;

import in.at.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

public class LoginPage extends BasePage {

    private By userNameTextBox = By.id("id_username");
    private By passwordTextBox = By.id("id_password");
    private By loginButton = By.cssSelector("button[type='submit']");

    private By userText = By.cssSelector(".avatar h6");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Object userLogin(String username, String password) {
        typeText(userNameTextBox, username).log(MessageFormat.format("Entering \"{0}\" in username text box.",username));
        typeText(passwordTextBox, password).log(MessageFormat.format("Entering \"{0}\" in password text box.",password));
        click(loginButton).log("Clicking on login button.");

        String userRole = getElementText(userText);

        if(userRole.equals("Admin"))
            return new AdminDashboardPage(driver);
        else if(userRole.equals("Doctor"))
            return new DoctorDashboardPage(driver);
        else if(userRole.equals("Patient"))
            return new PatientDashboardPage(driver);
        else
            return this;
    }
}
