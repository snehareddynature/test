package in.at.pages;

import in.at.basepage.BasePage;
import in.at.util.TestDataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardPage extends BasePage {

    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }

    private final By doctorLink = By.cssSelector("[href='/admin-doctor']>span");
    private final By patientLink = By.cssSelector("[href='/admin-patient']>span");
    private final By approvePatientLink = By.cssSelector("[href='/admin-approve-patient']>h6");
    private final By registerDoctorLink = By.cssSelector("[href='/admin-add-doctor']>h6");
    private final By firstNameTextBox = By.id("id_first_name");
    private final By lastNameTextBox = By.id("id_last_name");
    private final By usernameTextBox = By.id("id_username");
    private final By passwordTextBox = By.id("id_password");
    private final By departmentDropDown = By.id("id_department");
    private final By mobileNumberTextBox = By.id("id_mobile");
    private final By addressTextBox = By.id("id_address");
    private final By chooseFileButton = By.id("id_profile_pic");
    private final By registerButton = By.cssSelector("[type='submit']");
    private final By doctorsNameList = By.cssSelector("[id='dev-table']>tbody>tr>td:nth-child(1)");
    private final By patientNameList = By.cssSelector("[id='dev-table']>tbody>tr>td:nth-child(1)");
    private final By logoutLink = By.linkText("Logout");

    public AdminDashboardPage navigateToAdminDoctorArea() {
        click(doctorLink).log("Clicking on Doctor Link.");
        return this;
    }

    public AdminDashboardPage navigateToAdminAddDoctorArea() {
        click(registerDoctorLink).log("Clicking on Register Doctor Link.");
        return this;
    }

    public AdminDashboardPage clickOnPatientLink() {
        click(patientLink).log("Clicking on Patient Link.");
        return this;
    }

    public AdminDashboardPage clickOnApprovePatientLink() {
        click(approvePatientLink).log("Clicking on Approve Patient Link.");
        return this;
    }

    public AdminDashboardPage fillAddNewDoctorToHospitalForm(String firstName, String lastName) {
        typeText(firstNameTextBox, firstName).log(MessageFormat.format("Typing \"{0}\" in first name text box",firstName));
        typeText(lastNameTextBox, lastName).log(MessageFormat.format("Typing \"{0}\" in last name text box",lastName));
        typeText(usernameTextBox, firstName+"."+lastName).log("Typing username in user name text box.");
        typeText(passwordTextBox, TestDataUtil.password()).log("Typing password in password text box.");
        selectValue(departmentDropDown, "Dermatologists").log("Selecting department from department drop down.");
        typeText(mobileNumberTextBox, TestDataUtil.phoneNumber()).log("Typing mobile number in phone number text box.");
        typeText(addressTextBox, TestDataUtil.address()).log("Typing address in address text box.");
        //clickUsingActionsClass(chooseFileButton).log("Clicking on choose file button.");
        String filePath = System.getProperty("user.dir") + "\\pictures\\Doctor.png";
        //typeTextUsingActionsClass(chooseFileButton,filePath);
        typeText(chooseFileButton,filePath);
        //fileUploadUsingWinAppDriver(filePath);
        //fileUploadUsingRobotClass(filePath).log("Uploading doctor profile picture.");
        //typeTextUsingActionsClass(chooseFileButton, filePath);
        click(registerButton).log("Clicking on register button.");
        return this;
    }

    public List<String> getDoctorsList() {
        List<WebElement> doctorNameElements = driver.findElements(doctorsNameList);
        List<String> doctorNames = new ArrayList<>();

        for (WebElement doctorNameElement : doctorNameElements) {
            doctorNames.add(doctorNameElement.getText());
        }
        return doctorNames;
    }

    public AdminDashboardPage approvePatient(String patientName) {
        List<WebElement> patientNameElements = driver.findElements(patientNameList);
        int row = 0;
        for (WebElement patientNameElement : patientNameElements) {
            row++;
            if (patientNameElement.getText().equals(patientName)) {
                break;
            }
        }
        By approveLink = By.cssSelector("[id='dev-table']>tbody>tr:nth-child(" + row + ")>td:nth-child(6)>a");
        click(approveLink).log("Clicking on approve patient link.");
        return this;
    }

    public TopMenuPage logout() {
        click(logoutLink).log("Clicking on logout link.");
        return new TopMenuPage(driver);
    }
}