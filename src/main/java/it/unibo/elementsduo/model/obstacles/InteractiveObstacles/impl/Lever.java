package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

public class Lever extends InteractiveObstacle implements Triggerable {

    boolean active = false; // initialy not active
    List<Triggerable> linkedObjects = new ArrayList<>();

    public Lever(Position center, double halfWidth, double halfHeight) {
        super(center, halfWidth, halfHeight);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        toggle();
    }

    @Override
    public void deactivate() {
        if (this.active) {
            active = false;
            for (Triggerable t : linkedObjects) {
                t.deactivate();
            }
        }
    }

    @Override
    public void toggle() {
        this.active = !this.active;
        if (active) {
            for (Triggerable t : linkedObjects) {
                t.activate();
            }
        } else {
            for (Triggerable t : linkedObjects) {
                t.deactivate();
            }
        }
    }

    public void linkTo(Triggerable t) {
        linkedObjects.add(t);
    }

}
