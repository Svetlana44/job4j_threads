package ru.job4j.io.wait;

/* Метод notifyAll будит все нити, которые ждали изменения состояния.
Метод notifyAll переводит все нити из состояния wait в runnable.
 Метод wait переводит нить в состояние ожидания, если программа не может дальше выполняться.
 Переключение нити из состояния wait в runnable операция не атомарная.
Это значит, что состояние программы может поменяться, когда нить начнет выполнять полезную работу.
Чтобы избежать проблем с согласованностью данных, метод wait всегда вызывается в цикле while,
   а не в условном блоке if.
 */
public class Barrier {
    private boolean flag = false;

    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}