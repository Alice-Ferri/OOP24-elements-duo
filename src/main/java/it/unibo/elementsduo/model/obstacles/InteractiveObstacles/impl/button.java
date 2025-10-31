package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

public class button extends InteractiveObstacle implements Triggerable {

    private boolean active = false;
    private ArrayList<TriggerListener> linkedObjects = new ArrayList<>();
    private final static double halfWidth = 0.5;
    private final static double halfHeight = 0.5;

    public button(Position center) {
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
            this.linkedObjects.stream().forEach(t -> t.onTriggered(active));
        }
    }

    @Override
    public void deactivate() {
        if (this.active) {
            this.active = false;
            this.linkedObjects.stream().forEach(t -> t.onTriggered(active));
        }
    }

    // this method is not needed
    @Override
    public void toggle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggle'");
    }

    public void linkTo(TriggerListener t) {
        linkedObjects.add(t);
    }

}
