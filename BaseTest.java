package in.at.basetest;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import in.at.util.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    static public ExcelApiTest excelUtil;
    static public Properties properties;

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        CommonUtil.killBrowserDriverProcesses();
        properties = PropUtils.getProperties();
        ExtentUtil.createExtentReport(properties);
        //excelUtil = new ExcelApiTest("./src/test/resources/testdata/TestData.xlsx");
        //excelUtil = new ExcelUtil(Constants.TEST_DATA_DIR + Constants.TEST_DATA_FILE_NAME);
    }

    @BeforeMethod(alwaysRun = true)
    public void init(ITestResult result) {
        excelUtil = new ExcelApiTest("./src/test/resources/testdata/TestData.xlsx");
        TestCaseDetails testcase = TestCaseDetails.getTestCaseDetails(result);
        ExtentUtil.createTest(testcase.name, testcase.description, testcase.category, testcase.author);

        //if (!DataUtil.isTestCaseExecutable(excelUtil, testcase.name)) {
        //    throw new SkipException("Skipping the Testcase as the RUN MODE is N.");
        //System.out.println("Do not execute this TEST.");
        //} else {
        String browserName = properties.getProperty("browser");
        String executionType = properties.getProperty("executionMode");
        BrowserUtil.launchBrowser(browserName, executionType);
        //}
    }

    @AfterMethod(alwaysRun = true)
    public void getResults(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenShotPath = CommonUtil.captureScreenshot();
            ExtentUtil.getExtentTest().fail(MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
                    ExtentColor.RED));
            ExtentUtil.getExtentTest().fail(result.getThrowable());
            ExtentUtil.getExtentTest().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentUtil.getExtentTest().pass(MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentUtil.getExtentTest().skip(MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
            ExtentUtil.getExtentTest().skip(result.getThrowable());
        }
        BrowserUtil.closeBrowser();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown(ITestContext context) {
        // Flush the Reports
        ExtentUtil.flushReport();

        // Creating the Index reports file
        CommonUtil.createIndexFile(context);

        // Copy Screenshots folder
        CommonUtil.copyScreenshotFolder();

        //Closing the Database Connection if any
        DatabaseUtils.closeDBConnection();
    }


    public Map<String,String> readDataFromExcel(String sheetName, int row, String filePath)
    {
        Map<String,String> dataColumns = new HashMap<>();
        int colCount = excelUtil.getColumnCount(sheetName);
        String colName;
        String cellData;
        for(int col = 0; col < colCount; col++)
        {
            colName = excelUtil.getCellData(sheetName,col,0);
            cellData = excelUtil.getCellData(sheetName,col, row);
            if(cellData !=null || colName !=null) {
                dataColumns.put(colName,cellData);
            }
            else {
                dataColumns.put(colName,"");
            }
        }
        return dataColumns;
    }
}