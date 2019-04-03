package io.github.danilkuznetsov.procamp.task4;

import java.util.Objects;

public class BlockingWorkQueue<T> {

    private volatile Node<T> head;
    private volatile Node<T> tail;

    private volatile int size;

    // max size of BlockingWorkQueue
    private final int workQueueSize;

    private final Object lock = new Object();

    BlockingWorkQueue(int queueSize) {
        this.workQueueSize = queueSize;
    }

    T take() throws InterruptedException {
        synchronized (lock) {

            while (isEmpty()) {
                lock.wait();
            }

            T value = this.head.value;
            this.head = this.head.next;
            size--;
            lock.notifyAll();
            return value;
        }
    }

    void put(T value) {

        if (size == workQueueSize) {
            throw new WorkQueueIsFullException();
        }

        Node<T> newNode = new Node<>(value);

        synchronized (lock) {

            if (isEmpty()) {
                this.head = newNode;
            } else {
                this.tail.next = newNode;
            }

            this.tail = newNode;
            size++;
            lock.notifyAll();
        }
    }

    boolean isEmpty() {
        synchronized (lock) {
            return Objects.isNull(this.head);
        }
    }

    int size() {
        return size;
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }
}
