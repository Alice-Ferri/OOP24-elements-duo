package it.unibo.elementsduo.model.gameentity.api;

/**
 * Defines the broad categories of game entities.
 * Used by the EntityFactory to determine how to create an entity from a map symbol.
 */
public enum EntityType {
    STATIC_OBSTACLE,
    ENEMY,
    SPAWN_POINT,
    LEVER,
    PUSH_BOX,
    MOVING_PLATFORM,
    GEM,
    BUTTON
}
