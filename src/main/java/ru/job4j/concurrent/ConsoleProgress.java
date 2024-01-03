package ru.job4j.concurrent;

/* для вывода процесса загрузки в консоль */
public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int count = 0;
        char[] process = new char[]{'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
            /* Вывод через print c возвратом курсора (\r)  */
            System.out.print("\r load: " + process[count % 4]);
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}

/* Чтобы не создавать анонимный класс, в примере выше использовалось лямбда-выражение.
Thread another = new Thread(
        () -> System.out.println(Thread.currentThread().getName())
);
Перепишем этот код через анонимный класс.
Thread another = new Thread(
        new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName())
            }
        }
);
Если писать another.run(); вместо Thread#start, то
метод run не дает указания выполнить свои операторы в отдельной нити,
 как это делаем метод Thread#start.
 Метод run напрямую вызывает операторы в той же нити, в которой запущен этот метод.
  */
