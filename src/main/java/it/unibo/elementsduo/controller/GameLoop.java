package it.unibo.elementsduo.controller;

import java.util.Objects;

import it.unibo.elementsduo.controller.gamecontroller.api.GameController;

public class GameLoop implements Runnable {
    private static final int TARGET_FPS = 120;
    private static final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;
    private volatile boolean running = false;
    private Thread gameThread;
    private final GameController engine;

    public GameLoop (final GameController engine) {
        this.engine = Objects.requireNonNull(engine);
    }

    public synchronized void start() {
        if (running) {
            return;
        } else {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (gameThread != null) {
            gameThread.interrupt(); 
        }
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();

        while (running) {
            final long now = System.nanoTime();
            final long UpdateLength = now - lastLoopTime;
            lastLoopTime = now;
            final double deltaTime = UpdateLength / 1_000_000_000.0;
            this.engine.update(deltaTime);
            this.engine.render();

            long elapsed = System.nanoTime() - lastLoopTime;
            long sleepNanos = OPTIMAL_TIME - elapsed;

            if (sleepNanos > 0) {
                try {
                    Thread.sleep(sleepNanos / 1_000_000, (int) (sleepNanos % 1_000_000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }
    }

}
