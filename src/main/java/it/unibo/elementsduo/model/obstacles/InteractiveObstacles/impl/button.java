package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

public class button extends InteractiveObstacle implements Triggerable {

    private boolean active = false;
    private ArrayList<TriggerListener> linkedObjects = new ArrayList<>();

    public button(Position center, double halfWidth, double halfHeight) {
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
            for (TriggerListener t : linkedObjects) {
                t.onTriggered(this.active);
            }
        }
    }

    @Override
    public void deactivate() {
        if (this.active) {
            this.active = false;
            for (TriggerListener t : linkedObjects) {
                t.onTriggered(active);
            }
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
