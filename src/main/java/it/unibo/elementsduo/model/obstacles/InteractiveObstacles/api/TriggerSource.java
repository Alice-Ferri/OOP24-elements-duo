package it.unibo.elementsduo.model.obstacles.interactiveobstacles.api;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;

/**
 * Represents an interactive object that can act as a trigger source,
 * notifying listeners when its state changes.
 */
public interface TriggerSource extends Collidable {

     /**
      * Registers a new {@link TriggerListener} to be notified when this
      * trigger source changes state.
      *
      * @param listener the listener to add
      */
     void addListener(TriggerListener listener);

     /**
      * Removes a previously registered {@link TriggerListener}.
      *
      * @param listener the listener to remove
      */
     void removeListener(TriggerListener listener);

     /**
      * Checks whether this trigger source is currently active.
      *
      * @return {@code true} if active, {@code false} otherwise
      *
      */
     boolean isActive();
}
