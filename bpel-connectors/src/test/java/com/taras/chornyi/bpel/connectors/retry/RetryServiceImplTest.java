package com.taras.chornyi.bpel.connectors.retry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class RetryServiceImplTest {

    public static final int DEFAULT_ATTEMPTS_COUNT = 5;
    public static final long DEFAULT_WAIT_INTERVAL = 1;

    private RetryServiceImpl retryService = spy(new RetryServiceImpl(new CustomPublisher()));

    @Mock
    private ProcessData processData;

    @Mock
    private RetriableProcess<Object> retriableProcess;

    @Test
    public void testExecute_success() throws Exception {
        initDefaultBehavior();

        Object expectedResult = new Object();
        when(retriableProcess.tryExecute()).thenReturn(expectedResult);

        Object actualResult = retryService.execute(retriableProcess);

        assertThat(actualResult, is(equalTo(expectedResult)));
        verify(retriableProcess, times(1)).tryExecute();
    }

    @Test
    public void testExecute_2_retries() throws Exception {
        initDefaultBehavior();

        Exception someException = new Exception();
        Object expectedResult = new Object();

        when(retriableProcess.tryExecute())
                .thenThrow(someException)
                .thenThrow(someException)
                .thenReturn(expectedResult);

        Object actualResult = retryService.execute(retriableProcess);

        assertThat(actualResult, is(equalTo(expectedResult)));
        verify(retriableProcess, times(3)).tryExecute();
    }

    @Test
    public void testExecute_failed() throws Exception {
        initDefaultBehavior();

        Exception expectedException = new Exception();
        when(retriableProcess.tryExecute()).thenThrow(expectedException);

        try {
            retryService.execute(retriableProcess);
        } catch (Exception actualException) {
            assertThat(actualException, is(equalTo(expectedException)));
        }

        verify(retriableProcess, times(DEFAULT_ATTEMPTS_COUNT)).tryExecute();
    }

    private void initDefaultBehavior() {
        when(retriableProcess.getProcessData()).thenReturn(processData);
        when(retriableProcess.getCount()).thenReturn(DEFAULT_ATTEMPTS_COUNT);
        when(retriableProcess.getInterval()).thenReturn(DEFAULT_WAIT_INTERVAL);
    }

    private static class CustomPublisher implements ApplicationEventPublisher {

        @Override
        public void publishEvent(ApplicationEvent applicationEvent) {
        }

        @Override
        public void publishEvent(Object o) {
        }
    }
}
