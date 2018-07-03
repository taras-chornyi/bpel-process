package com.taras.chornyi.bpel.connectors.retry;

/**
 * BPEL process state
 *
 * @author Taras Chornyi
 */
public enum BpelState {
    NEW, RUNNING, FAILED, RETRY, OUTDATED, FINISHED
}
