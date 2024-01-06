package ru.job4j.concurrent.atomic;

/*  Класс с двумя нитями, которые увеличивают счетчик.
что операция инкремента - эта упрощенная запись трех операций:
 1. чтение переменной.
 2. увеличение локальной переменной на единицу.
 3. запись локальной переменной в общий ресурс.
Может возникнуть ситуация, что обе нити выполнят чтение переменной одновременно.
 В результате этого общий ресурс обновится на единицу, а не на два.
*/
public class CountShareMain {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }
}
/* Чтобы добиться атомарности неатомарных операций, в Java используется механизм синхронизации.
используется слово   synchronized
public synchronized void increment() { value++; }
   */