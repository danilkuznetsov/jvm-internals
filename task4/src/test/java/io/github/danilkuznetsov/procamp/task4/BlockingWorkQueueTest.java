package io.github.danilkuznetsov.procamp.task4;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class BlockingWorkQueueTest {

    @Test
    public void shouldPutCommand() {
        BlockingWorkQueue<Runnable> queue = new BlockingWorkQueue<>(1);
        queue.put(() -> {
            // mock commands
        });

        assertFalse(queue.isEmpty());
    }

    @Test(expected = WorkQueueIsFullException.class)
    public void shouldThrowExceptionWhenQueueIsFull() {
        BlockingWorkQueue<Runnable> queue = new BlockingWorkQueue<>(1);
        queue.put(() -> {
            // mock commands
        });

        queue.put(() -> {
            // mock commands
        });
    }

    @Test
    public void shouldTakeCommandWithoutWaiting() throws InterruptedException {
        BlockingWorkQueue<Runnable> queue = new BlockingWorkQueue<>(1);
        queue.put(() -> {
        });

        final Runnable take = queue.take();

        assertThat(take, CoreMatchers.notNullValue());
    }

    @Test
    public void shouldThreadWaitIfQueueEmpty() throws InterruptedException {

        BlockingWorkQueue<Runnable> queue = new BlockingWorkQueue<>(1);

        Thread testThread = new Thread(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                // do nothing
            }
        });

        testThread.start();

        // sleep current thread for waiting when test thread try to get command
        Thread.sleep(300);
        assertThat(testThread.getState(), CoreMatchers.is(Thread.State.WAITING));
    }

    @Test
    public void shouldThreadWaitIfQueueEmptyAndResumeAfterPutCommand() throws InterruptedException {

        BlockingWorkQueue<Runnable> queue = new BlockingWorkQueue<>(1);

        Thread testThread = new Thread(() -> {
            try {
                final Runnable command = queue.take();
                command.run();
            } catch (InterruptedException e) {
                // do nothing
            }
        });

        testThread.start();

        // sleep current thread for waiting when test thread try to get command
        Thread.sleep(300);
        assertThat(testThread.getState(), CoreMatchers.is(Thread.State.WAITING));

        Queue<Integer> resultQueue = new ConcurrentLinkedQueue<>();

        // put command in queue
        queue.put(() -> resultQueue.add(100));

        testThread.join();

        assertThat(resultQueue.size(), CoreMatchers.is(1));
        assertThat(resultQueue.size(), CoreMatchers.is(1));

        assertThat(resultQueue.element(), CoreMatchers.is(100));
    }
}
