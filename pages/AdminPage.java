package in.at.pages;

import in.at.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {

    private By signUpButton = By.linkText("SignUp");
    private By loginButton = By.linkText("Login");
    private By dashboardLink = By.xpath("//a[@href='/admin-dashboard']/span");
    private By doctorLink = By.xpath("//a[@href='/admin-doctor']/span");
    private By patientLink = By.xpath("//a[@href='/admin-patient']/span");
    private By appointmentLink = By.xpath("//a[@href='/admin-appointment']/span");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminSignUpPage navigateToAdminSignUpPage() {
        click(signUpButton).log("Clicking on Sign Up Button.");
        return new AdminSignUpPage(driver);
    }

    public LoginPage navigateToAdminLoginPage() {
        click(loginButton).log("Clicking on Admin log in button.");
        return new LoginPage(driver);
    }
}
