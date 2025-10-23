package it.unibo.elementsduo.model.map.api;

import java.util.Set;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;

public interface Level {

    
    Set<obstacle> getAllObstacles();
    Set<Enemy> getAllEnemies();

    <T extends obstacle> Set<T> getObstaclesByClass(final Class<T> type);
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




}
