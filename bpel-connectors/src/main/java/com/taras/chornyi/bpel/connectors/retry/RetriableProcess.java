package com.taras.chornyi.bpel.connectors.retry;

public interface RetriableProcess<T> {

    T tryExecute() throws Exception;

    ProcessData getProcessData();

    boolean isSkipRetry();

    int getCount();

    long getInterval();

}
