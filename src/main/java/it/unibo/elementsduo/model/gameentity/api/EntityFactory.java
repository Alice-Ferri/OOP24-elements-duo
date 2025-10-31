package it.unibo.elementsduo.model.gameentity.api;

import it.unibo.elementsduo.resources.Position;
import java.util.Set;

/**
 * A functional interface for creating game entities based on a map symbol.
 */
@FunctionalInterface
public interface EntityFactory {
    /**
     * Creates a set of game entities corresponding to a given symbol and position.
     * It returns a Set because one symbol (like a spawn point) might create
     * multiple entities.
     *
     * @param symbol The character symbol from the map file.
     * @param pos    The grid position for the new entity.
     * @return A set of {@link GameEntity} objects, or an empty set if the symbol is not recognized.
     */
    Set<GameEntity> createEntities(char symbol, Position pos);
}
