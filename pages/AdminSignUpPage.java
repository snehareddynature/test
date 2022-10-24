package in.at.pages;

import in.at.basepage.BasePage;
import in.at.util.CommonUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminSignUpPage extends BasePage {

    CommonUtil commonUtil = new CommonUtil();

    private By firstNameTextBox = By.id("id_first_name");
    private By lastNameTextBox = By.id("id_last_name");
    private By userNameTextBox = By.id("id_username");
    private By passwordTextBox = By.id("id_password");
    private By submitButton = By.cssSelector("button[type='submit']");

    public AdminSignUpPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage fillAddNewAdminForm(String username, String password) {

        String firstName = username.split("\\.")[0];
        String lastName = username.split("\\.")[1];

        typeText(firstNameTextBox, firstName).log("Entering first name in first name text box.");
        typeText(lastNameTextBox, lastName).log("Entering last name in last name text box.");
        typeText(userNameTextBox, username).log("Entering user name in user name text box.");
        typeText(passwordTextBox, password).log("Entering password in password text box.");
        click(submitButton).log("Clicking on Submit Button.");
        return new LoginPage(driver);
    }


}
