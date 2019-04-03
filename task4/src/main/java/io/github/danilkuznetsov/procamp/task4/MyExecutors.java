package io.github.danilkuznetsov.procamp.task4;

public class MyExecutors {

    /**
     * Creates a new ThreadPool with the given initial number of threads and work queue size
     * @param poolSize the number of threads to keep in the pool, even
     * if they are idle
     * @param workQueueSize the queue to use for holding tasks before they are
     * executed.  This queue will hold only the {@code Runnable}
     * tasks submitted by the {@code execute} method.
     */
    public static MyExecutorService newFixedThreadPool(int poolSize, int workQueueSize) {

        BlockingWorkQueue<Runnable> queue = new BlockingWorkQueue<>(workQueueSize);
        final WorkerThreadFactory threadFactory = new WorkerThreadFactory(queue);

        return new MyThreadPool(threadFactory, poolSize);
    }

    public static MyExecutorService newFixedThreadPool(int poolSize) {
        return newFixedThreadPool(poolSize, 10);
    }
}

