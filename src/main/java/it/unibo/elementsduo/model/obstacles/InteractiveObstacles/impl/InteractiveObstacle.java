package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.resources.Position;

public abstract class InteractiveObstacle implements Collidable {

    Position center;
    final double halfHeight;
    final double halfWidth;

    public InteractiveObstacle(Position center, double halfW, double halfH) {
        this.center = center;
        this.halfWidth = halfW;
        this.halfHeight = halfH;
    }

    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(center, 2 * halfHeight, 2 * halfWidth);
    }

    public Position getCenter() {
        return this.center;
    }

}
