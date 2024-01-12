package ru.job4j.io;

import java.io.*;

/* класс Immutable. Все поля final
Ошибки в IO: Не закрытые ресурсы. Чтение и запись файла без буфера.*/
public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }
}