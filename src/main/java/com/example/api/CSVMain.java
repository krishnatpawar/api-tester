package com.example.api;

import com.example.api.comparator.ResponseComparator;
import com.example.api.rest.RestClient;
import com.example.api.soap.SoapClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVMain {
    private static final String INPUT_CSV_FILE = "path/to/input.csv";
    private static final String OUTPUT_CSV_FILE = "path/to/output.csv";

    public static void main(String[] args) throws Exception {
        List<APIData> apiDataList = readFromCSV(INPUT_CSV_FILE);
        List<FailureReport> failureReports = new ArrayList<>();

        for (APIData apiData : apiDataList) {
            String restResponse = RestClient.callRestAPI(apiData.restUrl);
            String soapResponse = SoapClient.callSoapAPI(apiData.soapUrl);

            boolean isEqual = ResponseComparator.compareResponses(restResponse, soapResponse);
            if (!isEqual) {
                String reason = "Responses are different";
                failureReports.add(new FailureReport(apiData.restUrl, apiData.soapUrl, reason));
            }
        }

        writeFailureReportsToCSV(OUTPUT_CSV_FILE, failureReports);
    }

    private static List<APIData> readFromCSV(String filePath) {
        List<APIData> apiDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String restUrl = values[0];
                String soapUrl = values[1];
                apiDataList.add(new APIData(restUrl, soapUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiDataList;
    }

    private static void writeFailureReportsToCSV(String filePath, List<FailureReport> failureReports) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("REST URL,SOAP URL,Reason\n");
            for (FailureReport report : failureReports) {
                writer.write(report.restUrl + "," + report.soapUrl + "," + report.reason + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class APIData {
        String restUrl;
        String soapUrl;

        APIData(String restUrl, String soapUrl) {
            this.restUrl = restUrl;
            this.soapUrl = soapUrl;
        }
    }

    static class FailureReport {
        String restUrl;
        String soapUrl;
        String reason;

        FailureReport(String restUrl, String soapUrl, String reason) {
            this.restUrl = restUrl;
            this.soapUrl = soapUrl;
            this.reason = reason;
        }
    }
}

