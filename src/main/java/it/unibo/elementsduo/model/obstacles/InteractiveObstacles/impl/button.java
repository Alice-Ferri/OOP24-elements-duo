package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pressable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.resources.Position;

/**
 * Rappresenta un bottone (Button) che implementa la logica "premi e rilascia"
 * (Pressable).
 * Notifica i suoi listener (TriggerSource) quando viene premuto e rilasciato.
 */
public class Button extends AbstractInteractiveObstacle implements TriggerSource, Pressable {

    private static final double HALF_WIDTH = 0.5;

    private static final double HALF_HEIGHT = 0.5;

    private boolean active;

    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    public Button(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void press() {
        if (!this.active) {
            this.active = true;
            this.linkedObjects.forEach(t -> t.onTriggered(this.active));
        }
    }

    @Override
    public void release() {
        if (this.active) {
            this.active = false;
            this.linkedObjects.forEach(t -> t.onTriggered(this.active));
        }
    }

    @Override
    public void addListener(final TriggerListener listener) {
        this.linkedObjects.add(listener);
    }

    @Override
    public void removeListener(final TriggerListener listener) {
        this.linkedObjects.remove(listener);
    }


    @Override
    public EnumSet<CollisionLayer> getCollisionMask() {
        return EnumSet.of(
            CollisionLayer.PLAYER,
            CollisionLayer.PUSHABLE
        );
    }
}
    
                
                