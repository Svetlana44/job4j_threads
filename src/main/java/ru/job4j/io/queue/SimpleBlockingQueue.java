package ru.job4j.io.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

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
public class SimpleBlockingQueue<T> implements Iterable<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
        synchronized (this) {
            queue.add(value);
            this.notifyAll();
        }
    }

    /* Метод poll() должен вернуть объект из внутренней коллекции.
     Если в коллекции объектов нет, то нужно перевести текущую нить в состояние ожидания.  */
    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            return queue.poll();
        }
    }


    public synchronized Queue<T> getQueue() {
        return queue;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy((List<T>) queue).iterator();
    }

    private synchronized List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);

    }


}
