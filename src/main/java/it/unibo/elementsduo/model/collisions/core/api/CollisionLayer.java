package it.unibo.elementsduo.model.collisions.core.api;

import java.util.EnumSet;

/**
 * Represents the various collision layers used in the game world.
 * 
 * <p>
 * Each {@code CollisionLayer} defines a category of objects (e.g., player,
 * enemy, platform) and their default collision interactions with other layers.
 * </p>
 */
public enum CollisionLayer {
    PLAYER(true),
    ENEMY(true),
    PUSHABLE(true),
    PLATFORM(true),
    STATIC_OBSTACLE(true),
    HAZARD(true),
    PROJECTILE(true),
    GEM(false),
    EXIT_ZONE(false),
    BUTTON(false),
    LEVER(false),
    POWER_UP(false);

    private final boolean defaultPhysicsResponse;
    private EnumSet<CollisionLayer> defaultMask;

    CollisionLayer(final boolean defaultPhysicsResponse) {
        this.defaultPhysicsResponse = defaultPhysicsResponse;
    }

    static {
        PLAYER.defaultMask = EnumSet.of(STATIC_OBSTACLE, PLATFORM, PUSHABLE);
        ENEMY.defaultMask = EnumSet.of(STATIC_OBSTACLE, PLATFORM);
        PUSHABLE.defaultMask = EnumSet.of(STATIC_OBSTACLE, PLATFORM, PUSHABLE, PLAYER, HAZARD);
        PLATFORM.defaultMask = EnumSet.of(PLAYER, ENEMY, PUSHABLE);
        STATIC_OBSTACLE.defaultMask = EnumSet.of(PLAYER, ENEMY, PUSHABLE, PROJECTILE);
        HAZARD.defaultMask = EnumSet.of(PLAYER);
        PROJECTILE.defaultMask = EnumSet.of(PLAYER, ENEMY, STATIC_OBSTACLE, PUSHABLE);
        GEM.defaultMask = EnumSet.of(PLAYER);
        EXIT_ZONE.defaultMask = EnumSet.of(PLAYER);
        BUTTON.defaultMask = EnumSet.of(PLAYER, PUSHABLE);
        LEVER.defaultMask = EnumSet.of(PLAYER);
        POWER_UP.defaultMask = EnumSet.of(PLAYER);
    }

    /**
     * Returns whether objects in this layer have a physics response by default.
     *
     * @return {@code true} if objects in this layer have physics response,
     *         {@code false} otherwise
     */
    public boolean hasPhysicsResponseByDefault() {
        return this.defaultPhysicsResponse;
    }

    /**
     * Returns the default collision mask for this layer, indicating which layers
     * it can collide with.
     *
     * @return a copy of the {@link EnumSet} containing the default collision mask
     */
    public EnumSet<CollisionLayer> getDefaultMask() {
        return EnumSet.copyOf(this.defaultMask);
    }
}
