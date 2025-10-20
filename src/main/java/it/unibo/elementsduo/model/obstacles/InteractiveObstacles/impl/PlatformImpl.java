package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Platform;
import it.unibo.elementsduo.resources.Position;

public class PlatformImpl extends InteractiveObstacle implements Platform {

    public PlatformImpl(Position center, double halfWidth, double halfHeight) {
        super(center, halfWidth, halfHeight);
    }

    @Override
    public void changePos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePos'");
    }

}
