package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Projectiles;

public class ProjectileSolidEvent implements Event {
    private final Projectiles projectile;

    public ProjectileSolidEvent(final Projectiles projectile) {
        this.projectile = projectile;
    }

    public Projectiles getProjectile() {
        return this.projectile;
    }

}
