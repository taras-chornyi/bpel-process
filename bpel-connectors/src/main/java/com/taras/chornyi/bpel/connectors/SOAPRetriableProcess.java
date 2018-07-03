package com.taras.chornyi.bpel.connectors;

import com.taras.chornyi.bpel.connectors.domain.BusinessProcess;
import com.taras.chornyi.bpel.connectors.retry.ProcessData;
import com.taras.chornyi.bpel.connectors.retry.RetriableProcess;
import org.springframework.ws.client.core.WebServiceTemplate;

public class SOAPRetriableProcess implements RetriableProcess<BusinessProcess> {

    private final ProcessData processData;
    private final WebServiceTemplate webServiceTemplate;
    private boolean skipRetry;
    private int count;
    private long interval;

    public SOAPRetriableProcess(ProcessData processData,
                                WebServiceTemplate webServiceTemplate,
                                boolean skipRetry,
                                int count,
                                long interval) {
        this.processData = processData;
        this.webServiceTemplate = webServiceTemplate;
        this.skipRetry = skipRetry;
        this.count = count;
        this.interval = interval;
    }

    @Override
    public BusinessProcess tryExecute() throws Exception {
        return (BusinessProcess) webServiceTemplate
                .marshalSendAndReceive(processData.getEndpoint(), processData.getBusinessProcess(), null);
    }

    @Override
    public ProcessData getProcessData() {
        return processData;
    }

    @Override
    public boolean isSkipRetry() {
        return skipRetry;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public long getInterval() {
        return interval;
    }

}
