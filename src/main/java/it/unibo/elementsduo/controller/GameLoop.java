package it.unibo.elementsduo.controller;

import java.util.Objects;

import it.unibo.elementsduo.controller.gamecontroller.api.GameController;

/**
 * Manages the main game loop thread, ensuring consistent updates (ticks)
 * and rendering frames at a target FPS.
 */
public final class GameLoop implements Runnable {

    private static final long NANOS_PER_MILLISECOND = 1_000_000L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;

    private static final int TARGET_FPS = 120;
    private static final long OPTIMAL_TIME = NANOS_PER_SECOND / TARGET_FPS;
    private volatile boolean running;
    private Thread gameThread;
    private final GameController engine;

    /**
     * Constructs a new GameLoop associated with a specific GameController.
     *
     * @param engine the GameController (engine) to update and render.
     */
    public GameLoop(final GameController engine) {
        this.engine = Objects.requireNonNull(engine);
    }

    /**
     * Starts the game loop in a new thread.
     * If the loop is already running, this method does nothing.
     */
    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Stops the game loop and interrupts its thread.
     * If the loop is not running, this method does nothing.
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (gameThread != null) {
            gameThread.interrupt(); 
        }
    }

    /**
     * {@inheritDoc}
     * Contains the core logic of the game loop, calculating delta time
     * and managing frame pacing to achieve the target FPS.
     */
    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();

        while (running) {
            final long now = System.nanoTime();
            final long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            final double deltaTime = updateLength / 1_000_000_000.0;
            this.engine.update(deltaTime);
            this.engine.render();

            final long elapsed = System.nanoTime() - lastLoopTime;
            final long sleepNanos = OPTIMAL_TIME - elapsed;

            if (sleepNanos > 0) {
                try {
                    Thread.sleep(sleepNanos / NANOS_PER_MILLISECOND, (int) (sleepNanos % NANOS_PER_MILLISECOND));
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }
    }

}

