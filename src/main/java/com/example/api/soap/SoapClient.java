package com.example.api.soap;

import javax.xml.soap.*;

public class SoapClient {
    public static String callSoapAPI(String url) throws Exception {
        // Create a SOAP connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP request and get response
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

        // Process the SOAP response
        return soapResponse.getSOAPBody().getTextContent();
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        // Create a SOAP request message
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Add SOAP envelope and body
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", "http://example.com");
        SOAPBody soapBody = envelope.getBody();

        // Add SOAP operation and parameters
        SOAPElement operation = soapBody.addChildElement("operation", "example");
        operation.addChildElement("param1").addTextNode("value1");
        operation.addChildElement("param2").addTextNode("value2");

        return soapMessage;
    }
}
