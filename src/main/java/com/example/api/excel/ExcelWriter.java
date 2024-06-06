package com.example.api.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    public static void writeToExcel(String filePath, String sheetName, int rowIndex, String result, String reason) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet(sheetName);
            XSSFRow row = sheet.createRow(rowIndex);

            row.createCell(2).setCellValue(result);
            row.createCell(3).setCellValue(reason);

            workbook.write(outputStream);
        }
    }
}

