package it.unibo.elementsduo.model.map.mapvalidator.impl;

import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;
import it.unibo.elementsduo.resources.Position;

public class MapValidatorImpl implements MapValidator{

    private record MapDimensions(int minX, int minY, int maxX, int maxY) { }

    @Override
    public void validate(Level level) throws InvalidMapException {
        if (level.getAllObstacles().isEmpty()) {
            throw new InvalidMapException("La mappa è vuota.");
        }
        
        checkSpawnsAndExits(level);
    
        checkBoundaries(level);

        checkReachability(level);

        checkEnemyFloors(level);
    }

    private void checkSpawnsAndExits(final Level level) throws InvalidMapException{
        if (level.getObstaclesByClass(fireSpawn.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire spawn.");
        }
        if (level.getObstaclesByClass(waterSpawn.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water spawn.");
        }
        if (level.getObstaclesByClass(fireExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire exit.");
        }
        if (level.getObstaclesByClass(waterExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water exit.");
        }
    }

    private void checkBoundaries(final Level level) throws InvalidMapException{
        final MapDimensions dims = getMapDimensions(level);
        final Set<Position> wallPositions = getWallPositions(level);

        for (int x = dims.minX; x <= dims.maxX; x++) {
            checkWallExistsAt(new Position(x, dims.minY), wallPositions);
            checkWallExistsAt(new Position(x, dims.maxY), wallPositions);
        }

        for (int y = dims.minY + 1; y < dims.maxY; y++) {
            checkWallExistsAt(new Position(dims.minX, y), wallPositions);
            checkWallExistsAt(new Position(dims.maxX, y), wallPositions);
        }
    }

    private MapDimensions getMapDimensions(final Level level) {
        final int minX = 0;
        final int minY = 0;
        final int maxX = level.getAllObstacles().stream()
                .mapToInt(obs -> (int) obs.getHitBox().getCenter().x())
                .max().orElse(0);
        final int maxY = level.getAllObstacles().stream()
                .mapToInt(obs -> (int) obs.getHitBox().getCenter().y())
                .max().orElse(0);
        return new MapDimensions(minX, minY, maxX, maxY);
    }

    private Set<Position> getWallPositions(final Level level) {
        return level.getObstaclesByClass(Wall.class).stream()
            .map(obs -> new Position(
                (int) obs.getHitBox().getCenter().x(),
                (int) obs.getHitBox().getCenter().y()
            ))
            .collect(Collectors.toSet());
    }

    private void checkWallExistsAt(final Position pos, final Set<Position> wallPositions) throws InvalidMapException {
        if (!wallPositions.contains(pos)) {
            throw new InvalidMapException("Bordo non chiuso: Muro mancante a " + pos);
        }
    }

    private void checkReachability(final Level level) throws InvalidMapException{
        
    }

    private void checkEnemyFloors(final Level level) throws InvalidMapException{
        final Set<Position> floorPositions = level.getObstaclesByClass(Floor.class).stream()
                .map(obs -> new Position(
                (int) obs.getHitBox().getCenter().x(),
                (int) obs.getHitBox().getCenter().y()))
                .collect(Collectors.toSet());

        for (final Enemy enemy : level.getAllEnemies()) {
            final Position enemyPos = new Position(
                (int) enemy.getX(),
                (int) enemy.getY()
            );
            
            final Position posBelow = new Position(enemyPos.x(), enemyPos.y() + 1);
            if (!floorPositions.contains(posBelow)) {
                throw new InvalidMapException(
                    "Errore di posizionamento: Il nemico a " + enemyPos 
                    + " sta fluttuando. Manca un pavimento nella posizione " + posBelow + "."
                );
            }
        }
    }
    
}
