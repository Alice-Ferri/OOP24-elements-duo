package it.unibo.elementsduo.model.map.level.api;

import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;

public interface Level {

    Set<GameEntity> getGameEntities();

    <T extends GameEntity> Set<T> getEntitiesByClass(Class<T> type);

    Set<obstacle> getAllObstacles();

    Set<Enemy> getAllEnemies();

    Set<Enemy> getLivingEnemies();

    Set<Player> getAllPlayers();

    Set<AbstractInteractiveObstacle> getAllInteractiveObstacles();

    Set<Projectiles> getAllProjectiles();

    void cleanInactiveEntities();

    void addProjectile(Projectiles p);

    void cleanProjectiles();

    Set<Updatable> getAllUpdatables();

    List<Collidable> getAllCollidables();
}