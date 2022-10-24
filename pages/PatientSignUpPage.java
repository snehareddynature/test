package in.at.pages;

import in.at.basepage.BasePage;
import in.at.util.TestDataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientSignUpPage extends BasePage {

    public PatientSignUpPage(WebDriver driver) {
        super(driver);
    }

    //<editor-fold desc="Selectors">
    private By firstNameTextBox = By.id("id_first_name");
    private By lastNameTextBox = By.id("id_last_name");
    private By userNameTextBox = By.id("id_username");
    private By passwordTextBox = By.id("id_password");
    private By addressTextBox = By.id("id_address");
    private By mobileNumberTextBox = By.id("id_mobile");
    private By symptomsTextBox = By.id("id_symptoms");
    private By nameAndDepartmentDropDown = By.id("id_assignedDoctorId");
    private By chooseFileButton = By.id("id_profile_pic");
    private By registerButton = By.cssSelector("button[type='submit']");
    //</editor-fold>

    public LoginPage fillRegisterToHospitalForm(String firstName, String lastName, String username, String password) {
        typeText(firstNameTextBox, firstName).log("Typing first name in first name text box.");
        typeText(lastNameTextBox, lastName).log("Typing last name in last name text box.");
        typeText(userNameTextBox, username).log("Typing username in user name text box.");
        typeText(passwordTextBox, password).log("Typing password in password text box.");
        typeText(addressTextBox, TestDataUtil.address()).log("Typing address in address text box.");
        typeText(mobileNumberTextBox, TestDataUtil.phoneNumber()).log("Typing cell phone number in phone number text box.");
        typeText(symptomsTextBox, TestDataUtil.symptom()).log("Typing symptom in symptom text box.");
        selectValue(nameAndDepartmentDropDown, "Sriram (Cardiologist)").log("Selecting doctor and department from department dorp down.");
        //clickUsingActionsClass(chooseFileButton).log("Clicking on choose file button.");
        String filePath = System.getProperty("user.dir") + "\\pictures\\Patient.jpg";
        typeText(chooseFileButton,filePath);
        //fileUploadUsingWinAppDriver(filePath);
        //fileUploadUsingRobotClass(filePath).log("Uploading patient profile picture.");
        click(registerButton).log("Clicking on register button.");
        return new LoginPage(driver);
    }
}
