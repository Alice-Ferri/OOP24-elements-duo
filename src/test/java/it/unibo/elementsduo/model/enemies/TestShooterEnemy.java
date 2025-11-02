package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;


/**
 * Unit tests for the {@link ShooterEnemyImpl} class, ensuring correct movement, 
 * state management, and projectile cooldown logic.
 */
final class TestShooterEnemy {

    private static final double START_X = 10.0;
    private static final double START_Y = 5.0;
    private static final double SHOOTER_SPEED = 2.0; 
    private static final double MAX_COOLDOWN = 3.0; 
    private static final double DELTA_TIME = 0.5;
    private static final double DELTA = 0.001;

    private ShooterEnemyImpl enemy;

    /**
     * Sets up the test environment before each test method runs.
     */
    @BeforeEach
    void setUp() {
        this.enemy = new ShooterEnemyImpl(new Position(START_X, START_Y));
        this.enemy.setMoveManager(new ManualMoveManagerStub());
    }
    
    
    /**
     * Sets the private shootCooldown field via reflection.
     * @param value the new cooldown value.
     * @throws Exception if reflection fails.
     */
    private void setShootCooldown(final double value) throws Exception {
        final Field cooldownField = ShooterEnemyImpl.class.getDeclaredField("shootCooldown");
        cooldownField.setAccessible(true);
        cooldownField.set(enemy, value);
    }
    
    /**
     * Gets the current value of the private shootCooldown field via reflection.
     * @return the current cooldown value.
     * @throws Exception if reflection fails.
     */
    private double getShootCooldown() throws Exception {
        final Field cooldownField = ShooterEnemyImpl.class.getDeclaredField("shootCooldown");
        cooldownField.setAccessible(true);
        return cooldownField.getDouble(enemy);
    }
    
    /**
     * Tests that the attack is successful and the cooldown is reset when the cooldown is zero.
     */
    @Test
    void testAttackWhenCooldownIsZero() throws Exception {
        setShootCooldown(0.0);
        Optional<Projectiles> result = enemy.attack();
        assertTrue(result.isPresent());
        assertEquals(MAX_COOLDOWN, getShootCooldown(), DELTA);
    }
    
    /**
     * Tests that the attack is unsuccessful when the cooldown is active.
     */
    @Test
    void testAttackWhenCooldownIsActive() throws Exception {
        setShootCooldown(MAX_COOLDOWN / 2);
        Optional<Projectiles> result = enemy.attack();
        assertFalse(result.isPresent());
        assertEquals(MAX_COOLDOWN / 2, getShootCooldown(), DELTA);
    }
    
    
    /**
     * Tests that a lateral physics collision correctly inverts the enemy's direction.
     */
    @Test
    void testPhysicsCollisionInvertsDirection() {
        enemy.correctPhysicsCollision(0.1, new Vector2D(-1.0, 0.0), null);
        assertEquals(-1, enemy.getDirection(), DELTA);
    }

    /**
     * Tests that the die method correctly sets the enemy's state to inactive.
     */
    @Test
    void testDie() {
        enemy.die();
        assertFalse(enemy.isAlive());
    }
    
    /**
     * Minimal stub implementation of EnemiesMoveManager to prevent NullPointerException
     * during the update cycle.
     */
    private static class ManualMoveManagerStub implements EnemiesMoveManager {
        @Override
        public void handleEdgeDetection(final Enemy enemy) {  }

        public void notifyWallHit(final Enemy enemy, final Vector2D normal) { }
    }
}