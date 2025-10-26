package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.resources.Position;

public class GemImpl implements Gem {

    private Position center;
    private HitBox hitbox;
    private boolean active = true;

    private static double GEM_SIZE = 0.5;

    public GemImpl(Position pos) {
        this.center = pos;
        this.hitbox = new HitBoxImpl(pos, GEM_SIZE, GEM_SIZE);
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void collect() {
        this.active = false;
    }

}
