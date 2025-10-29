package it.unibo.elementsduo.model.map.level.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.ManagerInjectable;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;


public class LevelImpl implements Level {

    private final Set<GameEntity> gameEntities;
    private final Set<Projectiles> projectiles = new HashSet<>();

    public LevelImpl(final Set<GameEntity> gameEntities) {
        this.gameEntities = Set.copyOf(Objects.requireNonNull(gameEntities));
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
        return Set.copyOf(this.projectiles);
    }

    @Override
    public void addProjectile(final Projectiles p) {
        if (p != null) {
            this.projectiles.add(p);
        }
    }

    @Override
    public void cleanProjectiles() {
        this.projectiles.removeIf(proj -> !proj.isActive());
    }

    @Override
    public void setEnemiesMoveManager(final EnemiesMoveManager manager) {
        this.getEntitiesByClass(Enemy.class).stream()
                .filter(ManagerInjectable.class::isInstance)
                .map(ManagerInjectable.class::cast)
                .forEach(injectableEnemy -> injectableEnemy.setMoveManager(manager));
    }

    @Override
    public List<Collidable> getAllCollidables() {
        Stream<? extends GameEntity> entityStream = this.gameEntities.stream();
        Stream<? extends Projectiles> projectileStream = this.projectiles.stream()
                                                                .filter(Projectiles::isActive);

        return Stream.concat(entityStream, projectileStream)
                     .filter(Collidable.class::isInstance)
                     .filter(collidable -> !(collidable instanceof Enemy) || ((Enemy) collidable).isAlive())
                     .map(Collidable.class::cast)
                     .collect(Collectors.toList());
    }

}