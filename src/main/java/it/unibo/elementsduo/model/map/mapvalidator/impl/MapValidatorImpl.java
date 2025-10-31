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
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.GreenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.LavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.WaterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.resources.Position;

public class MapValidatorImpl implements MapValidator {

    private static final Set<Class<? extends obstacle>> ENEMY_SURFACES = Set.of(
            Floor.class, LavaPool.class, WaterPool.class, GreenPool.class);
    private static final Set<Class<? extends obstacle>> INTERACTIVE_SURFACES = Set.of(
            Floor.class, LavaPool.class, WaterPool.class, GreenPool.class);

    private record MapDimensions(int minX, int minY, int maxX, int maxY) {
    }

    @Override
    public void validate(final Level level) throws InvalidMapException {
        if (level.getAllObstacles().isEmpty()) {
            throw new InvalidMapException("La mappa è vuota.");
        }

        checkSpawnsAndExits(level);
        checkBoundaries(level);
        checkReachability(level);
        checkEnemyFloors(level);
        checkFloatingInteractives(level);
    }

    private void checkSpawnsAndExits(final Level level) throws InvalidMapException {
        if (level.getEntitiesByClass(Fireboy.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire spawn.");
        }
        if (level.getEntitiesByClass(Watergirl.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water spawn.");
        }
        if (level.getEntitiesByClass(FireExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire exit.");
        }
        if (level.getEntitiesByClass(WaterExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water exit.");
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
            throw new InvalidMapException("Bordo non chiuso: Muro mancante a " + pos);
        }
    }

    private void checkEnemyFloors(final Level level) throws InvalidMapException {
        // 1. Crea un Set di tutte le superfici valide (Floor E Hazard)
        final Set<Position> validSurfaces = level.getAllObstacles().stream()
                .filter(obs -> ENEMY_SURFACES.stream().anyMatch(type -> type.isInstance(obs)))
                .map(this::getGridPosFromHitBox)
                .collect(Collectors.toSet());

        for (final Enemy enemy : level.getAllEnemies()) {
            final Position enemyPos = getGridPosFromEnemy(enemy);
            final Position posBelow = new Position(enemyPos.x(), enemyPos.y() + 1);

            if (!validSurfaces.contains(posBelow)) {
                throw new InvalidMapException(
                        "Errore di posizionamento: Il nemico a " + enemyPos
                                + " sta fluttuando. Manca una superficie valida (Floor/Hazard) a " + posBelow + ".");
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

        final Set<Position> walkableSpace = new HashSet<>(emptySpace);
        walkableSpace.addAll(platformPositions);

        final Position fireSpawnPos = getGridPosFromHitBox(level.getEntitiesByClass(Fireboy.class).iterator().next());
        final Position fireExitPos = getGridPosFromHitBox(level.getEntitiesByClass(FireExit.class).iterator().next());
        final Position waterSpawnPos = getGridPosFromHitBox(
                level.getEntitiesByClass(Watergirl.class).iterator().next());
        final Position waterExitPos = getGridPosFromHitBox(level.getEntitiesByClass(WaterExit.class).iterator().next());

        // 5. Esegui BFS sul nuovo 'walkableSpace'
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
            throw new InvalidMapException(
                    "Lo spawn di " + playerName + " a " + spawn + " non è adiacente a nessuno spazio vuoto.");
        }

        final Set<Position> endPoints = getNeighbors(exit).stream()
                .filter(emptySpace::contains)
                .collect(Collectors.toSet());

        if (endPoints.isEmpty()) {
            throw new InvalidMapException(
                    "L'uscita di " + playerName + " a " + exit + " non è adiacente a nessuno spazio vuoto.");
        }

        if (!isPathPossible(startPoints, endPoints, walkableSpace)) {
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
                        "Errore di posizionamento: L'oggetto " + interactive.getClass().getSimpleName()
                                + " a " + interactivePos + " sta fluttuando. Manca un pavimento a " + posBelow + ".");
            }
        }
    }

    private List<Position> getNeighbors(final Position pos) {
        return List.of(
                new Position(pos.x() + 1, pos.y()),
                new Position(pos.x() - 1, pos.y()),
                new Position(pos.x(), pos.y() + 1),
                new Position(pos.x(), pos.y() - 1));
    }

    private Position getGridPosFromEnemy(final Enemy enemy) {
        return new Position(
                (int) enemy.getX(),
                (int) enemy.getY());
    }

    private Position getGridPosFromHitBox(final Collidable entity) {
        return new Position(
                (int) entity.getHitBox().getCenter().x(),
                (int) entity.getHitBox().getCenter().y());
    }

}
