package ru.job4j.concurrent;

/* Выполнение нити можно приостановить в самой программе
 Thread.sleep(millisecond);
 Этот метод переводит нить в состояние TIMED_WAITING
 этот метод может выкинуть исключение -InterruptedException  */
public class ThreadSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("Start loading ... ");
                Thread.sleep(3000);
                System.out.println("Loaded.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("Main");
    }
}
