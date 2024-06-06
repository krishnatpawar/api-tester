package com.example.api;

import com.example.api.comparator.ResponseComparator;
import com.example.api.excel.ExcelReader;
import com.example.api.excel.ExcelWriter;
import com.example.api.rest.RestClient;
import com.example.api.soap.SoapClient;

import java.io.IOException;
import java.util.List;

public class Application {
    private static final String EXCEL_FILE_PATH = "path/to/your/excel/file.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    public static void main(String[] args) {
        try {
            List<ExcelReader.APIData> apiDataList = ExcelReader.readFromExcel(EXCEL_FILE_PATH, SHEET_NAME);

            int rowIndex = 0;
            for (ExcelReader.APIData apiData : apiDataList) {
                String restResponse = RestClient.callRestAPI(apiData.getRestUrl());
                String soapResponse = SoapClient.callSoapAPI(apiData.getSoapUrl());

                boolean isEqual = ResponseComparator.compareResponses(restResponse, soapResponse);
                String result = isEqual ? "Pass" : "Fail";
                String reason = isEqual ? "" : "Responses are different";

                ExcelWriter.writeToExcel(EXCEL_FILE_PATH, SHEET_NAME, rowIndex, result, reason);
                rowIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

