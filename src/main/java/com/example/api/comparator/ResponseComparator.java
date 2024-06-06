package com.example.api.comparator;

import com.example.api.model.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class ResponseComparator {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean compareResponses(String restResponse, String soapResponse) throws Exception {
        JsonNode restNode = objectMapper.readTree(restResponse);
        Employee soapEmployee = parseXmlResponse(soapResponse);

        return compareEmployeeData(restNode, soapEmployee);
    }

    private static Employee parseXmlResponse(String soapResponse) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(soapResponse);
        return (Employee) unmarshaller.unmarshal(reader);
    }

    private static boolean compareEmployeeData(JsonNode restNode, Employee soapEmployee) {
        // Compare employee ID
        if (!restNode.get("employee_id").asText().equals(soapEmployee.getId())) {
            return false;
        }

        // Compare name
        if (!restNode.get("name").asText().equals(soapEmployee.getName())) {
            return false;
        }

        // Compare salary
        return restNode.get("salary").asInt() == soapEmployee.getSalary();
    }

    // Employee, Contact, and Manager classes omitted for brevity
}

