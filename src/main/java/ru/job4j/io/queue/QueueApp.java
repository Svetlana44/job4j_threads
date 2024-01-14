package ru.job4j.io.queue;

import org.apache.poi.ss.formula.functions.T;

public class QueueApp {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue simpleBlockingQueue = new SimpleBlockingQueue();
        Thread first = new Thread(
                () -> {
                    simpleBlockingQueue.offer(new T());
                }, "First");
        Thread second = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Second");
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
