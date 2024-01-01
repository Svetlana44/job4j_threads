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
    }
}
/* Метод Thread#start() указывает виртуальной машине,
 что операторы описанные в конструкторе нужно запустить в отдельной нити.
Если убрать этот оператор, то вывода имени второй нити не будет. */