package in.at.pages;

import in.at.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientPage extends BasePage {

    public PatientPage(WebDriver driver) {
        super(driver);
    }

    private By registerYourAccountButton = By.linkText("Register Your Account");
    private By loginButton = By.linkText("Login");

    public PatientSignUpPage navigateToPatientSignUpPage() {
        click(registerYourAccountButton).log("Clicking on register your account button.");
        return new PatientSignUpPage(driver);
    }

    public LoginPage navigateToLoginPage() {
        click(loginButton).log("Clicking on patient login button.");
        return new LoginPage(driver);
    }
}