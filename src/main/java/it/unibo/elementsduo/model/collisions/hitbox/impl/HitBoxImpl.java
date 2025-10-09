package it.unibo.elementsduo.model.collisions.hitbox.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.resources.Position;

public class HitBoxImpl implements HitBox {

    private Position center;
    private double height;
    private double width;

    public HitBoxImpl(Position center, double height, double width) {
        this.center = center;
        this.height = height;
        this.width = width;
    }

    @Override
    public Position getCenter() {
        return this.center;
    }

    @Override
    public double getHalfHeight() {
        return this.height / 2.0;
    }

    @Override
    public double getHalfWidth() {
        return this.width / 2.0;
    }

    @Override
    public boolean intersects(HitBox otherHitBox) {
        return Math.abs(center.x() - otherHitBox.getCenter().x()) <= otherHitBox.getHalfWidth() + getHalfWidth()
                && Math.abs(center.y() - otherHitBox.getCenter().y()) <= otherHitBox.getHalfHeight() + getHalfHeight();
    }

}
