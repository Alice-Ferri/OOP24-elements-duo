package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pushable;
import it.unibo.elementsduo.resources.Position;

public class PushBox extends InteractiveObstacle implements Pushable {

    public PushBox(Position center, double halfWidth, double halfHeight) {
        super(center, halfWidth, halfHeight);
    }

    @Override
    public void push() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'push'");
    }

}
