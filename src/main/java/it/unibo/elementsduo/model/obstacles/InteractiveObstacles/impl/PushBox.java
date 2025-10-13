package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pushable;

public class PushBox extends InteractiveObstacle implements Pushable {

    public PushBox(HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public void push() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'push'");
    }

}
