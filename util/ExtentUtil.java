package in.at.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ExtentUtil {

    protected static ThreadLocal<ExtentTest> EXTENT = new ThreadLocal<>();

    public static ExtentSparkReporter extentSparkReporterAll;
    public static ExtentSparkReporter extentSparkReporterFail;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;

    public static String[] resultFiles;

    public static void setExtentTest(ExtentTest extentTest) {
        EXTENT.set(extentTest);
    }

    public static ExtentTest getExtentTest() {
        return EXTENT.get();
    }

    public static void createExtentReport(Properties properties) {

        resultFiles = CommonUtil.prepareReportsDirectory();

        // Configuring Extent Reports
        if (extentReports == null) {
            extentSparkReporterAll = new ExtentSparkReporter(resultFiles[0]);
            extentSparkReporterAll.config().setTheme(Theme.DARK);
            extentSparkReporterAll.config()
                    .setDocumentTitle(properties.getProperty("projectName") + " Automation Testing Report");
            extentSparkReporterFail = new ExtentSparkReporter(resultFiles[1]).filter().statusFilter().as(new Status[] {Status.FAIL}).apply();
            extentSparkReporterFail.config().setTheme(Theme.DARK);
            extentSparkReporterFail.config()
                    .setDocumentTitle(properties.getProperty("projectName") + " Automation Testing Failure Report");
            try {
                if (properties.getProperty("testType").equalsIgnoreCase("smoke")) {
                    extentSparkReporterAll.config().setReportName("Smoke Testing");
                    extentSparkReporterFail.config().setReportName("Smoke Testing");
                } else if (properties.getProperty("testType").equalsIgnoreCase("regression")) {
                    extentSparkReporterAll.config().setReportName("Regression Testing");
                    extentSparkReporterFail.config().setReportName("Regression Testing");
                }
            } catch (NullPointerException e) {
                extentSparkReporterAll.config().setReportName("Automation Testing");
                extentSparkReporterFail.config().setReportName("Automation Testing Failures");
            }
            //htmlReporter.config().setResourceCDN(ResourceCDN.GITHUB);
            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporterAll,extentSparkReporterFail);
            /*extentReports.setSystemInfo("Project Name", PropUtils.getProperties().getProperty("projectName").toUpperCase());
            extentReports.setSystemInfo("Environment", PropUtils.getProperties().getProperty("environment").toUpperCase());*/
            extentReports.setSystemInfo("Project Name", properties.getProperty("projectName").toUpperCase());
            extentReports.setSystemInfo("Environment", properties.getProperty("environment").toUpperCase());
            extentReports.setSystemInfo("User Name", System.getProperty("user.name").trim().toUpperCase());
            extentReports.setSystemInfo("Automation Tool", "SELENIUM-JAVA");
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version").trim().toUpperCase());
            extentReports.setSystemInfo("Operating System", System.getProperty("os.name").trim().toUpperCase());
            try {
                extentReports.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName().trim().toUpperCase());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            extentReports.setSystemInfo("IP Address", CommonUtil.getIPAddress().trim().toUpperCase());
        }
    }

    public static ExtentTest createTest(String testName, String description, String categoryName, String authorName) {
        extentTest = extentReports.createTest(testName, description).assignCategory(categoryName).assignAuthor(authorName);
        setExtentTest(extentTest);
        return extentTest;
    }

    public static void flushReport() {
        if (extentReports != null)
            extentReports.flush();
    }

    public static void logTest(String logInfo) {
        ExtentUtil.getExtentTest().info(logInfo);
    }
}