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
                Thread.currentThread().interrupt();
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
        progress.join();
/* Метод join() позволяет вызывающему потоку ждать поток, у которого этот метод вызывается.
Главный поток, который исполняет метод main будет ждать окончания выполнения потока progress.  */
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

 Метод Thread.interrupt(), вызванный, например, в методах sleep(), join(), wait(), не выставляет флаг прерывания,
  если нить находится в режиме ожидания, сна или заблокирована на длительное время другим схожим вызовом.
  В этом случае такие методы выкинут исключение InterruptedException,
   поэтому нужно дополнительно проставить флаг прерывания в блоке catch.
   Исключение InterruptedException нужно для того, чтобы прервать поток,
    который уже выполняет блокирующий вызов(то есть спит, ждет и тд). Исключение является
  единственным способом прервать выполнение метода без возврата какого-либо значения, не нарушая его контракт.

  */
