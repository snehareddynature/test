package in.at.util;

public class DataUtil {

    public static boolean isTestCaseExecutable(ExcelApiTest eat, String testCaseName)
    {
        int rows = eat.getRowCount("TestCases");
        for(int rNum = 1; rNum <= rows; rNum++)
        {
            String tcName = eat.getCellData("TestCases","TestCaseName",rNum);
            if(tcName.equalsIgnoreCase(testCaseName))
            {
                String runMode = eat.getCellData("TestCases","RunMode",rNum);
                if(runMode.equalsIgnoreCase("Y"))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
}
