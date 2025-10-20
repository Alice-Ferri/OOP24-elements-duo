package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

// interface for the lever and the button

public interface Triggerable {
    boolean isActive();

    void activate();

    void deactivate();

    void toggle();
}