
package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

public interface Player extends Collidable {

    double getX();

    double getY();

    double getVelocityY();

    boolean isOnGround();

    void move(double dx);

    void applyGravity(double gravity);

    void jump(double strength);

    void landOn(double groundY);

    void stopJump(double ceilingY);

    void setAirborne();

    // Bounding box
    default double getWidth() {
        return 0.8;
    }

    default double getHeight() {
        return 1.0;
    }
}