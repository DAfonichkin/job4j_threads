package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;

import static java.lang.Math.max;
import static java.lang.System.currentTimeMillis;

public class Wget implements Runnable {
    private final URL url;
    private final int speed;
    private final String fileName;

    public Wget(String[] args) {
        validate(args);
        try {
            this.url = new URL(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL");
        }
        this.speed = Integer.parseInt(args[1]);
        this.fileName = args[2];
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startDate = currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long finishDate = currentTimeMillis();
                    int currentTime = (int) (finishDate - startDate);
                    Thread.sleep(max(1000 - currentTime, 0));
                    startDate = currentTimeMillis();
                    downloadData = 0;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
             }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Wrong amounts of parameters");
        }
        try {
            URL url = new URL(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL");
        }
        try {
            int speed = Integer.parseInt(args[1]);
            if (speed <= 0) {
                throw new IllegalArgumentException("Speed should be greater than zero");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid speed");
        }
        Paths.get(args[2]);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread wget = new Thread(new Wget(args));
        System.out.println("Start download - " + new Date());
        wget.start();
        wget.join();
        System.out.println("Finish download - " + new Date());
    }
}