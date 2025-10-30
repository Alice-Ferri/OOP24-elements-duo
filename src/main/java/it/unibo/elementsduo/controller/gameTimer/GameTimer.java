package it.unibo.elementsduo.controller.gameTimer;

import java.util.concurrent.atomic.AtomicLong;

public class GameTimer implements Runnable {

    private static final double MS_PER_SECOND = 1000.0;

    private volatile boolean running = false;
    private long elapsedTime = 0;
    private Thread timerThread;
    private long lastUpdate;

    @Override
    public void run() {
        while (running) {
            long now = System.currentTimeMillis();
            long delta = now - lastUpdate;
            elapsedTime += delta;
            lastUpdate = now;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public synchronized void start() {
        if (running) return;

        running = true;
        lastUpdate = System.currentTimeMillis();

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

    public synchronized void reset() {
        elapsedTime = 0;
        lastUpdate = 0;
    }

    public synchronized double getElapsedSeconds() {
        return elapsedTime / MS_PER_SECOND;
    }
}