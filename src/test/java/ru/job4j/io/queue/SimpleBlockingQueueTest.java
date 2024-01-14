package ru.job4j.io.queue;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void offerPoll() throws InterruptedException {
        SimpleBlockingQueue<T> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    simpleBlockingQueue.offer(new T());
                }, "Producer");
        Thread consumer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(simpleBlockingQueue.getQueue()).isEmpty();
    }

    @Test
    void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    simpleBlockingQueue.offer(1);
                }, "Producer");
        producer.start();
        producer.join();
        assertThat(simpleBlockingQueue.getQueue()).isEqualTo(List.of(1));
    }

    @Test
    void poll() throws InterruptedException {
        SimpleBlockingQueue<T> simpleBlockingQueue = new SimpleBlockingQueue<>();
        AtomicInteger countA = new AtomicInteger(1);

        Thread consumer = new Thread(
                () -> {
                    try {
                        countA.getAndIncrement();
                        simpleBlockingQueue.poll();
                        countA.getAndIncrement();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Consumer");
        consumer.start();
        consumer.interrupt();
        assertThat(countA.get()).isEqualTo(1);
    }
}