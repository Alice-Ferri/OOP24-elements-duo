package it.unibo.elementsduo.model.powerups.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;

public interface PowerUp extends Collidable, GameEntity {
    PowerUpType getType();

    PowerUpEffect getEffectStrategy();

    double getDuration();

    void consume();

    boolean isActive();
}
