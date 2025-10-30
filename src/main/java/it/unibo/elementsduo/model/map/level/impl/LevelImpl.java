package it.unibo.elementsduo.model.map.level.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;


public class LevelImpl implements Level {

    private final Set<GameEntity> gameEntities;

    public LevelImpl(final Set<GameEntity> gameEntities) {
        this.gameEntities = new HashSet<>(Objects.requireNonNull(gameEntities));
    }

    @Override
    public Set<GameEntity> getGameEntities() {
        return this.gameEntities; 
    }

    @Override
    public <T extends GameEntity> Set<T> getEntitiesByClass(final Class<T> type) {
        return this.gameEntities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<obstacle> getAllObstacles() {
        return getEntitiesByClass(obstacle.class);
    }

    @Override
    public Set<Enemy> getAllEnemies() {
        return getEntitiesByClass(Enemy.class);
    }

    @Override
    public Set<Enemy> getLivingEnemies() {
        return getAllEnemies().stream()      
                .filter(Enemy::isAlive)      
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Player> getAllPlayers() {
        return getEntitiesByClass(Player.class);
    }

    @Override
    public Set<InteractiveObstacle> getAllInteractiveObstacles() {
        return getEntitiesByClass(InteractiveObstacle.class);
    }

    @Override
    public Set<Projectiles> getAllProjectiles() {
        return getEntitiesByClass(Projectiles.class);
    }

    @Override
    public void addProjectile(final Projectiles p) {
        if (p != null) {
            this.gameEntities.add(p);
        }
    }

    @Override
    public void cleanProjectiles() {
        this.gameEntities.removeIf(e -> e instanceof Projectiles p && !p.isActive());
    }
    
    @Override
    public void cleanInactiveEntities() {
        this.gameEntities.removeIf(entity -> (entity instanceof Projectiles p && !p.isActive()) || (entity instanceof Enemy e && !e.isAlive()));
    }

    @Override
    public List<Collidable> getAllCollidables() {
        return this.gameEntities.stream()
                .filter(Collidable.class::isInstance) 
                .map(Collidable.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Updatable> getAllUpdatables() {
        return this.gameEntities.stream()
                .filter(Updatable.class::isInstance)
                .map(Updatable.class::cast)
                .collect(Collectors.toSet());
    }


}