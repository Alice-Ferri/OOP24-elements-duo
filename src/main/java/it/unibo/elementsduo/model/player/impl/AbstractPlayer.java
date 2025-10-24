package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Position;

public abstract class AbstractPlayer implements Player {
    protected double x;
    protected double y;
    protected double velocityY = 0;
    protected boolean onGround = true;

    protected AbstractPlayer(final Position startPos) {
        this.x = startPos.x();
        this.y = startPos.y();
    }

    @Override public double getX() {
        return x; 
    }

    @Override public double getY() {
        return y;
    }

    @Override public double getVelocityY() {
        return velocityY;
    }

    @Override public boolean isOnGround() {
        return onGround;
    }

    @Override public void move(final double dx) {
        this.x += dx;
    }

    @Override public void applyGravity(final double gravity) {
        if (!onGround) {
            velocityY += gravity;
            y += velocityY;
        }
    }

    @Override public void jump(final double strength) {
        if (onGround) {
            velocityY = -strength;
            onGround = false;
        }
    }

    @Override public void landOn(final double groundY) {
        this.y = groundY;
        velocityY = 0;
        onGround = true;
    }

    @Override public void stopJump(final double ceilingY) {
        this.y = ceilingY;
        this.velocityY = 0;
    }

    @Override public void setAirborne() {
        this.onGround = false;
    }

    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(
            new Position(this.x, this.y),
            getHeight(),
            getWidth()
        );
    }

    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal) {

        final double POSITION_SLOP = 0.001;
        final double CORRECTION_PERCENT = 0.8;
    
        if (penetration <= 0) {
            return;
        }

        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);
        this.x += correction.x();
        this.y += correction.y();

        final double velocityNormal = this.velocity.dot(normal);
        if (velocityNormal < 0) {
            this.velocity = this.velocity.subtract(normal.multiply(velocityNormal));
        }

        final double normalY = normal.y();
        if (normalY < -0.5) {
            this.onGround = true;
            this.velocity = new Vector2D(this.velocity.x(), 0);
        } else if (normalY > 0.5) {
            this.velocity = new Vector2D(this.velocity.x(), 0);
        }
    }

}