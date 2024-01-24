package ru.job4j.io.queue;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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
        AtomicInteger countA = new AtomicInteger(0);

        Thread producer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(33);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Producer");
        Thread consumer = new Thread(
                () -> {
                    try {
                        int integer = simpleBlockingQueue.poll();
                        countA.addAndGet(integer);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Consumer");
        producer.start();
        producer.join();

        consumer.start();
        consumer.join();
        assertThat(countA.get()).isEqualTo(33);

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(22);

        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 3).forEach(v -> {
                        try {
                            queue.offer(v);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }, "Producer");
        Thread concumer = new Thread(
                () -> {
                    while (!queue.queueIsEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Consumer");

        producer.start();
        concumer.start();
        /* Сначала дожидаемся завершения работы производителя.*/
        producer.join();
        /*  Далее посылаем сигнал, что потребителю можно остановиться.*/
        concumer.interrupt();
        /*  Ждем пока потребитель прочитает все данные и завершит свою работу.   */
        concumer.join();

        assertThat(buffer).containsExactly(0, 1, 2);
    }

    @Test
    public void whenFetchAllThenGetItSmallBuff() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);

        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 7).forEach(v -> {
                        try {
                            queue.offer(v);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }, "Producer");
        Thread concumer = new Thread(
                () -> {
                    while (!queue.queueIsEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Consumer");

        producer.start();
        concumer.start();
        /* Сначала дожидаемся завершения работы производителя.*/
        producer.join();
        /*  Далее посылаем сигнал, что потребителю можно остановиться.*/
        concumer.interrupt();
        /*  Ждем пока потребитель прочитает все данные и завершит свою работу.   */
        concumer.join();

        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6);
    }
}