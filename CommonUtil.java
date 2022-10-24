package in.at.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.StringTokenizer;

public class CommonUtil {

    public static String resultFile;
    public static String resultAllFile;
    public static String resultFailureFile;
    public static String indexFile;
    public static String reportsFolder;
    public static String instanceReportsFolder;
    public static String screenshotFolder;
    public static String failuresFile;

    public static String getCurrentDateTime(String pattern) {
        Date currentDate = new Date();
        String dateAndTime = new SimpleDateFormat(pattern).format(currentDate);
        return dateAndTime;
    }

    public static void createFolder(String folderPath) {
        File dirInfo = new File(folderPath);

        // creating the folder which is holding the test reports
        if (!dirInfo.exists()) {
            dirInfo.mkdir();
        }
    }

    //public static String getReportsFolder() {
    public static String createCurrentDayFolder() {
        String reportsFolder = Constants.REPORTS_DIR + getCurrentDateTime("MMMMM_dd_yyyy");
        System.out.println("reportsFolder: " + reportsFolder);
        createFolder(reportsFolder);
        return reportsFolder;
    }

    public static String createCurrentRunFolder() {
        String instanceReportsFolder = createCurrentDayFolder() + "/" + getCurrentDateTime("MMMMM_dd_yyyy_hh-mm");
        System.out.println("instanceReportsFolder: " + instanceReportsFolder);
        createFolder(instanceReportsFolder);
        return instanceReportsFolder;
    }

    public static String getInstanceReportsFolder() {
        String instanceReportsFolder = createCurrentDayFolder() + "/" + getCurrentDateTime("MMMMM_dd_yyyy_hh-mm");
        System.out.println("instanceReportsFolder: " + instanceReportsFolder);
        return instanceReportsFolder;
    }

    public static String getScreenshotFolder() {
        String screenshotFolder = getInstanceReportsFolder() + "/" + "screenshots";
        System.out.println("screenshotFolder: " + screenshotFolder);
        return screenshotFolder;
    }

    public static String createCurrentRunScreenshotFolder() {
        String screenshotFolder = getInstanceReportsFolder() + "/" + "screenshots";
        System.out.println("screenshotFolder: " + screenshotFolder);
        createFolder(screenshotFolder);
        return screenshotFolder;
    }

    public static void createScreenshotFolder() {
        createFolder("./screenshots");
    }

