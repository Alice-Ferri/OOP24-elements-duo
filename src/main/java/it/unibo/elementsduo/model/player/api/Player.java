package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.model.obstacles.impl.obstacleType;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;

public interface Player extends Collidable, Movable {

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

    PlayerType getType();

    boolean canWalkOn(obstacleType.type type);     
    boolean isFatal(obstacleType.type type);       
    obstacleType.type getGoalExitType();           

    //Bounding box
    default double getWidth() {
        return 0.8;
    }

    default double getHeight() {
        return 1.0;
    }
}