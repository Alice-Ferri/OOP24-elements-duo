package it.unibo.elementsduo.model.collisions.core.api;

import java.util.EnumSet;

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

    public boolean hasPhysicsResponseByDefault() {
        return this.defaultPhysicsResponse;
    }

    public EnumSet<CollisionLayer> getDefaultMask() {
        return EnumSet.copyOf(this.defaultMask);
    }
}
