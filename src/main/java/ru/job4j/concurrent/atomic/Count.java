package ru.job4j.concurrent.atomic;

/*  Одновременно с объектом может работать только одна нить.
 Если две нити пробуют выполнить один и тот же синхронизированный метод,
  то одна из нитей переходит в режим блокировки до тех пор пока первая нить не закончить работу с этим методом.
Синхронизация делает параллельную программу последовательной.   */
public class Count {
    private int value;

    /*      public void increment() {
              value++;
          }

          public int get() {
              return value;
          }

 Код внутри метода, обозначенного synchronized, называется критической секцией. */
    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }
}
/*Как только нить заходит в syncronized метод какого-либо класса,
 другая нить не сможет зайти ни в один из syncronized методов этого класса,
 пока первая нить не освободит метод, который она заняла.
 В случае нестатического метода монитором будет объект этого класса.
 В случае со статическом методом монитором будет сам класс.*/