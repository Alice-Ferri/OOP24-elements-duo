package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

public class PlatformImpl extends InteractiveObstacle implements Triggerable, TriggerListener {

    private static double halfWidth = 0.5;
    private static double halfHeight = 0.5;

    private Position a, b;
    private double speed = 1.0;
    private Position pos;
    private Vector2D velocity = Vector2D.ZERO;
    private boolean forward = true;
    private boolean active = false;

    public PlatformImpl(Position pos, Position a, Position b) {
        super(pos, halfWidth, halfHeight);
        this.a = a;
        this.b = b;
        this.pos = pos;
    }

    public void update(double delta) {
        if (!this.active) {
            return;
        }
        Position target = forward ? b : a;
        Vector2D dir = pos.vectorTo(target).normalize();
        velocity = dir.multiply(speed);
        pos = pos.add(velocity.multiply(delta));
        this.center = pos;

        if (pos.distanceBetween(target) < speed * delta) {
            forward = !forward;
            this.velocity = Vector2D.ZERO;
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
        this.velocity = Vector2D.ZERO;
    }

    @Override
    public void toggle() {
        this.active = !this.active;
    }

    public Vector2D getVelocity() {
        return this.velocity;
    }

    @Override
    public void onTriggered(boolean state) {
        if (state) {
            this.activate();
        } else {
            this.deactivate();
        }
    }

}
