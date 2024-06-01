package com.swag.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelReader {
	
	/*
	@DataProvider(name="ExcelDataRead")
    public static Object[][] readData() {
        String scenarioId = "Scenario_Id"; // Replace this with the scenario ID you want to use
        return new Object[][] { { getData(scenarioId) } };
    } */

    public static Map<String, String> getData(String scenarioId) {
        String filePath = "./TestData/ScenarioData.xlsx"; // Specify your file path here
        Map<String, String> data = new HashMap<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int scenarioIdColumn = 0;

            // Find the Scenario ID column (assuming it is the first column)
            Row headerRow = sheet.getRow(0);
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().equalsIgnoreCase("Scenario_Id")) {
                    scenarioIdColumn = cell.getColumnIndex();
                    break;
                }
            }

            // Find the row with the matching Scenario ID
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(scenarioIdColumn);

                if (cell.getStringCellValue().equalsIgnoreCase(scenarioId)) {
                    for (Cell headerCell : headerRow) {
                        String key = headerCell.getStringCellValue();
                        String value = row.getCell(headerCell.getColumnIndex()).getStringCellValue();
                        data.put(key, value);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}

/*
Class Definition and DataProvider Method -- Optional
* @DataProvider(name="ExcelDataRead"): This annotation indicates that the readData method is a TestNG DataProvider. It can be used to supply test data to test methods.
* public static Object[][] readData(): Defines a static method that returns a 2D Object array.
* String scenarioId = "Scenario_Id";: Initializes the scenarioId variable with the ID you want to use. Replace "Scenario_Id" with the actual scenario ID you need.
* return new Object[][] { { getData(scenarioId) } };: Returns a 2D array containing the data fetched by the getData method for the specified scenarioId.

getData Method
* public static Map<String, String> getData(String scenarioId): Defines a static method that takes a scenarioId as a parameter and returns a Map of key-value pairs.
* String filePath = "./TestData/ScenarioData.xlsx";: Specifies the path to the Excel file. Modify this to match your actual file path.
* Map<String, String> data = new HashMap<>();: Initializes an empty HashMap to store the data.

Reading the Excel File
* try (FileInputStream file = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(file)): Uses a try-with-resources block to ensure the FileInputStream and Workbook are closed after use.
* FileInputStream file: Reads the Excel file as a stream of bytes.
* Workbook workbook: Loads the Excel file into a workbook object, allowing access to its sheets, rows, and cells

Accessing the Sheet and Finding Scenario ID Column
* Sheet sheet = workbook.getSheetAt(0);: Gets the first sheet of the workbook.
* int rowCount = sheet.getPhysicalNumberOfRows();: Gets the number of rows in the sheet.
* int scenarioIdColumn = 0;: Initializes a variable to hold the index of the "Scenario ID" column.

Finding the Scenario ID Column
* Row headerRow = sheet.getRow(0);: Gets the header row (first row) of the sheet.
* Loop: Iterates through the cells of the header row to find the "Scenario ID" column by comparing cell values case-insensitively. Once found, it sets scenarioIdColumn to the cell's index.

Finding the Matching Row and Extracting Data
* Loop: Iterates through each row (starting from the second row, index 1) to find a row where the cell in the "Scenario ID" column matches the provided scenarioId.

* When a match is found:
* Inner Loop: Iterates through each cell in the header row to get the column headers.
* Data Extraction: For each header cell, it gets the corresponding cell value from the matching row and stores it in the data map with the header cell's value as the key.


Summary
* Purpose: The ExcelReader class reads data from an Excel file based on a given scenario ID and returns the data as a map.
* DataProvider Method: The readData method provides data to test methods using the @DataProvider annotation.
* getData Method: The getData method reads the Excel file, finds the matching scenario ID, and returns the associated data in a map.

*/
