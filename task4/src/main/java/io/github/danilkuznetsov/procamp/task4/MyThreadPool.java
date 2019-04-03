package io.github.danilkuznetsov.procamp.task4;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MyThreadPool implements MyExecutorService {

    private final List<Thread> workers;
    private final BlockingWorkQueue<Runnable> commandQueue;
    private final WorkerThreadFactory workerFactory;

    private volatile Thread houseKeeper;

    private volatile boolean isRunning = true;

//    private static final int MAX_RETRY_COUNT = 3;

    private Object lockWorkerOperation = new Object();

    MyThreadPool(WorkerThreadFactory workerThreadFactory, int poolSize) {
        this.workerFactory = workerThreadFactory;
        this.commandQueue = workerThreadFactory.taskQueue();

        // pre-start pool
        this.workers = createAndStartWorkers(poolSize);
    }

    private List<Thread> createAndStartWorkers(int numberWorkers) {
        return IntStream
                .range(0, numberWorkers)
                .mapToObj(i -> {
                    Thread thread = this.workerFactory.createWorker();
                    thread.start();
                    return thread;
                })
                .collect(toList());
    }

    @Override
    public void execute(Runnable command) {
        runHouseKeeper();
        commandQueue.put(command);
    }

    private void runHouseKeeper() {
        // lazy initialization housekeeper
        if (this.houseKeeper == null) {
            synchronized (this) {
                if (this.houseKeeper == null) {
                    this.houseKeeper = new Thread(new HouseKeeper());
                    this.houseKeeper.start();
                }
            }
        }
    }

// implementation of a method that executes the command with attempts to retry putting command to queue
//    public void execute(Runnable command) {
//
//        int retryCount = 0;
//
//        while (retryCount <= MAX_RETRY_COUNT) {
//            try {
//                taskQueue.put(command);
//                return;
//            } catch (WorkQueueIsFullException e) {
//                // retry
//                retryCount++;
//            }
//        }
//
//        System.out.println("Command queue is full. Cannot process " + command.toString());
//    }

    @Override
    public void shutdownNow() {
        this.isRunning = false;
        synchronized (this.lockWorkerOperation) {
            System.out.println(commandQueue.size() + " tasks still wait in the task queue");
            workers.forEach(Thread::interrupt);
        }
    }

    private class HouseKeeper implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                synchronized (lockWorkerOperation) {
                    // find and take dead workers
                    List<Thread> deadWorker = workers
                            .stream()
                            .filter(thread -> thread.getState() == Thread.State.TERMINATED)
                            .collect(toList());

                    if (!deadWorker.isEmpty()) {
                        System.out.println("restart  " + deadWorker.size() + " dead workers");
                        workers.removeAll(deadWorker);
                        // create and start new workers instead of dead
                        List<Thread> newWorkers = createAndStartWorkers(deadWorker.size());
                        workers.addAll(newWorkers);
                    }
                }

                try {
                    // sleep between an iteration of housekeeping
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
