package com.taras.chornyi.bpel.connectors.retry;

import com.taras.chornyi.bpel.connectors.domain.BusinessProcess;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Optional;

@Getter
public class BusinessProcessEvent extends ApplicationEvent {

    private String id;
    private Optional<BusinessProcess> process;
    private BpelState state;
    private int retryCount;

    public BusinessProcessEvent(Object source, String id, Optional<BusinessProcess> process, BpelState state, int retryCount) {
        super(source);
        this.id = id;
        this.process = process;
        this.state = state;
        this.retryCount = retryCount;
    }

}
