package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

/**
 * Contains the enumeration of all possible static obstacle types
 * used in the game world.
 */
public final class ObstacleType {

        /**
         * Enumeration of obstacle types available in the game.
         * 
         * <p>
         * Each constant represents a different kind of static or interactive
         * element present in the level.
         */
        public enum Type {
                /** Represents a pool of water. */
                WATER_POOL,

                /** Represents a pool of lava. */
                LAVA_POOL,

                /** Represents a pool of green toxic liquid. */
                GREEN_POOL,

                /** Represents a wall obstacle. */
                WALL,

                /** Represents a floor obstacle. */
                FLOOR,

                /** Represents the spawn point for the water character. */
                WATER_SPAWN,

                /** Represents the spawn point for the fire character. */
                FIRE_SPAWN,

                /** Represents the exit zone for the fire character. */
                FIRE_EXIT,

                /** Represents the exit zone for the water character. */
                WATER_EXIT,

                /** Represents a collectible gem. */
                GEM
        }

        // Private constructor to prevent instantiation
        private ObstacleType() {
        }
}
