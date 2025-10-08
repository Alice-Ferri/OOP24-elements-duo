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
}