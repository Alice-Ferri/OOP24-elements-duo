package it.unibo.elementsduo.model.map.level.api;

import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager; // Consider moving this responsibility
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;


public interface Level {

    Set<GameEntity> getGameEntities();

    <T extends GameEntity> Set<T> getEntitiesByClass(final Class<T> type);
    Set<obstacle> getAllObstacles();
    Set<Enemy> getAllEnemies();
    Set<Player> getAllPlayers();
    Set<InteractiveObstacle> getAllInteractiveObstacles();

    Set<Projectiles> getAllProjectiles();
    void addProjectile(Projectiles p);
    void cleanProjectiles();

    void setEnemiesMoveManager(final EnemiesMoveManager manager);
    List<Collidable> getAllCollidables();
}