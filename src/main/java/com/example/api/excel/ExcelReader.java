package com.example.api.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static List<APIData> readFromExcel(String filePath, String sheetName) throws IOException {
        List<APIData> apiDataList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String restUrl = row.getCell(0).getStringCellValue();
                String soapUrl = row.getCell(1).getStringCellValue();
                apiDataList.add(new APIData(restUrl, soapUrl));
            }
        }

        return apiDataList;
    }

    public static class APIData {
        String restUrl;
        String soapUrl;

        APIData(String restUrl, String soapUrl) {
            this.restUrl = restUrl;
            this.soapUrl = soapUrl;
        }

        public String getRestUrl() {
            return restUrl;
        }

        public void setRestUrl(String restUrl) {
            this.restUrl = restUrl;
        }

        public String getSoapUrl() {
            return soapUrl;
        }

        public void setSoapUrl(String soapUrl) {
            this.soapUrl = soapUrl;
        }
    }
}

