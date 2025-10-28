package it.unibo.elementsduo.model.map.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.ManagerInjectable;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;
import it.unibo.elementsduo.model.player.api.Player;

public class LevelImpl implements Level{

    private final Set<Obstacle> obstacles;
    private final Set<Enemy> enemies;
    private final Set<Player> players;
    private final Set<InteractiveObstacle> interactiveObs;
    private final Set<Projectiles> projectiles = new HashSet<>();

    public LevelImpl(final Set<Obstacle> ob, final Set<Enemy> en, final Set<Player> pl, final Set<InteractiveObstacle> iob){
        this.obstacles = Set.copyOf(Objects.requireNonNull(ob));
        this.enemies = Set.copyOf(Objects.requireNonNull(en));
        this.players = Set.copyOf(Objects.requireNonNull(pl));
        this.interactiveObs = Set.copyOf(Objects.requireNonNull(iob));
        
    }

    @Override
    public Set<Obstacle> getAllObstacles() {
        return this.obstacles;
    }

    @Override
    public <T extends Obstacle> Set<T> getObstaclesByClass(Class<T> type) {
        return this.obstacles.stream()
        .filter(type::isInstance)
        .map(type::cast)          
        .collect(Collectors.toSet());
    
    }

    @Override
    public Set<Wall> getWalls() {
        return getObstaclesByClass(Wall.class);
    }

    @Override
    public Set<Floor> getFloors() {
        return getObstaclesByClass(Floor.class);
    }

    @Override
    public Set<fireSpawn> getFireSpawn() {
        return getObstaclesByClass(fireSpawn.class);
    }

    @Override
    public Set<waterSpawn> getWaterSpawn() {
        return getObstaclesByClass(waterSpawn.class);
    }

    @Override
    public Set<fireExit> getFireExit() {
        return getObstaclesByClass(fireExit.class);
    }

    @Override
    public Set<waterExit> getWaterExit() {
        return getObstaclesByClass(waterExit.class);
    }

    @Override
    public Set<Enemy> getAllEnemies() {
        return this.enemies;
    }

    @Override
    public <T extends Enemy> Set<T> getEnemyByClass(Class<T> type) {
       return this.enemies.stream()
        .filter(type::isInstance)
        .map(type::cast)          
        .collect(Collectors.toSet());
    }

    @Override
    public Set<Enemy> getLivingEnemies() {
        return this.enemies.stream()
        .filter(Enemy::isAlive)
        .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<ClassicEnemiesImpl> getClassicEnemies() {
        return getEnemyByClass(ClassicEnemiesImpl.class);
    }

    @Override
    public Set<ShooterEnemyImpl> getShooterEnemies() {
        return getEnemyByClass(ShooterEnemyImpl.class);
    }

    public Set<Player> getAllPlayers(){
        return this.players;
    }

    @Override
    public <T extends InteractiveObstacle> Set<T> getInteractiveObsByClass(Class<T> type) {
       return this.interactiveObs.stream()
        .filter(type::isInstance)
        .map(type::cast)          
        .collect(Collectors.toSet());
    }

    @Override
    public Set<Projectiles> getAllProjectiles() {
        return Set.copyOf(this.projectiles); 
    }

    @Override
    public void addProjectile(Projectiles p) {
        this.projectiles.add(p);
    }

    @Override
    public void cleanProjectiles() {
        this.projectiles.removeIf(p -> !p.isActive());
    }

    public void setEnemiesMoveManager(final EnemiesMoveManager manager) {
        this.enemies.stream()
            .filter(e -> e instanceof ManagerInjectable)
            .map(e -> (ManagerInjectable) e)
            .forEach(injectableEnemy -> injectableEnemy.setMoveManager(manager));
        }
    
}
