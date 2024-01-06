package ru.job4j.concurrent.atomic;

public class ShowRoomLock {
    /*Неявные мониторы*/
    /* Монитор - это объект ShowRoomLock */
    public synchronized void lockOfInstanceN() {
    }

    /* Монитор будет сам класс ShowRoomLock */
    public static synchronized void lockOfClassN() {
    }

    /*явные мониторы*/
    /* Монитор - это объект ShowRoomLock */
    public void lockOfInstance() {
        synchronized (this) {
        }
    } /* Монитор будет сам класс ShowRoomLock */

    public static void lockOfClass() {
        synchronized (ShowRoomLock.class) {
        }
    }
}
