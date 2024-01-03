package ru.job4j.concurrent;

public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(count++);
            }
        });
        thread.start();
        Thread.sleep(100);
        /*в методе main Главная нить выставляет прерывание.    */
        thread.interrupt();
    }
}
/* Во второй нити идет проверка этого флага.
while (!Thread.currentThread().isInterrupted()) {
Если он выставлен, то мы не заходим больше в тело цикла и выходим из метода run() для therad (там, где start())
* */

