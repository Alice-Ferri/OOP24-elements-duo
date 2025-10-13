package it.unibo.elementsduo.controller;

import java.util.Objects;

import it.unibo.elementsduo.controller.api.MainController;

public class GameLoop implements Runnable{
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1_000_000_000/TARGET_FPS;
    private volatile boolean running = false;
    private Thread gameThread;
    private final MainController engine;

    public GameLoop (final MainController engine) {
        this.engine = Objects.requireNonNull(engine);
    }

    public synchronized void start () {
        if (running) {
            return;
        }
        else {
            running = true;
            gameThread= new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void stop () {
        if (!running) {
            return;
        }
        running = false;
        try {
            gameThread.join();
            } 
        catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            gameThread.start();
        }
    
    


    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();

        while (running){
            final long now = System.nanoTime();
            final long UpdateLength = now - lastLoopTime;
            lastLoopTime = now;
            final double deltaTime = UpdateLength / ((double)OPTIMAL_TIME);
            this.engine.update(deltaTime);
            this.engine.render();

            try {
                final long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME / 1_000_000);
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                running=false;
            }
        }
    }
    
}
