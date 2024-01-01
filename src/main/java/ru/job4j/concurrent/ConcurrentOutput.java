package ru.job4j.concurrent;

public class ConcurrentOutput {
/*статический метод Thread.currentThread().getName() позволяет получить экземпляр текущей нити выполнения.
 То есть той нити в который выполняется этот оператор.
Обратите внимание, что создание переменной another идет в нити main,
 но вызов выражения описанного в конструкторе идет (вывод на консоль) уже в нити с именем Thread-0. */

    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        System.out.println(Thread.currentThread().getName());

        Thread second = new Thread(
                ()-> System.out.println(Thread.currentThread().getName())
        );
        second.start();
    }
}
/* Метод Thread#start() указывает виртуальной машине,
 что операторы описанные в конструкторе нужно запустить в отдельной нити.
Если убрать этот оператор, то вывода имени второй нити не будет.

класс java.lang.Thread
Конструктор этого класса принимает функциональный интерфейс java.lang.Runnable.
Это интерфейс имеет один метод public void run().

Чтобы не создавать анонимный класс, в примере выше использовалось лямбда-выражение.
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
Выглядит громоздко.
Метод run имеет модификатор public.
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.run();
        System.out.println(Thread.currentThread().getName());
    }
}
В этом случае имя нити и в первом и во втором случае одинаковое.
 Это происходит, потому что метод run не дает указания
  выполнить свои операторы в отдельной нити, как это делаем метод Thread#start.
   Метод run напрямую вызывает операторы в той же нити, в которой запущен этот метод.


*/