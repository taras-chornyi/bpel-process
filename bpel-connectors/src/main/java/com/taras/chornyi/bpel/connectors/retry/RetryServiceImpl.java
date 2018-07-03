package com.taras.chornyi.bpel.connectors.retry;

import com.taras.chornyi.bpel.connectors.domain.BusinessProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Objects;
import java.util.Optional;

/**
 * Redelivery service implementation.
 *
 * @author Taras Chornyi
 */
@Slf4j
public class RetryServiceImpl implements RetryService {

    private final ApplicationEventPublisher publisher;

    public RetryServiceImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public <T> T execute(RetriableProcess<T> retriableProcess) throws Exception {
        String processId = retriableProcess.getProcessData().getProcessID();
        BusinessProcess process = retriableProcess.getProcessData().getBusinessProcess();
        publishAnEvent(processId, process, BpelState.NEW, 0);
        if (retriableProcess.isSkipRetry()) {
            log.info("Try to execute at once, because skip retry is enabled.");
            return tryOnce(retriableProcess, BpelState.FINISHED, 0);
        }

        Exception resultException = null;
        int attemptsCount = retriableProcess.getCount();
        long waitInterval = retriableProcess.getInterval();

        int i = 0;
        for (; i < attemptsCount; i++) {
            try {
                log.info("Try to execute retry attempt [" + i + "]");
                return tryOnce(retriableProcess, BpelState.FINISHED, i);
            } catch (Exception e) {
                resultException = e;
                waitForAWhile(waitInterval);
                publishAnEvent(processId, process, BpelState.RETRY, i);
            }
        }

        publishAnEvent(processId, process, BpelState.FAILED, i);

        throw Objects.requireNonNull(resultException);
    }

    private <T> T tryOnce(RetriableProcess<T> retriableProcess, BpelState state, int retryCount) throws Exception {
        T response = retriableProcess.tryExecute();
        publishAnEvent(retriableProcess.getProcessData().getProcessID(), response, state, retryCount);
        return response;
    }

    private void waitForAWhile(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            log.error(ie.getMessage(), ie);
        }
    }

    private void publishAnEvent(final String id, Object process, final BpelState state, final int retryCount) {
        if (process instanceof BusinessProcess) {
            BusinessProcessEvent event = new BusinessProcessEvent(
                    this,
                    id,
                    Optional.of((BusinessProcess) process),
                    state,
                    retryCount);
            publisher.publishEvent(event);
        }
    }
}
