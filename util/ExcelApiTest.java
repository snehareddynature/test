package in.at.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelApiTest {

    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    public String xlFilePath;

    public ExcelApiTest(String xlFilePath) {
        this.xlFilePath = xlFilePath;
        try {
            fis = new FileInputStream(xlFilePath);
            workbook = new XSSFWorkbook(fis);
            fis.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            } else if (cell.getCellType() == CellType.BLANK) {
                return "";
            } else {
                return String.valueOf(cell.getBooleanCellValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return "row " + rowNum + "or Column " + colNum + " does NOT EXIST in the Excel file.";
        }
    }

    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            int colNum = -1;
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim())) {
                    colNum = i;
                }
            }
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            } else if (cell.getCellType() == CellType.BLANK) {
                return "";
            } else {
                return String.valueOf(cell.getBooleanCellValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "row " + rowNum + " or Column " + colName + " does NOT EXIST in the Excel file.";
        }
    }

    public int getRowCount(String sheetName)
    {
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() + 1;
        return rowCount;
    }

    public int getColumnCount(String sheetName)
    {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);
        int colCount = row.getLastCellNum();
        return  colCount;
    }

    public boolean setCellData(String sheetName, int colNum, int rowNum, String value)
    {
        try{
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            if(row == null)
            {
                row = sheet.createRow(rowNum);
            }
            cell = row.getCell(colNum);
            if(cell == null)
            {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(value);

            fos = new FileOutputStream(xlFilePath);
            workbook.write(fos);
            fos.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean setCellData(String sheetName, String colName, int rowNum, String value)
    {
        try
        {
            int colNum = -1;
            sheet = workbook.getSheet(sheetName);

            row = sheet.getRow(0);
            for(int i = 0; i < row.getLastCellNum(); i++)
            {
                if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim()))
                {
                    colNum = i;
                }
            }
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
            {
                row = sheet.createRow(rowNum - 1);
            }

            cell = row.getCell(colNum);
            if(cell == null)
            {
                cell = row.createCell(colNum);
            }

            cell.setCellValue(value);

            fos = new FileOutputStream(xlFilePath);
            workbook.write(fos);
            fos.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}