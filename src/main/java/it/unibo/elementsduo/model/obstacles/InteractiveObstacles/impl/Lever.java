package it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.Toggler;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.TriggerSource;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents a lever that can be toggled on and off.
 * 
 * <p>
 * When toggled, it notifies all linked {@link TriggerListener} objects of its
 * state change.
 * </p>
 */
public final class Lever extends AbstractInteractiveObstacle implements TriggerSource, Toggler {

     private static final double HALF_WIDTH = 0.2;
     private static final double HALF_HEIGHT = 0.5;

     private boolean active;
     private final List<TriggerListener> linkedObjects = new ArrayList<>();

     /**
      * Creates a new lever centered at the specified position.
      *
      * 
      * @param center the position of the lever's center
      */
     public Lever(final Position center) {
          super(center, HALF_WIDTH, HALF_HEIGHT);
     }

     /**
      * {@inheritDoc}
      *
      * @return true if the lever is currently active, false otherwise
      */
     @Override
     public boolean isActive() {
          return this.active;
     }

     /**
      * {@inheritDoc}
      * 
      * Toggles the lever state and notifies all registered listeners.
      */
     @Override
     public void toggle() {
          this.active = !this.active;
          this.linkedObjects.forEach(t -> t.onTriggered(this.active));
     }

     /**
      * {@inheritDoc}
      *
      * @return false since the lever does not have a physical response
      */
     @Override
     public boolean hasPhysicsResponse() {
          return false;
     }

     /**
      * {@inheritDoc}
      * 
      * Adds a {@link TriggerListener} that will be notified when the lever is
      * toggled.
      *
      * @param listener the listener to add
      */
     @Override
     public void addListener(final TriggerListener listener) {
          this.linkedObjects.add(listener);
     }

     /**
      * {@inheritDoc}
      * 
      * <p>
      * Removes a {@link TriggerListener} from the list of listeners.
      *
      * @param listener the listener to remove
      */
     @Override
     public void removeListener(final TriggerListener listener) {
          this.linkedObjects.remove(listener);
     }

     /**
      * {@inheritDoc}
      *
      * @return the collision layer associated with this lever
      */
     @Override
     public CollisionLayer getCollisionLayer() {
          return CollisionLayer.LEVER;
     }
}
