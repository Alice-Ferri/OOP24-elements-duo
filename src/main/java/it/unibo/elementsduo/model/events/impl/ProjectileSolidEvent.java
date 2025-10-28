package it.unibo.elementsduo.model.events.impl;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.events.api.Event;

public class ProjectileSolidEvent implements Event {
    private final Projectiles projectile;

    public ProjectileSolidEvent(Projectiles projectile) {
        this.projectile = projectile;
    }

    public Projectiles getProjectile() {
        return this.projectile;
    }

}
