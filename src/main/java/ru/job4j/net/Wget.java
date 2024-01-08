package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/*  Программа должна скачивать файл из сети с ограничением по скорости скачки.
 Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
Пауза должна вычисляться, а не быть константой.
Основное время занимает соединения с сервисом.
Время скачивания первого пакета занимает 100400 наносекунд.
 512 / 100400 = 5099 байт в миллисекунду. 1000000 наносекунда = 1 миллисекунда.
  Если в параметрах программы указана скорость 1000 байт в миллисекунду, то программа должна выставить паузу
   5099 / 1000 = 5 миллисекунд. Thread.sleep(5);
Если в параметрах программы указана скорость в 6000 байт в миллисекунду, то паузу выставлять не нужно.*/
public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileOut;

    public Wget(String url, int speed, String fileOut) {
        this.url = url;
        this.speed = speed;
        this.fileOut = fileOut;
    }

    @Override
    public void run() {
        fileDownLoad(this.url, this.speed, this.fileOut);
        System.out.println("Что-то скачалось");
        /* Скачать файл*/
    }

    /*  "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml"   1000  */
    public static boolean validationArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неверное количество аргументов.");
        }
        if (!args[0].contains("https://")) {
            throw new IllegalArgumentException("Первый аргумент не содержит ссылку.");
        }
        if (!args[1].matches("\\d+")) {
            throw new IllegalArgumentException("Второй аргумент должен быть числом.");
        }
/*        Pattern pattern = Pattern.compile("https://.*?/pom.xml");
        Matcher matcher = pattern.matcher(args[0]);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Первый аргумент не содержит ссылку.");
        }  */
        return true;
    }

    public static void fileDownLoad(String url, int speed, String fileOut) {
        var startAt = System.currentTimeMillis();
        /*  var file = new File("tmp.xml");   */
        var file = new File(fileOut);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
 /*  старт отсекаем время с помощью currentTimMillis и до цикла. Также до цикла добавляем переменную,
  в которую будем накапливать количество скачанных байт - как только она достигла speed - ставим на паузу если это необходимо    */
            int sumReadedBytes = 0;
            long startTimeMillis = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                /*      var downloadAt = System.nanoTime(); */
                output.write(dataBuffer, 0, bytesRead);
                sumReadedBytes += bytesRead;
                /*       System.out.println("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano.");   */
                if (sumReadedBytes >= speed) {
                    if (System.currentTimeMillis() - startTimeMillis < 1000) {
                        Thread.currentThread().sleep(1000 - (System.currentTimeMillis() - startTimeMillis));
                        System.out.println("-----------------\n Read " + sumReadedBytes + " bytes : " + (System.currentTimeMillis() - startTimeMillis) + " ms.");
                        sumReadedBytes = 0;
                        continue;
                    }
                }
       /*         System.out.println("-----------------\n Read " + sumReadedBytes + " bytes : " + (System.currentTimeMillis() - startTimeMillis) + " ms."); */
                startTimeMillis = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("file size " + Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (validationArgs(args)) {
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String fileOut = "tmp.xml";
            Thread wget = new Thread(new Wget(url, speed, fileOut));
            wget.start();
            wget.join();
        }
    }
}