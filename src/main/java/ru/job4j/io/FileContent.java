package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/* класс Immutable. Все поля final
Ошибки в IO: Не закрытые ресурсы. Чтение и запись файла без буфера.*/
public class FileContent {

    private final File file;

    public FileContent(File file) {
        this.file = file;
    }

/*  Методы getContent написаны в стиле копипаста. Нужно применить шаблон стратегия. content(Predicate<Character> filter)
if (filter.test((char) data))

    public String getContent() throws IOException {
        String output = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) > 0) {
                output += (char) data;
            }
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        String output = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        }
        return output;
    }*/

    public String getContent(Predicate<Character> filter) throws IOException {
        String output = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }
}
