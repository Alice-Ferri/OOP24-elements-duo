package it.unibo.elementsduo.model.enemies.impl;


import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.resources.Position;

/* Standard enemies that move around the level and inflict damage when the player touches them. */
public class ClassicEnemiesImpl implements Enemy {
    private Position position;
    private boolean alive=true;

    public ClassicEnemiesImpl(Position pos) {
        this.position = new Position(pos.x(), pos.y());
        this.alive = true;
        
    }

    @Override
    public void move() {
    
}

    @Override
    public void attack() {

        // Classic enemy does not attack, so this method does nothing
    }

    /**
     * Update method called every tick.
     * Moves the enemy and returns Optional.empty() since it does not shoot.
     * @return Optional.empty() always
     */
    public void update() {
        
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public Position GetPosition() {
        return position;
    }
}
