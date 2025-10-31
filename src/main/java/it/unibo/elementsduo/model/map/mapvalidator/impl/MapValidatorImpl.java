package it.unibo.elementsduo.model.map.mapvalidator.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of {@link MapValidator}.
 * Validates a map by checking for spawn/exit points, boundaries,
 * and the reachability of exits by players.
 */
public final class MapValidatorImpl implements MapValidator {

    private static final Set<Class<? extends obstacle>> ENEMY_SURFACES = Set.of(
            Floor.class, lavaPool.class, waterPool.class, greenPool.class
    );
    private static final Set<Class<? extends obstacle>> INTERACTIVE_SURFACES = Set.of(
            Floor.class, lavaPool.class, waterPool.class, greenPool.class
    );


    /**
     * {@inheritDoc}
     * Performs a series of checks to validate the map's structure and logic.
     *
     * @throws InvalidMapException if the map fails any of the validation checks.
     */
    @Override
    public void validate(final Level level) throws InvalidMapException {
        if (level.getAllObstacles().isEmpty()) {
            throw new InvalidMapException("The map is empty.");
        }

        checkSpawnsAndExits(level);
        checkBoundaries(level);
        checkReachability(level);
        checkEnemyFloors(level);
        checkFloatingInteractives(level);
    }

    private void checkSpawnsAndExits(final Level level) throws InvalidMapException {
        if (level.getEntitiesByClass(Fireboy.class).size() != 1) {
            throw new InvalidMapException("Map must have exactly 1 fire spawn.");
        }
        if (level.getEntitiesByClass(Watergirl.class).size() != 1) {
            throw new InvalidMapException("Map must have exactly 1 water spawn.");
        }
        if (level.getEntitiesByClass(fireExit.class).size() != 1) {
            throw new InvalidMapException("Map must have exactly 1 fire exit.");
        }
        if (level.getEntitiesByClass(waterExit.class).size() != 1) {
            throw new InvalidMapException("Map must have exactly 1 water exit.");
        }
    }

