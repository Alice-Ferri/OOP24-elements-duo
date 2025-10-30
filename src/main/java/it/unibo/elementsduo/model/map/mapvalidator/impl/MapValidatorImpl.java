package it.unibo.elementsduo.model.map.mapvalidator.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
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
        if (level.getEntitiesByClass(Fireboy.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire spawn.");
        }
        if (level.getEntitiesByClass(Watergirl.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water spawn.");
        }
        if (level.getEntitiesByClass(fireExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire exit.");
        }
        if (level.getEntitiesByClass(waterExit.class).size() != 1) {
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
        return level.getEntitiesByClass(Wall.class).stream()
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

    private void checkEnemyFloors(final Level level) throws InvalidMapException{
        final Set<Position> floorPositions = level.getEntitiesByClass(Floor.class).stream()
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

    
    private void checkReachability(final Level level) throws InvalidMapException{
        final MapDimensions dims = getMapDimensions(level);
        final Set<Position> blockedPositions = level.getAllObstacles().stream()
                .map(this::getObstaclePos)
                .collect(Collectors.toSet());
        
        final Set<Position> emptySpace = calculateEmptySpace(dims, blockedPositions);

        final Position fireSpawnPos = level.getEntitiesByClass(Fireboy.class).iterator().next().getHitBox().getCenter();
        final Position fireExitPos = level.getEntitiesByClass(fireExit.class).iterator().next().getHitBox().getCenter();
        final Position waterSpawnPos = level.getEntitiesByClass(Watergirl.class).iterator().next().getHitBox().getCenter();
        final Position waterExitPos = level.getEntitiesByClass(waterExit.class).iterator().next().getHitBox().getCenter();

        checkPathForPlayer("Fire", fireSpawnPos, fireExitPos, emptySpace);
        checkPathForPlayer("Water", waterSpawnPos, waterExitPos, emptySpace);
    }

    private void checkPathForPlayer(final String playerName, final Position spawn, final Position exit, final Set<Position> emptySpace) throws InvalidMapException {
        final Set<Position> startPoints = getNeighbors(spawn).stream()
                .filter(emptySpace::contains)
                .collect(Collectors.toSet());
        
        if (startPoints.isEmpty()) {
            throw new InvalidMapException("Lo spawn di " + playerName + " a " + spawn + "non è adiacente a nessuno spazio vuoto.");
        }

        final Set<Position> endPoints = getNeighbors(exit).stream()
                .filter(emptySpace::contains)
                .collect(Collectors.toSet());
        
        if (endPoints.isEmpty()) {
            throw new InvalidMapException("L'uscita di " + playerName + " a " + exit + " non è adiacente a nessuno spazio vuoto.");
        }
        if (!isPathPossible(startPoints, endPoints, emptySpace)) {
            throw new InvalidMapException(playerName + " non può raggiungere l'uscita.");
        }
    }

    private Set<Position> calculateEmptySpace(final MapDimensions dims, final Set<Position> blockedPositions) {
        final Set<Position> empty = new HashSet<>();
        for (int y = dims.minY; y <= dims.maxY; y++) {
            for (int x = dims.minX; x <= dims.maxX; x++) {
                final Position p = new Position(x, y);
                if (!blockedPositions.contains(p)) {
                    empty.add(p);
                }
            }
        }
        return empty;
    }

    private boolean isPathPossible(final Set<Position> startPoints, final Set<Position> endPoints,final Set<Position> walkableSpace) {
        
        final Queue<Position> queue = new LinkedList<>(startPoints);
        final Set<Position> visited = new HashSet<>(startPoints);

        while (!queue.isEmpty()) {
            final Position current = queue.poll();

            if (endPoints.contains(current)) {
                return true; 
            }
            for (final Position neighbor : getNeighbors(current)) {
               
                if (walkableSpace.contains(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    private List<Position> getNeighbors(final Position pos) {
        return List.of(
            new Position(pos.x() + 1, pos.y()),
            new Position(pos.x() - 1, pos.y()),
            new Position(pos.x(), pos.y() + 1),
            new Position(pos.x(), pos.y() - 1)
        );
    }

    private Position getObstaclePos(final obstacle obs) {
         return new Position(
            (int) obs.getHitBox().getCenter().x(),
            (int) obs.getHitBox().getCenter().y()
        );
    }
    
}
