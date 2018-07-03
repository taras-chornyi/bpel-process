package com.taras.chornyi.bpel.connectors;

import com.taras.chornyi.bpel.connectors.config.ConnectorsProperties;
import com.taras.chornyi.bpel.connectors.domain.BusinessProcess;
import com.taras.chornyi.bpel.connectors.retry.ProcessData;
import com.taras.chornyi.bpel.connectors.retry.RetryService;
import com.taras.chornyi.bpel.connectors.retry.RetryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SOAPConnectorComponent extends WebServiceGatewaySupport {

    private ProcessData processData;

    @Autowired
    private ConnectorsProperties properties;

    @Autowired
    private ApplicationEventPublisher publisher;

    public BusinessProcess getProcessResponse(String id, BusinessProcess process) {

        ProcessData processData = new ProcessData(
                properties.getWebservice().getBpelUrl(),
                id,
                process);

        logger.info("Requesting response for " + process);

        BusinessProcess response;
        try {
            RetryService retryService = new RetryServiceImpl(publisher);
            response = retryService.execute(new SOAPRetriableProcess(
                    processData,
                    getWebServiceTemplate(),
                    properties.getRetry().isSkipRetry(),
                    properties.getRetry().getAttempts().getCount(),
                    properties.getRetry().getAttempts().getInterval()));
        } catch (Exception e) {
            logger.error("Error! org.springframework.ws.client.WebServiceIOException: I/O error: Connection refused (Connection refused)");
            throw new WebServiceIOException(e.getMessage());
        }

        return response;
    }

}