    private void checkBoundaries(final Level level) throws InvalidMapException {
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
        final int minX = level.getAllObstacles().stream()
                .mapToInt(obs -> (int) obs.getHitBox().getCenter().x())
                .min().orElse(0);
        final int minY = level.getAllObstacles().stream()
                .mapToInt(obs -> (int) obs.getHitBox().getCenter().y())
                .min().orElse(0);
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
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());
    }

    private void checkWallExistsAt(final Position pos, final Set<Position> wallPositions) throws InvalidMapException {
        if (!wallPositions.contains(pos)) {
            throw new InvalidMapException("Boundary not closed: Missing wall at " + pos);
        }
    }

    private void checkEnemyFloors(final Level level) throws InvalidMapException {
        final Set<Position> validSurfaces = level.getAllObstacles().stream()
                .filter(obs -> ENEMY_SURFACES.stream().anyMatch(type -> type.isInstance(obs)))
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());

        for (final Enemy enemy : level.getAllEnemies()) {
            final Position enemyPos = getGridPosFromEnemy(enemy);
            final Position posBelow = new Position(enemyPos.x(), enemyPos.y() + 1);

            if (!validSurfaces.contains(posBelow)) {
                throw new InvalidMapException(
                        "Positioning Error: Enemy at " + enemyPos
                        + " is floating. Missing a valid surface at " + posBelow + "."
                );
            }
        }
    }

    private void checkReachability(final Level level) throws InvalidMapException {
        final MapDimensions dims = getMapDimensions(level);

        final Set<Position> blockedPositions = level.getAllObstacles().stream()
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());
        final Set<Position> emptySpace = calculateEmptySpace(dims, blockedPositions);

        final Set<Position> platformPositions = level.getEntitiesByClass(PlatformImpl.class).stream()
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());

        final Set<Position> pushBoxes = level.getEntitiesByClass(PushBox.class).stream()
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());

        final Set<Position> walkableSpace = new HashSet<>(emptySpace);
        walkableSpace.addAll(platformPositions);
        walkableSpace.addAll(pushBoxes);

        final Position fireSpawnPos = getGridPosFromHitBox(level.getEntitiesByClass(Fireboy.class).iterator().next());
        final Position fireExitPos = getGridPosFromHitBox(level.getEntitiesByClass(fireExit.class).iterator().next());
        final Position waterSpawnPos = getGridPosFromHitBox(level.getEntitiesByClass(Watergirl.class).iterator().next());
        final Position waterExitPos = getGridPosFromHitBox(level.getEntitiesByClass(waterExit.class).iterator().next());

        checkPathForPlayer("Fire", fireSpawnPos, fireExitPos, walkableSpace, emptySpace);
        checkPathForPlayer("Water", waterSpawnPos, waterExitPos, walkableSpace, emptySpace);
    }

    private void checkPathForPlayer(final String playerName, final Position spawn, final Position exit,
            final Set<Position> walkableSpace, final Set<Position> emptySpace)
            throws InvalidMapException {

        final Set<Position> startPoints = getNeighbors(spawn).stream()
                .filter(emptySpace::contains)
                .collect(Collectors.toSet());

        if (startPoints.isEmpty()) {
            throw new InvalidMapException(playerName + " spawn at " + spawn
                    + " is not adjacent to any empty space.");
        }

        final Set<Position> endPoints = getNeighbors(exit).stream()
                .filter(emptySpace::contains)
                .collect(Collectors.toSet());

        if (endPoints.isEmpty()) {
            throw new InvalidMapException(playerName + " exit at " + exit + " is not adjacent to any empty space.");
        }

        if (!isPathPossible(startPoints, endPoints, walkableSpace)) {
            throw new InvalidMapException(playerName + " cannot reach the exit.");
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

    private boolean isPathPossible(final Set<Position> startPoints, final Set<Position> endPoints,
            final Set<Position> walkableSpace) {

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

    private void checkFloatingInteractives(final Level level) throws InvalidMapException {
        final Set<Position> validSurfaces = level.getAllObstacles().stream()
                .filter(obs -> INTERACTIVE_SURFACES.stream().anyMatch(type -> type.isInstance(obs)))
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());

        final Set<Collidable> interactives = new HashSet<>();
        interactives.addAll(level.getEntitiesByClass(Lever.class));
        interactives.addAll(level.getEntitiesByClass(button.class));

        for (final Collidable interactive : interactives) {
            final Position interactivePos = getGridPosFromHitBox(interactive);
            final Position posBelow = new Position(interactivePos.x(), interactivePos.y() + 1);

            if (!validSurfaces.contains(posBelow)) {
                throw new InvalidMapException(
                        "Positioning Error: The object " + interactive.getClass().getSimpleName()
                        + " at " + interactivePos + " is floating. Missing a floor at " + posBelow + "."
                );
            }
        }
    }

    private List<Position> getNeighbors(final Position pos) {
        return List.of(
                new Position(pos.x() + 1, pos.y()),
                new Position(pos.x() - 1, pos.y()),
                new Position(pos.x(), pos.y() + 1),
                new Position(pos.x(), pos.y() - 1)
        );
    }

    private Position getGridPosFromEnemy(final Enemy enemy) {
        return new Position(
                (int) enemy.getX(),
                (int) enemy.getY()
        );
    }

    private Position getGridPosFromHitBox(final Collidable entity) {
        return new Position(
                (int) entity.getHitBox().getCenter().x(),
                (int) entity.getHitBox().getCenter().y()
        );
    }

    /**
     * Record to store the minimum and maximum dimensions of the map grid.
     *
     * @param minX minimum X coordinate
     * @param minY minimum Y coordinate
     * @param maxX maximum X coordinate
     * @param maxY maximum Y coordinate
     */
    private record MapDimensions(int minX, int minY, int maxX, int maxY) { }
}
