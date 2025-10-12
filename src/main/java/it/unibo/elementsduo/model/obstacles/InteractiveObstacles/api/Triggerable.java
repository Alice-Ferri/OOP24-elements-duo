package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

// interface for the lever

public interface Triggerable {
    boolean isActive();

    void activate();

    void deactivate();
}