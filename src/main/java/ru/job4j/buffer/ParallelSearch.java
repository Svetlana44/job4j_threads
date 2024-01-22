package ru.job4j.buffer;

import ru.job4j.io.queue.SimpleBlockingQueue;

/*нужно разработать механизм остановки потребителя, когда производитель закончил свою работу.
Представим утилиту по поиску текста в файловой системе. Одна нить ищет файлы с подходящим именем.
Вторая нить берет эти файлы и читает. Эта схема хорошо описывается шаблоном Producer-Consumer. Однако есть один момент.
Когда первая нить заканчивает свою работу, потребители переходят в режим wait. */
public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(100);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                            Thread.sleep(5000);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        producer.join();
        consumer.interrupt();
    }
}