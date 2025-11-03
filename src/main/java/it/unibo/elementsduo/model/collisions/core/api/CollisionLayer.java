package it.unibo.elementsduo.model.collisions.core.api;

import java.util.EnumSet;

/**
 * Represents the various collision layers used to categorize objects
 * in the game world for collision detection and physics interactions.
 * <p>
 * Each {@code CollisionLayer} defines:
 * <ul>
 * <li>whether objects in the layer have a default physical response,</li>
 * <li>and a default collision mask specifying which layers they interact
 * with.</li>
 * </ul>
 * </p>
 */
public enum CollisionLayer {

    /** The layer representing the player entity. */
    PLAYER(true),

    /** The layer representing enemy entities. */
    ENEMY(true),

    /** The layer for movable pushable boxes. */
    PUSHABLE(true),

    /** The layer for moving platforms. */
    PLATFORM(true),

    /** The layer for static obstacles such as walls or terrain. */
    STATIC_OBSTACLE(true),

    /** The layer for hazards such as spikes or traps. */
    HAZARD(true),

    /** The layer for projectiles like bullets or fireballs. */
    PROJECTILE(true),

    /** The layer for collectible gems or items. */
    GEM(false),

    /** The layer for exit zones or level-end triggers. */
    EXIT_ZONE(false),

    /** The layer for buttons that can be pressed. */
    BUTTON(false),

    /** The layer for levers that can be toggled. */
    LEVER(false);

    /** Whether this layer responds to physics interactions by default. */
    private final boolean defaultPhysicsResponse;

    /** The default set of collision layers this layer interacts with. */
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
    }

    /**
     * Checks whether this layer has a default physics response.
     *
     * @return {@code true} if this layer interacts physically by default,
     *         {@code false} otherwise
     */
    public boolean hasPhysicsResponseByDefault() {
        return this.defaultPhysicsResponse;
    }

    /**
     * Returns a copy of the default collision mask for this layer.
     * <p>
     * The collision mask defines which layers this layer can collide with by
     * default.
     * </p>
     *
     * @return an {@link EnumSet} representing the default collision mask
     */
    public EnumSet<CollisionLayer> getDefaultMask() {
        return EnumSet.copyOf(this.defaultMask);
    }
}
