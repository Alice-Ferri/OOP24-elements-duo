package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Platform;

public class PlatformImpl extends InteractiveObstacle implements Platform {

    public PlatformImpl(HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public void changePos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePos'");
    }

}
