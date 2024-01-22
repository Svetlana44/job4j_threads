package ru.job4j.io.queue;

import org.apache.poi.ss.formula.functions.T;

public class QueueApp {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue simpleBlockingQueue = new SimpleBlockingQueue(1);
        Thread first = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(new T());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "First");
        Thread firstToo = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(new T());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "FirstToo");


        Thread second = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Second");
        first.start();
        firstToo.start();
        second.start();
        first.join();
        firstToo.join();
        second.join();
    }
}
