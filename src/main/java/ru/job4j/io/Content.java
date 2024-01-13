package ru.job4j.io;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*Аннотация говорит пользователям данного класса, что класс можно использовать в многопоточном режиме и он будет работать правильно.*/
@ThreadSafe
public class Content {
    /*выставляется над общим ресурсом. Аннотация имеет входящий параметр.
     Он указывает на монитор, по которому мы будем синхронизироваться.*/
    @GuardedBy("this")
    private final File file;

    public Content(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }
}
