package ru.job4j.concurrent.atomic;

public class Flag {
    private static volatile boolean flag = true;

    /* облегченный механизм синхронизации - volatile.
    Thread используем свою область памяти для хранения ссылок на общие ресурсы.
    Может возникнуть ситуация, что главная нить запишет новое значение переменной в кеш процессора, а дополнительная нить будет продолжать читать переменную flag из регистра.
    , когда общий ресурс не обновляется в зависимости от своего состояния.
    Например, для инкремента его использовать нельзя.
    volаtile - это ключевое слово, которое используется для полей класса. По сути это отдельная синхронизация операций чтения и записи.
    Если поле класса обозначено volatile, то чтение и запись переменной будет происходить только из RAM памяти процессора.
       */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        flag = false;
        thread.join();
    }
}
