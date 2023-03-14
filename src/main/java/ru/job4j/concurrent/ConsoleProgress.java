package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/' };
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char ch : process) {
                    System.out.print("\r load: " + ch);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("\n" + " Process finished");
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
