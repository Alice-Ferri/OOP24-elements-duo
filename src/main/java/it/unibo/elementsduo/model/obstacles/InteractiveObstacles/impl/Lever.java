package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Toggler;
import it.unibo.elementsduo.resources.Position;

public class Lever extends AbstractInteractiveObstacle implements TriggerSource, Toggler {

    private static final double HALF_WIDTH = 0.2;

    private static final double HALF_HEIGHT = 0.5;

    private boolean active;

    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    public Lever(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void toggle() {
        this.active = !this.active;
        this.linkedObjects.forEach(t -> t.onTriggered(this.active));
    }

    @Override
    public boolean hasPhysicsResponse() {
        return false;
    }

    public void addListener(final TriggerListener listener) {
        this.linkedObjects.add(listener);

    }

    @Override
    public void removeListener(final TriggerListener listener) {
        this.linkedObjects.remove(listener);
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.LEVER;
    }

    @Override
    public EnumSet<CollisionLayer> getCollisionMask() {
        return EnumSet.of(CollisionLayer.PLAYER);
    }

}