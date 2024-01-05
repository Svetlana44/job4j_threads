package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*  В данном примере все операции выполнялись последовательно в главной нити Main
Чтобы создать из этого потока параллельный поток, используется метод parallelStream().
Данный метод вернет параллельный поток, если это возможно.
 Если какие-то операции нельзя выполнить параллельно, то вернется обычный последовательный поток Stream.
Данный метод определен в интерфейсе Collection.
 */
/*  поток на примере перемножения чисел  */
public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        /*  parallelStream() создает поток из элементов Integer  */
        Stream<Integer> stream = list.parallelStream();
        /*  проверяем поток на параллельность с помощью метода isParallel()  */
        System.out.println(stream.isParallel());
        Optional<Integer> multiplication = list.stream().reduce((left, right) -> left * right);
        System.out.println(multiplication.get());

/*  Вид потока можно изменить. Для этого существуют 2 метода, также определенных в интерфейсе BaseStream:
- sequential() - если данный поток параллельный, то возвращает новый последовательный поток с теми же элементами.
    Если метод применяется к последовательному потоку, то возвращает тот же поток.
- parallel() - если данный поток последовательный, то возвращает новый параллельный поток с теми же элементами.
 Если метод применяется к параллельному потоку, то возвращает тот же поток.
    */
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println(parallel.isParallel());
        IntStream sequential = parallel.sequential();
        System.out.println(sequential.isParallel());

 /* Например, ArrayList сохраняет порядок следования при добавлении в него элементов, а HashSet нет
 Для последовательных потоков порядок следования элементов не имеет никакой разницы с точки зрения производительности.
 Для параллельных потоков в некоторых случаях будет полезно отказаться методом
        BaseStream.unordered(),
который возвращает новый поток, в котором не поддерживается сохранение очередности

  выполнение потока в многопоточной среде по умолчанию не гарантирует сохранения порядка следования элементов
  Данное поведение - это неустранимый побочный эффект метода peek() в многопоточной среде.
 + др. forEach  */
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        integerList.stream().parallel().peek(System.out::println).toList();
        integerList.stream().parallel().forEach(System.out::println);
        /*  Для сохранения порядка следования элементов можно воспользоваться методов forEachOrdered():  */
        integerList.stream().parallel().forEachOrdered(System.out::println);
    }
}
