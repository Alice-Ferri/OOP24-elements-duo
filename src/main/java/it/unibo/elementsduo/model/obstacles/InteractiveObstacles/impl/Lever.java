package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

public class Lever extends InteractiveObstacle implements Triggerable {

    boolean active = false; // initialy not active
    List<TriggerListener> linkedObjects = new ArrayList<>();

    public Lever(Position center, double halfWidth, double halfHeight) {
        super(center, halfWidth, halfHeight);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        if (!this.active) {
            this.active = true;
        }
    }

    @Override
    public void deactivate() {
        if (this.active) {
            this.active = false;
        }
    }

    @Override
    public void toggle() {
        this.active = !this.active;
        for (TriggerListener t : linkedObjects) {
            t.onTriggered(active);
        }
    }

    public void addLinkedObject(TriggerListener t) {
        linkedObjects.add(t);
    }

}
