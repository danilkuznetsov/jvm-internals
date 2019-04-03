package io.github.danilkuznetsov.procamp.task4;

public class WorkerThreadFactory {

    private final BlockingWorkQueue<Runnable> commandQueue;

    WorkerThreadFactory(final BlockingWorkQueue<Runnable> taskQueue) {
        this.commandQueue = taskQueue;
    }

    Thread createWorker() {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    this.commandQueue.take().run();
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + " was interrupted");
        });
    }

    BlockingWorkQueue<Runnable> taskQueue() {
        return commandQueue;
    }
}
