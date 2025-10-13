package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.utils.Position;

public class ProjectilesImpl implements Projectiles {
    private double x, y;
    private static final double SPEED = 0.05;
    private int direction;
    private boolean alive = true;

    public ProjectilesImpl(Position pos, int direction) {
        this.x = pos.x();
        this.y = pos.y();
        this.direction = direction;
    }

    

    @Override
public void update(Level level) {
    move(level);
    
}


    @Override
    public boolean isActive() {
        return alive;
    }

   @Override
    public double getX() {
        return this.x;
        
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getDirection() {
        return this.direction;
    }



    @Override
    public void move(Level level) {
        // Sposta il proiettile in avanti
    x += direction * SPEED;

    // Calcola la posizione corrente del proiettile
    Position pos = new Position((int) Math.floor(x), (int) Math.floor(y));

    // Controlla se in quella posizione c'è un ostacolo "solido"
    boolean blocked = level.getAllObstacles().stream()
        .filter(ob -> ob.getPos().equals(pos))
        .anyMatch(ob ->
            ob instanceof Wall ||
            ob instanceof Floor
        );

    // Se ha colpito un ostacolo, viene disattivato
    if (blocked) {
        alive = false;
    }
    }

    

}
