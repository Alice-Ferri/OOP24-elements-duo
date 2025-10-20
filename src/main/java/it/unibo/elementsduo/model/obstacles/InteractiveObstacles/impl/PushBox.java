package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pushable;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

public class PushBox extends InteractiveObstacle implements Pushable {

    private final static double FRICTION = 0.9;

    private Vector2D velocity = Vector2D.ZERO;
    private double mass;

    public PushBox(Position center, double halfWidth, double halfHeight, double mass) {
        super(center, halfWidth, halfHeight);
        this.mass = mass;
    }

    @Override
    public void push(Vector2D v) {
        Vector2D dv = new Vector2D(v.x() / mass, v.y() / mass);
        velocity = velocity.add(dv);
    }

    @Override
    public void move(Vector2D delta) {
        this.center = new Position(this.center.x() + delta.x(), this.center.y() + delta.y());
    }

    public void update(double dt) {
        move(velocity.multiply(dt));
        velocity.multiply(FRICTION);
    }

}
