package it.unibo.elementsduo.model.map.api;

import java.util.Set;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;
import it.unibo.elementsduo.model.player.api.Player;

public interface Level {

    
    Set<Obstacle> getAllObstacles();
    Set<Enemy> getAllEnemies();

    <T extends Obstacle> Set<T> getObstaclesByClass(final Class<T> type);
    Set<Wall> getWalls();
    Set<Floor> getFloors();
    Set<fireSpawn> getFireSpawn();
    Set<waterSpawn> getWaterSpawn();
    Set<fireExit> getFireExit();
    Set<waterExit> getWaterExit();

    <T extends Enemy> Set<T> getEnemyByClass(final Class<T> type);
    Set<Enemy> getLivingEnemies();

    Set<ClassicEnemiesImpl> getClassicEnemies();
    Set<ShooterEnemyImpl> getShooterEnemies();

    Set<Player> getAllPlayers();

    <T extends Enemy> Set<T> getInteractiveObsByClass(Class<T> type);




}
