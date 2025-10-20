package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

public class PlatformImpl extends InteractiveObstacle implements Triggerable {

    private Position a, b;
    private double speed;
    private Position pos;
    private Vector2D velocity = Vector2D.ZERO;
    private boolean forward = true;
    private boolean active = false;

    public PlatformImpl(Position pos, Position a, Position b, double halfWidth, double halfHeight, double speed) {
        super(pos, halfWidth, halfHeight);
        this.a = a;
        this.b = b;
        this.pos = pos;
        this.speed = speed;
    }

    public void update(double delta) {
        if (!this.active) {
            return;
        }
        Position target = forward ? b : a;
        Vector2D dir = new Vector2D(target.x() - this.pos.x(), target.y() - this.pos.y()).normalize();
        velocity = dir.multiply(speed);
        pos = new Position(this.pos.x() + (velocity.multiply(delta)).x(),
                this.pos.y() + (velocity.multiply(delta)).y());
        this.center = pos;

        Vector2D d = new Vector2D(target.x() - this.pos.x(), target.y() - this.pos.y());
        if (d.length() < speed * delta) {
            forward = !forward;
        }
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    @Override
    public void deactivate() {
        this.active = false;
    }

    @Override
    public void toggle() {
        this.active = !this.active;
    }

    public Vector2D getVelocity() {
        return this.velocity;
    }

}
