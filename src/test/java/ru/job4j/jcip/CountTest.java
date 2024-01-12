package ru.job4j.jcip;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountTest {

    /*Этот тест не является надежным. То есть он зависит от случая, один раз он может пройти, другой раз нет.
     По этой причине подобные тесты мы не пишем - то есть те тесты, где создаются нити.
     1. Проблема гонок. Общий ресурс не синхронизирован.
     2. Проблема видимости общего ресурса. Одна нить считывает данные из кэша, другая из регистра.*/
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new Count();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(2);
    }
}