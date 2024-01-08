package ru.job4j.chess.firuges.black;

/* класс BishopBlack не меняет состояние после создания объекта.

Правила создания Immutable объекта.

1. Все поля отмечены final.

2. Состояние объекта не изменяется после создания объекта.


import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

public class BishopBlack implements Figure {
    private final Cell position;

    public BishopBlack(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell destination) {
        throw new IllegalStateException(
                String.format("Could not way by diagonal from %s to %s", source, destination)
        );
    }

    public boolean isDiagonal(Cell source, Cell destination) {
        return false;
    }

    @Override
    public Figure copy(Cell destination) {
        return new BishopBlack(destination);
    }
}

 Пример не-Immutable класса.
 В этом классе все поля обозначены final, но метод add изменяет состояние объекта Cache. Класс Cache потокобезопасный, но не Immutable.

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Cache {
    private final ConcurrentHashMap<Integer, String> dictionary = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger();

    public Cache() {
        dictionary.put(ids.incrementAndGet(), "Petr Arsentev");
        dictionary.put(ids.incrementAndGet(), "Ivan Ivanov");
    }

    public void add(String name) {
        dictionary.put(ids.incrementAndGet(), name);
    }

    public boolean contains(String name) {
        return dictionary.containsValue(name);
    }
}
*/