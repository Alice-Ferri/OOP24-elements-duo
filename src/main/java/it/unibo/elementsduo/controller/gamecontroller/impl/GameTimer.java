package it.unibo.elementsduo.controller.gamecontroller.impl;

/**
 * A simple game timer that measures elapsed time in seconds.
 */
public class GameTimer implements Runnable {

    private static final double MS_PER_SECOND = 1000.0;
    private static final int MS_TO_SLEEP = 50;

    private volatile boolean running;
    private long elapsedTime;
    private Thread timerThread;
    private long lastUpdate;

    /**
     * Main loop of the timer. Updates elapsed time while running.
     */
    @Override
    public void run() {
        while (running) {
            final long now = System.currentTimeMillis();
            final long delta = now - lastUpdate;
            elapsedTime += delta;
            lastUpdate = now;

            try {
                Thread.sleep(MS_TO_SLEEP);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /**
     * Starts the timer if it is not already running.
     */
    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        lastUpdate = System.currentTimeMillis();

        timerThread = new Thread(this);
        timerThread.start();
    }

    /**
     * Stops the timer if it is running.
     */
    public synchronized void stop() {
        running = false;
        if (timerThread != null) {
            try {
                timerThread.join();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Returns the total elapsed time in seconds since the timer was started.
     *
     * @return elapsed time in seconds
     */
    public synchronized double getElapsedSeconds() {
        return elapsedTime / MS_PER_SECOND;
    }
}
