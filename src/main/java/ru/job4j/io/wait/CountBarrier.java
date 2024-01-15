package ru.job4j.io.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/* Класс блокирует выполнение по условию счетчика
Переменная total содержит количество вызовов метода count().
 Метод count изменяет состояние программы.
  Это значит, что внутри метода count нужно вызывать метод notifyAll.
Нити, которые выполняют метод await, могут начать работу если поле count >= total.
Если оно не равно, то нужно перевести нить в состояние wait.
 Использовать цикл while для проверки состояния, а не оператор if.  */
@ThreadSafe
public class CountBarrier {
    @GuardedBy("this")
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (this) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
