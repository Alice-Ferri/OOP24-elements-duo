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
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.GreenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.LavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.WaterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;
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
            Floor.class, LavaPool.class, WaterPool.class, GreenPool.class);
    private static final Set<Class<? extends obstacle>> INTERACTIVE_SURFACES = Set.of(
            Floor.class, LavaPool.class, WaterPool.class);
    private static final Set<Class<? extends obstacle>> FIREBOY_SURFACES = Set.of(
            Floor.class, LavaPool.class);
    private static final Set<Class<? extends obstacle>> WATERGIRL_SURFACES = Set.of(
            Floor.class, WaterPool.class);
    private static final Set<Class<? extends obstacle>> VISITABLE_SURFACES_BFS = Set.of(
            PlatformImpl.class,
            PushBox.class);

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
        checkAllFloatingEntities(level);
    }

    private void checkSpawnsAndExits(final Level level) throws InvalidMapException {
        if (level.getEntitiesByClass(Fireboy.class).size() != 1) {
            throw new InvalidMapException("Spawn and Exit Error: Map must have exactly 1 fire spawn.");
        }
        if (level.getEntitiesByClass(Watergirl.class).size() != 1) {
            throw new InvalidMapException("Spawn and Exit Error: Map must have exactly 1 water spawn.");
        }

        if (level.getEntitiesByClass(FireExit.class).size() != 1) {
            throw new InvalidMapException("Spawn and Exit Error: Map must have exactly 1 fire exit.");
        }
        if (level.getEntitiesByClass(WaterExit.class).size() != 1) {
            throw new InvalidMapException("Spawn and Exit Error: Map must have exactly 1 water exit.");
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

    private void checkWallExistsAt(final Position pos, final Set<Position> wallPositions) throws InvalidMapException {
        if (!wallPositions.contains(pos)) {
            throw new InvalidMapException("Boundary Error: Missing wall at " + pos);
        }
    }

    private void checkReachability(final Level level) throws InvalidMapException {
        final MapDimensions dims = getMapDimensions(level);

        final Set<Position> blockedPositions = level.getAllObstacles().stream()
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());
        final Set<Position> emptySpace = calculateEmptySpace(dims, blockedPositions);

        final Set<Position> visitableObstacles = level.getAllObstacles().stream()
                .filter(obs -> VISITABLE_SURFACES_BFS.stream()
                        .anyMatch(type -> type.isInstance(obs)))
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());

        final Set<Position> walkableSpace = new HashSet<>(emptySpace);
        walkableSpace.addAll(visitableObstacles);

        final Position fireSpawnPos = getGridPosFromHitBox(level.getEntitiesByClass(Fireboy.class).iterator().next());
        final Position fireExitPos = getGridPosFromHitBox(level.getEntitiesByClass(FireExit.class).iterator().next());
        final Position waterSpawnPos = getGridPosFromHitBox(
                level.getEntitiesByClass(Watergirl.class).iterator().next());
        final Position waterExitPos = getGridPosFromHitBox(level.getEntitiesByClass(WaterExit.class).iterator().next());

        checkPathForPlayer("Fire", fireSpawnPos, fireExitPos, walkableSpace);
        checkPathForPlayer("Water", waterSpawnPos, waterExitPos, walkableSpace);
    }

    private void checkPathForPlayer(final String playerName, final Position spawn, final Position exit,
            final Set<Position> walkableSpace)
            throws InvalidMapException {

        final Set<Position> startPoints = getNeighbors(spawn).stream()
                .filter(walkableSpace::contains)
                .collect(Collectors.toSet());

        if (startPoints.isEmpty()) {

            throw new InvalidMapException("Spawn Adj Error: " + playerName + " spawn at " + spawn
                    + " is not adjacent to any walkable space.");
        }

        final Set<Position> endPoints = getNeighbors(exit).stream()
                .filter(walkableSpace::contains)
                .collect(Collectors.toSet());

        if (endPoints.isEmpty()) {

            throw new InvalidMapException("Adj Error: " + playerName + " exit at " + exit
                     + " is not adjacent to any empty space.");
        }

        if (!isPathPossible(startPoints, endPoints, walkableSpace)) {
            throw new InvalidMapException("Path Error: " + playerName + " cannot reach the exit.");
        }
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

    private void checkAllFloatingEntities(final Level level) throws InvalidMapException {
        final Set<Position> enemyGround = getValidGround(level, ENEMY_SURFACES);
        final Set<Position> interactiveGround = getValidGround(level, INTERACTIVE_SURFACES);
        final Set<Position> fireboyGround = getValidGround(level, FIREBOY_SURFACES);
        final Set<Position> watergirlGround = getValidGround(level, WATERGIRL_SURFACES);

        final Set<Enemy> enemies = level.getAllEnemies();
        final Set<Collidable> interactives = new HashSet<>();
        interactives.addAll(level.getEntitiesByClass(Lever.class));
        interactives.addAll(level.getEntitiesByClass(Button.class));
        final Set<Fireboy> fireboys = level.getEntitiesByClass(Fireboy.class);
        final Set<Watergirl> watergirls = level.getEntitiesByClass(Watergirl.class);

        checkFloatingEntities(enemies, enemyGround, "Enemy");
        checkFloatingEntities(interactives, interactiveGround, "Interactive Object");
        checkFloatingEntities(fireboys, fireboyGround, "Fireboy");
        checkFloatingEntities(watergirls, watergirlGround, "Watergirl");
    }

    private <T extends Collidable> void checkFloatingEntities(
            final Set<T> entities,
            final Set<Position> validGround,
            final String entityTypeName) throws InvalidMapException {

        for (final T entity : entities) {
            final Position entityPos = getGridPosFromHitBox(entity);
            final Position posBelow = new Position(entityPos.x(), entityPos.y() + 1);

            if (!validGround.contains(posBelow)) {
                throw new InvalidMapException(
                        "Positioning Error: " + entityTypeName + " (" + entity.getClass().getSimpleName() + ")"
                                + " at " + entityPos
                                + " is floating or on an invalid surface. Missing a valid floor at " + posBelow + ".");
            }
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

    private Set<Position> getValidGround(final Level level, final Set<Class<? extends obstacle>> surfaceTypes) {
        return level.getAllObstacles().stream()
                .filter(obs -> surfaceTypes.stream().anyMatch(type -> type.isInstance(obs)))
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());
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

    private List<Position> getNeighbors(final Position pos) {
        return List.of(
                new Position(pos.x() + 1, pos.y()),
                new Position(pos.x() - 1, pos.y()),
                new Position(pos.x(), pos.y() + 1),
                new Position(pos.x(), pos.y() - 1));

    }

    private Position getGridPosFromHitBox(final Collidable entity) {
        return new Position(
                (int) entity.getHitBox().getCenter().x(),
                (int) entity.getHitBox().getCenter().y());
    }

    /**
     * Record to store the minimum and maximum dimensions of the map grid.
     *
     * @param minX minimum X coordinate
     * @param minY minimum Y coordinate
     * @param maxX maximum X coordinate
     * @param maxY maximum Y coordinate
     */
    private record MapDimensions(int minX, int minY, int maxX, int maxY) {
    }

}
