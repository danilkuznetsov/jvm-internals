package io.github.danilkuznetsov.procamp.task4;

public interface MyExecutorService {

    /**
     * Executes the given value in a thread from thread pool
     *
     * @param command the runnable task
     *
     * @throws WorkQueueIsFullException if this task cannot be accepted for execution due
     *                                  to workQueue is full
     */
    void execute(Runnable command);

    /**
     * Attempts to stop all actively executing tasks.
     */
    void shutdownNow();
}
