package io.github.danilkuznetsov.procamp.task4;

import java.util.concurrent.atomic.AtomicInteger;

public class MyExecutorDemo {

    public static void main(String[] args) throws InterruptedException {

        final MyExecutorService executorService = MyExecutors.newFixedThreadPool(10, 200);
        runIteration(executorService, 1000);
    }

    private static void runIteration(final MyExecutorService executorService, int countSubmittedTasks) throws InterruptedException {

        AtomicInteger executedTasks = new AtomicInteger();
        AtomicInteger deadTasks = new AtomicInteger();
        AtomicInteger declinedTasks = new AtomicInteger();

        for (int taskIndex = 0; taskIndex < countSubmittedTasks; taskIndex++) {
            try {
                if (taskIndex % 10 == 0) {
                    executorService.execute(() -> {
                        deadTasks.incrementAndGet();
                        throw new RuntimeException();
                    });
                }
                else {
                    executorService.execute(executedTasks::incrementAndGet);
                }

            } catch (WorkQueueIsFullException e) {
                declinedTasks.incrementAndGet();
            }
        }

        // wait until all tasks are finished

        Thread.sleep(10000);

        executorService.shutdownNow();

        System.out.println("Submitted tasks: " +  countSubmittedTasks);
        System.out.println("Executed tasks: " + executedTasks);
        System.out.println("Dead tasks: " + deadTasks);
        System.out.println("Declined tasks: " + declinedTasks);
    }
}
