package ru.job4j.concurrent;

/*  Чтобы получить состояние нити можно воспользоваться методом - getState() */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(() -> {
        });
        System.out.println(first.getName() + "  " + first.getState());
        System.out.println(second.getName() + "  " + second.getState());
        first.start();
        second.start();

        System.out.println(first.getName() + "  " + first.getState());
        System.out.println(second.getName() + "  " + second.getState());
/*        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + first.getState());
        } */
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + "  " + first.getState());
            System.out.println(second.getName() + "  " + second.getState());
        }
        System.out.println(first.getName() + "  " + first.getState());
        System.out.println(second.getName() + "  " + second.getState());
        System.out.println("Работа завершена.");

    }
}
