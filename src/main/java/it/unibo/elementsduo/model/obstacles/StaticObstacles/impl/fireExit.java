package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.Wall;
import it.unibo.elementsduo.resources.Position;

public class fireExit extends Wall {

    public fireExit(Position pos, HitBox hitBox) {
        super(hitBox);
    }
}