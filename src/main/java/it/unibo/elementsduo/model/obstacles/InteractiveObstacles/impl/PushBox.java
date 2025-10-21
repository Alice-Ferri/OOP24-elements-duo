package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pushable;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

public class PushBox extends InteractiveObstacle implements Pushable {

    private static final double GROUND_FRICTION = 0.75;
    private static final double AIR_FRICTION = 0.98;
    private static final double GRAVITY = 9.8;
    private static final double MIN_VELOCITY_X = 0.02;
    private static final double MAX_FALL_SPEED = 15;

    private Vector2D velocity = Vector2D.ZERO;
    private double mass;
    private boolean onGround = false;

    public PushBox(Position center, double halfWidth, double halfHeight, double mass) {
        super(center, halfWidth, halfHeight);
        this.mass = mass;
    }

    @Override
    public void push(Vector2D v) {
        Vector2D orizontal = new Vector2D(v.x(), 0);
        Vector2D accel = new Vector2D(orizontal.x() / mass, orizontal.y() / mass);
        this.velocity = this.velocity.add(accel);
    }

    @Override
    public void move(Vector2D delta) {
        this.center = this.center.add(delta);
    }

    public void update(double dt) {
        if (!onGround) { // gravity
            velocity = velocity.add(new Vector2D(0, GRAVITY * dt));
        }

        move(velocity.multiply(dt));
        if (onGround) {
            velocity = new Vector2D(velocity.x() * GROUND_FRICTION, velocity.y());
        } else {
            velocity = new Vector2D(velocity.x() * AIR_FRICTION, velocity.y());
        }

        if (Math.abs(velocity.x()) < MIN_VELOCITY_X) {
            velocity = new Vector2D(0, velocity.y());
        }

        if (velocity.y() > MAX_FALL_SPEED) {
            velocity = new Vector2D(velocity.x(), MAX_FALL_SPEED);
        }
    }

    public void setOnGround(boolean state) {
        this.onGround = state;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public Vector2D getVelocity() {
        return this.velocity;
    }

}