    public static void cleanFolder(String folderPath) {
        try {
            FileUtils.cleanDirectory(new File(folderPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanScreenshotFolder() {
        cleanFolder("./screenshots");
    }

    public static void copyScreenshotFolder() {
        try {
            FileUtils.copyDirectory(new File("./screenshots"), new File(screenshotFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getIPAddress() {
        String _ip = null;

        try {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c route print");
            pro.getOutputStream().close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                StringTokenizer tokens = new StringTokenizer(line, " ");
                if (tokens.countTokens() == 5 && tokens.nextToken().contains("0.0.0.0")) {
                    int count = 1;
                    while (tokens.hasMoreTokens()) {
                        String data = tokens.nextToken();
                        if (count == 3) {
                            _ip = data;
                            break;
                        }
                        count++;
                    }
                }
            }
            // pro.waitFor();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return _ip;
    }

    public static String captureScreenshot() {

        TakesScreenshot takesScreenshot = (TakesScreenshot) BrowserUtil.getDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String timestamp = getCurrentDateTime("MMddHHmmssms");
        String dest = "./screenshots/" + generateRandomDigits(4) + timestamp + ".png";
        File destination = new File(dest);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }

    /******************************************************************************************
     * Name: getDecPass | Description: Decrypts the Encrypted password
     ******************************************************************************************/
	/*public static String getDecPass(String encrypted) {
		String dPassword = null;
		try {
			dPassword = ProtectedConfigFile.decrypt(encrypted);
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return dPassword;
	}*/

    // Generates a random int with n digits
    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    // Creates reporting directory structure and returns the report file path.
    /*public static String prepareReportsDirectory() {

        //String resultFile;
        //String instanceReportsFolder;
        //String screenshotFolder;

        // Creating Reports Folder
        CommonUtil.createFolder(Constants.REPORTS_DIR);

        // Creating Logs Folder
        CommonUtil.createFolder(Constants.LOG_DIR);

        // Creating current day folder under reports folder
        CommonUtil.createCurrentDayFolder();

        // Creating current run folder under current day folder
        instanceReportsFolder = CommonUtil.createCurrentRunFolder();

        // Creating current run screenshot folder under current run folder
        screenshotFolder = CommonUtil.createCurrentRunScreenshotFolder();

        // Creating common screenshot folder under the project
        CommonUtil.createScreenshotFolder();
        CommonUtil.cleanScreenshotFolder();  // -------> Why we need to clean immediately after creating folder
        System.out.println("screenshotFolder: " + screenshotFolder);

        resultFile = instanceReportsFolder + "/"
                + PropUtils.getProperties().getProperty("projectName") + "_"
                + CommonUtil.getCurrentDateTime("MMMMM_dd_yyyy_hh-mm") + "_" + "AutomationResults.html";
        System.out.println("resultFile: " + resultFile);

        return resultFile;
    }*/

    public static String[] getTestcaseDetails(ITestResult result) {
        String[] testcaseDetails = new String[4];

        String testcaseName = result.getMethod().getMethodName();
        String testcaseDescription = result.getMethod().getDescription();
        TestDetails testDetails = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestDetails.class);
        String testcaseCategory = testDetails.categoryName();
        String testcaseAuthorName = testDetails.authorName();

        testcaseDetails[0] = testcaseName;
        testcaseDetails[1] = testcaseDescription;
        testcaseDetails[2] = testcaseCategory;
        testcaseDetails[3] = testcaseAuthorName;

        return testcaseDetails;
    }

    public static void killBrowserDriverProcesses() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe /T");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] prepareReportsDirectory() {

        // Creating Reports Folder
        createFolder(Constants.REPORTS_DIR);

        // Creating Logs Folder
        CommonUtil.createFolder(Constants.LOG_DIR);

        // Creating current day folder under reports folder
        CommonUtil.createCurrentDayFolder();

        // Creating current run folder under current day folder
        instanceReportsFolder = CommonUtil.createCurrentRunFolder();

        // Creating current run screenshot folder under current run folder
        screenshotFolder = CommonUtil.createCurrentRunScreenshotFolder();

        // Creating common screenshot folder under the project
        CommonUtil.createScreenshotFolder();
        CommonUtil.cleanScreenshotFolder();  // -------> Why we need to clean immediately after creating folder
        System.out.println("screenshotFolder: " + screenshotFolder);

        resultAllFile = instanceReportsFolder + "/"
                + PropUtils.getProperties().getProperty("projectName") + "_"
                + CommonUtil.getCurrentDateTime("MMMMM_dd_yyyy_hh-mm") + "_" + "AutomationResults.html";
        resultFailureFile = instanceReportsFolder + "/"
                + PropUtils.getProperties().getProperty("projectName") + "_"
                + CommonUtil.getCurrentDateTime("MMMMM_dd_yyyy_hh-mm") + "_" + "FailureResults.html";
        System.out.println("resultFile: " + resultAllFile);

        String[] testResults = new String[2];
        testResults[0] = resultAllFile;
        testResults[1] = resultFailureFile;

        return testResults;
    }

    public static void createIndexFile(ITestContext context) {

        indexFile = "./index.html";
        failuresFile = "./failures.html";
        System.out.println("indexFile: " + indexFile);
        System.out.println("failuresFile: " + failuresFile);
        // Creating Index file
        File sourceAllFile = new File(resultAllFile);
        File destinationIndexAllFile = new File(indexFile);
        File destinationIndexAllTargetFile = new File("./target/index.html");
        try {
            FileUtils.copyFile(sourceAllFile, destinationIndexAllFile);
            FileUtils.copyFile(sourceAllFile, destinationIndexAllTargetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int failedTestsCount = context.getFailedTests().size();
        File sourceFailureFile = new File(resultFailureFile);
        File destinationIndexFailureFile = new File(failuresFile);
        File destinationIndexFailureTargetFile = new File("./target/failures.html");
        if (failedTestsCount != 0) {
            try {
                FileUtils.copyFile(sourceFailureFile, destinationIndexFailureFile);
                FileUtils.copyFile(sourceFailureFile, destinationIndexFailureTargetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (destinationIndexFailureFile.exists() || destinationIndexFailureTargetFile.exists()) {
                destinationIndexFailureFile.delete();
                destinationIndexFailureTargetFile.delete();
            }
        }
    }
}