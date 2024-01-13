package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        }*/
    /*Это метод с предикатом, то же самое что сверху*/
    public String getContentSimple() throws IOException {
    /*    Predicate<Character> filter = new Predicate<Character>() {
            @Override
            public boolean test(Character character) {
                return character > 0;
            }
        };*/
        return getContent(character -> character > 0);
    }

    /*   public String getContentWithoutUnicode() throws IOException {
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
    public String getContentWithoutUnicode() throws IOException {
        return getContent(character -> character < 0x80);
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
