package genricUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

    private static final String EXCEL_PATH = "./src/test/resources/TestData.xlsx";

    // Provides invalid login credentials from the InvalidLogin sheet
    @DataProvider(name = "loginDetails")
    public static Object[][] loginDetails()throws EncryptedDocumentException, IOException {

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
            Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet("InvalidLogin");
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet 'InvalidLogin' was not found in TestData.xlsx");
            }

            DataFormatter formatter = new DataFormatter();
            int rowCount = sheet.getLastRowNum();
            Object[][] data = new Object[rowCount][2];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < 2; j++) {
                    data[i - 1][j] = row == null || row.getCell(j) == null? "": formatter.formatCellValue(row.getCell(j)).trim();
                }
            }

            return data;
        }
    }

    // Provides checkout information from the CheckoutDetails sheet
    @DataProvider(name = "yourInformation")
    public static Object[][] yourInformation()
            throws EncryptedDocumentException, IOException {

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet("CheckoutDetails");
            if (sheet == null) {
            	throw new IllegalArgumentException("Sheet 'CheckoutDetails' was not found in TestData.xlsx");
            }

            DataFormatter formatter = new DataFormatter();
            int rowCount = sheet.getLastRowNum();
            Object[][] data = new Object[rowCount][3];
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < 3; j++) {
                    data[i - 1][j] = row == null || row.getCell(j) == null? "": formatter.formatCellValue(row.getCell(j)).trim();
                }
            }
            return data;
        }
    }

    // Reads a specific checkout data row
    private static Object[][] readInformationRow(int excelRow)
            throws EncryptedDocumentException, IOException {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet("CheckoutDetails");
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet 'CheckoutDetails' was not found in TestData.xlsx");
            }
            Row row = sheet.getRow(excelRow);
            if (row == null) {
                throw new IllegalArgumentException("Excel row " + (excelRow + 1) + " is missing");
            }
            DataFormatter formatter = new DataFormatter();
            Object[][] data = new Object[1][3];
            for (int i = 0; i < 3; i++) {
                data[0][i] = row.getCell(i) == null? "": formatter.formatCellValue(row.getCell(i)).trim();
            }
            return data;
        }
    }

    // Provides checkout data with all fields empty
    @DataProvider(name = "emptyAllFields")
    public static Object[][] emptyAllFields()
            throws EncryptedDocumentException, IOException {
        return readInformationRow(1);
    }
    // Provides checkout data with First Name empty
    @DataProvider(name = "emptyFirstName")
    public static Object[][] emptyFirstName()
            throws EncryptedDocumentException, IOException {
        return readInformationRow(2);
    }

    // Provides checkout data with Last Name empty
    @DataProvider(name = "emptyLastName")
    public static Object[][] emptyLastName()
            throws EncryptedDocumentException, IOException {
        return readInformationRow(3);
    }

    // Provides valid login credentials from the ValidLogin sheet
    @DataProvider(name = "loginDetailsxlx")
    public static Object[][] loginDetailsxlx()
            throws EncryptedDocumentException, IOException {

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet("ValidLogin");
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet 'ValidLogin' was not found in TestData.xlsx");
            }

            DataFormatter formatter = new DataFormatter();
            List<Object[]> data = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String username = row.getCell(0) == null? "": formatter.formatCellValue(row.getCell(0)).trim();
                String password = row.getCell(1) == null? "": formatter.formatCellValue(row.getCell(1)).trim();
                if (!username.isEmpty() && !password.isEmpty()) {
                    data.add(new Object[]{username, password});
                }
            }
            return data.toArray(new Object[0][]);
        }
    }
}