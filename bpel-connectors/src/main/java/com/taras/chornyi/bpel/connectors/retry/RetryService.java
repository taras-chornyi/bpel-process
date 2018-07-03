package com.taras.chornyi.bpel.connectors.retry;

/**
 * Redelivery service interface
 *
 * @author Taras Chornyi
 */
public interface RetryService {

    <T> T execute(RetriableProcess<T> retriableProcess) throws Exception;

}
