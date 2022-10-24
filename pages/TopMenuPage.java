package in.at.pages;

import in.at.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TopMenuPage extends BasePage {

    public TopMenuPage(WebDriver driver) {
        super(driver);
    }

    private By adminLink = By.linkText("Admin");
    private By doctorLink = By.linkText("Doctor");
    private By patientLink = By.linkText("Patient");

    public AdminPage navigateToAdminPage() {
        click(adminLink).log("Clicking on Admin Link.");
        return new AdminPage(driver);
    }

    public void navigateToDoctorPage() {
        click(doctorLink).log("Clicking on Doctor Link.");
    }

    public PatientPage navigateToPatientPage() {
        click(patientLink).log("Clicking on Patient Link.");
        return new PatientPage(driver);
    }
}