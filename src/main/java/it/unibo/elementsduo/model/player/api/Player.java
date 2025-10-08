

package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.model.map.api.TileType;

public interface Player {

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

    // Regole di interazione con le tile
    boolean canWalkOn(TileType type);     
    boolean isFatal(TileType type);       
    TileType getGoalExitType();           

    // Bounding box
    default double getWidth() { return 0.8; }
    default double getHeight() { return 1.0; }
}