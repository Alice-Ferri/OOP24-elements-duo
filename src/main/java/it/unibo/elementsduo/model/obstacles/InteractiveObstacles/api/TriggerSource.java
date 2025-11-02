package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

public interface TriggerSource extends Collidable {
    void addListener(TriggerListener listener);

    void removeListener(TriggerListener listener);

    boolean isActive();
}