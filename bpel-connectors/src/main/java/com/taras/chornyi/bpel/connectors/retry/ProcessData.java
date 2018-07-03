package com.taras.chornyi.bpel.connectors.retry;

import com.taras.chornyi.bpel.connectors.domain.BusinessProcess;
import lombok.Getter;

/**
 * Process data entity
 *
 * @author Taras Chornyi
 */
@Getter
public class ProcessData {

    private final String endpoint;
    private final String processID;
    private final BusinessProcess businessProcess;

    public ProcessData(String endpoint, String processID, BusinessProcess businessProcess) {
        this.endpoint = endpoint;
        this.processID = processID;
        this.businessProcess = businessProcess;
    }

}
