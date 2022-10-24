package in.at.util;

import in.at.util.TestDetails;
import org.testng.ITestResult;

// POJO class implementation for getting TestCase details
public final class TestCaseDetails
{
    public String name;
    public String description;
    public String category;
    public String author;

    public TestCaseDetails(String name, String description, String category, String author){
        this.name = name;
        this.description = description;
        this.category = category;
        this.author = author;
    }

    public static TestCaseDetails getTestCaseDetails(ITestResult result) {

        String name = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        TestDetails testDetails = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestDetails.class);
        String category = testDetails.categoryName();
        String author = testDetails.authorName();

        return new TestCaseDetails(name, description, category, author);
    }
}