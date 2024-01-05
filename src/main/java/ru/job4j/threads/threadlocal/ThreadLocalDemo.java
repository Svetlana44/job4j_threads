package ru.job4j.threads.threadlocal;

/* ThreadLocal<T> является обобщенным классом. T - тип данных, который использует этот класс. ThreadLocal обычно объявляют как private static.
private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
Записать значение в переменную можно с помощью метода set(T value),
 а получить значение можно с помощью метода get().
Работа с этими методами изолирована от других нитей.
ThreadLocal применяется в следующих случаях:
- Когда нужно хранить информацию (например, о сессии пользователя), которую нужно передавать по цепочке методов.
- Когда нужно реализовать какой-либо счетчик для нитей. - Кэширование непотокобезопасных ресурсов.
  В многопоточном приложении держать кэш потокобезопасным будет очень дорогим из-за синхронизации.

ThreadLocal изолирует только ссылки на объекты.
 Если в разных нитях записать в эту переменную один и тот же объект,
 то при работе с этим объектом проявятся все проблемы многопоточности   */
public class ThreadLocalDemo {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        threadLocal.set("Это поток main.");
        System.out.println(threadLocal.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
