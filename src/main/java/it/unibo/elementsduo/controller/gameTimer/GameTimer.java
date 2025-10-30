package it.unibo.elementsduo.controller.gameTimer;

import java.util.concurrent.atomic.AtomicLong;

public class GameTimer implements Runnable {

    private static final double MS_PER_SECOND = 1000;

    private volatile boolean running = false;
    private final AtomicLong elapsedTime = new AtomicLong(0);
    private Thread timerThread;
    private long lastUpdate;


    @Override
    public void run() {
        lastUpdate = System.currentTimeMillis();
        while (running) {
            long now = System.currentTimeMillis();
            long delta = now - lastUpdate;
            elapsedTime.addAndGet(delta);
            lastUpdate = now;
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
        return elapsedTime.get() / MS_PER_SECOND;
    }


    public synchronized void reset() {
        elapsedTime.set(0);
        lastUpdate = System.currentTimeMillis();
    }
}
