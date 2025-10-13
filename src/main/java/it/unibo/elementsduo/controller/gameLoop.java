package it.unibo.elementsduo.controller;

public class gameLoop implements Runnable{
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1_000_000_000/TARGET_FPS;
    private volatile boolean running = false;
    private Thread gameThread;

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

    @Override
    public void run() {
        
    }
    
}
