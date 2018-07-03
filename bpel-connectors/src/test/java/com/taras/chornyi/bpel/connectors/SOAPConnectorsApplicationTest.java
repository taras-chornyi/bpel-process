package com.taras.chornyi.bpel.connectors;

import com.taras.chornyi.bpel.connectors.domain.BusinessProcess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.Assert.*;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import javax.xml.transform.Source;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SOAPConnectorsApplication.class)
public class SOAPConnectorsApplicationTest {

    public static final String ID_PROCESS = "1234567890";
    public static final String URL = "http://localhost:9000/ws";

    @Autowired
    private SOAPConnectorComponent soapConnector;

    private MockWebServiceServer mockServer;

    @Before
    public void createServer() throws Exception {
        mockServer = MockWebServiceServer.createServer(soapConnector);
    }

    @Test
    public void testResponse() {

        Long timestamp = getLongValue(LocalDate.now());

        Source expectedRequestPayload =
                new StringSource("<ns2:businessProcess xmlns:ns2=\"https://github.com/taras-chornyi/bpel-process\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "<ns2:id_process>" + ID_PROCESS + "</ns2:id_process>" +
                        "<ns2:timestamp>" + timestamp + " </ns2:timestamp>" +
                        "<ns2:sourceAddress>" + URL + "</ns2:sourceAddress>" +
                        "</ns2:businessProcess>");

        Source responsePayload = new StringSource("<ns2:businessProcess xmlns:ns2=\"https://github.com/taras-chornyi/bpel-process\">" +
                "<ns2:id_process>"+ ID_PROCESS + "</ns2:id_process>" +
                "<ns2:timestamp>" + timestamp + "</ns2:timestamp>" +
                "<ns2:sourceAddress>" + URL + "</ns2:sourceAddress>" +
                "</ns2:businessProcess>");

        mockServer.expect(payload(expectedRequestPayload)).andRespond(withPayload(responsePayload));

        // soapConnector.getResponse uses the WebServiceTemplate
        BusinessProcess process = new BusinessProcess();
        process.setIdProcess(ID_PROCESS);
        process.setTimestamp(timestamp);
        process.setSourceAddress(URL);
        BusinessProcess response = soapConnector.getProcessResponse(ID_PROCESS, process);
        assertEquals(ID_PROCESS, response.getIdProcess());

        mockServer.verify();
    }

    /**
     * Returns long value in milliseconds from LocalDate
     *
     * @param date
     * @return
     */
    public static Long getLongValue(LocalDate date) {
        return date == null ? null : date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
