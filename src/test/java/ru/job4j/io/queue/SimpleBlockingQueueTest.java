package ru.job4j.io.queue;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void offerPoll() throws InterruptedException {
        SimpleBlockingQueue<T> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(new T());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
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
        assertThat(simpleBlockingQueue.queueIsEmpty()).isTrue();
    }

    @Test
    void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Producer");
        producer.start();
        producer.join();
        assertThat(simpleBlockingQueue.getQueue()).isEqualTo(List.of(1));
    }

    @Test
    void pollOne() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
        AtomicInteger countA = new AtomicInteger(1);
        Thread producer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Producer");
        producer.start();
        producer.join();
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
        consumer.join();
        assertThat(simpleBlockingQueue.getT()).isEqualTo(1);
    }

    @Test
    void poll() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
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