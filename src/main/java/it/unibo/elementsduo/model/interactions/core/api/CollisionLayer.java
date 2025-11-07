package it.unibo.elementsduo.model.interactions.core.api;

import java.util.Set;

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
    private Set<CollisionLayer> defaultMask;

    CollisionLayer(final boolean defaultPhysicsResponse) {
        this.defaultPhysicsResponse = defaultPhysicsResponse;
    }

    static {
        PLAYER.defaultMask = Set.of(STATIC_OBSTACLE, PLATFORM, PUSHABLE);
        ENEMY.defaultMask = Set.of(STATIC_OBSTACLE, PLATFORM);
        PUSHABLE.defaultMask = Set.of(STATIC_OBSTACLE, PLATFORM, PUSHABLE, PLAYER, HAZARD);
        PLATFORM.defaultMask = Set.of(PLAYER, ENEMY, PUSHABLE);
        STATIC_OBSTACLE.defaultMask = Set.of(PLAYER, ENEMY, PUSHABLE, PROJECTILE);
        HAZARD.defaultMask = Set.of(PLAYER);
        PROJECTILE.defaultMask = Set.of(PLAYER, ENEMY, STATIC_OBSTACLE, PUSHABLE);
        GEM.defaultMask = Set.of(PLAYER);
        EXIT_ZONE.defaultMask = Set.of(PLAYER);
        BUTTON.defaultMask = Set.of(PLAYER);
        LEVER.defaultMask = Set.of(PLAYER);
        POWER_UP.defaultMask = Set.of(PLAYER);
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
     * @return a copy of the {@link Set} containing the default collision mask
     */
    public Set<CollisionLayer> getDefaultMask() {
        return Set.copyOf(this.defaultMask);
    }
}
