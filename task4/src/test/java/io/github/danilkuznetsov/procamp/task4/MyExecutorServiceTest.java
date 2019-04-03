package io.github.danilkuznetsov.procamp.task4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MyExecutorServiceTest {

    private final int DEFAULT_THREAD_POOL_SIZE = 2;

    @Mock
    private WorkerThreadFactory workerThreadFactory;

    @Mock
    private Thread firstMockThread;

    @Mock
    private Thread secondMockThread;

    @Mock
    private BlockingWorkQueue<Runnable> queue;

    private MyExecutorService executorService;

    @Before
    public void setUp() {

        // setup behavior for creating treads
        when(workerThreadFactory.createWorker())
            .thenReturn(firstMockThread)
            .thenReturn(secondMockThread);

        when(this.workerThreadFactory.taskQueue()).thenReturn(this.queue);

        executorService = new MyThreadPool(workerThreadFactory, DEFAULT_THREAD_POOL_SIZE);
    }

    @Test
    public void shouldCreateThreadAsPoolSize() {
        // we setup default executor service with mock workers
        // check if that workers start
        verify(this.firstMockThread).start();
        verify(this.secondMockThread).start();
    }

    @Test
    public void shouldRunRunnable() {
        executorService.execute(mock(Runnable.class));
        verify(this.queue).put(any(Runnable.class));
    }

    @Test(expected = WorkQueueIsFullException.class)
    public void shouldThrowExceptionIfQuueueIfFull() {

        doThrow(new WorkQueueIsFullException())
            .when(this.queue).put(any(Runnable.class));

        executorService.execute(mock(Runnable.class));
    }

    @Test
    public void shouldShutdownAllThreads() {

        executorService.shutdownNow();

        verify(this.firstMockThread).interrupt();
        verify(this.secondMockThread).interrupt();
    }
}
