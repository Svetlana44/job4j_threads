package ru.job4j.concurrent;

/*  Создать нить внутри метода main.
В теле метода создайте цикл от 0 до 100.
Через 1 секунду выводите на консоль информацию о загрузке.
Вывод должен быть с обновлением строки.  */
public class Wget {
    public static void main(String[] args) {
        Thread tread = new Thread(() -> {
            try {
                for (int index = 0; index <= 100; index++) {
                    Thread.sleep(1000);
        /*  Метод print печатает символы в строку без перевода каретки.
            Символ \r указывает, что каретку каждый раз нужно вернуть в начало строки.  */
                    System.out.print("\rLoading : " + index + "%");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tread.start();
    }
}
