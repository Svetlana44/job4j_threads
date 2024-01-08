package ru.job4j.chess.firuges.black;

public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        /*  this.next = next;  */
        throw new IllegalStateException("The value of this variable cannot be set.");
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        throw new IllegalStateException("The value of this variable cannot be set.");
        /*   this.value = value;  */
    }
}