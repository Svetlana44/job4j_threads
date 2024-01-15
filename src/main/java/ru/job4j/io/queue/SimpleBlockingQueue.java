package ru.job4j.io.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/*  шаблон Producer Consumer
собственная версия bounded blocking queue. Это блокирующая очередь, ограниченная по размеру.
Producer помещает данные в очередь, а Consumer извлекает данные из очереди.
Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется, до тех пор пока Consumer не извлечет очередные данные,
 т.е. в очереди появится свободное место.
Если очередь пуста поток Consumer блокируется, до тех пор пока Producer не поместит в очередь данные.
Для того чтобы нить перевести в ждущее состояние, необходимо в ее процессе вызвать метод wait() для монитора.
 Разбудить нить, нужно, чтобы другая нить вызвала у монитора метод notify().

 Каждой нити нужно передать объект:
new SimpleBlockingQueue<Integer>()
Этот объект будет общим ресурсом между этими нитями.
Важный момент, когда нить переводить в состояние ожидания, то она отпускает монитор и другая нить тоже может выполнить этот метод. */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    @GuardedBy("this")
    private final int boundOver;

    @GuardedBy("this")
    private T t;

    public synchronized T getT() {
        return t;
    }

    public SimpleBlockingQueue(int boundOver) {
        this.boundOver = boundOver;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= boundOver) {
                this.wait();
            }
            queue.add(value);
            this.notifyAll();
        }
    }

    /* Метод poll() должен вернуть объект из внутренней коллекции.
     Если в коллекции объектов нет, то нужно перевести текущую нить в состояние ожидания.
     необходимо извлечь, потом оповестить нити и потом вернуть результат  */
    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            T t = queue.poll();
            this.notifyAll();
            this.t = t;
            return t;
        }
    }

    public synchronized Queue<T> getQueue() {
        return queue;
    }

    public synchronized boolean queueIsEmpty() {
        return queue.isEmpty();
    }
}
