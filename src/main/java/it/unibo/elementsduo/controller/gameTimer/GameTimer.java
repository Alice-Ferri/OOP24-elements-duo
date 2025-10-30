package it.unibo.elementsduo.controller.gameTimer;

public class GameTimer implements Runnable {

    private static final double CONSTANT_PER_SECONDS = 1000;

    private volatile boolean running = false;
    private long elapsedTime;
    private Thread timerThread;


    @Override
    public void run() {
        final long startTime = System.currentTimeMillis();
        while (running) {
            elapsedTime = System.currentTimeMillis() - startTime;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        timerThread = new Thread(this);
        timerThread.start();
    }

    public synchronized void stop() {
        running = false;
        if (timerThread != null) {
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public double getElapsedSeconds() {
        return elapsedTime / CONSTANT_PER_SECONDS;
    }
}
