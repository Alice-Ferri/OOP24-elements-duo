package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test di unità per la classe ClassicEnemiesImpl (semplificato, senza MoveManager).
 */
final class ClassicEnemyTest {

    private static final double START_X = 20.0;
    private static final double START_Y = 15.0;
    private static final double CLASSIC_SPEED = 0.8;
    private static final double DELTA_TIME = 0.5;

    private ClassicEnemiesImpl enemy;

    @BeforeEach
    void setUp() {
        // La classe ClassicEnemiesImpl deve essere testata da sola.
        // La chiamata a setMoveManager è omessa.
        this.enemy = new ClassicEnemiesImpl(new Position(START_X, START_Y));
    }

    @Test
    void testInitialStateAndPosition() {
        assertTrue(enemy.isAlive());
        assertEquals(START_X, enemy.getX(), 0.001);
        assertEquals(START_Y, enemy.getY(), 0.001);
        assertEquals(1, enemy.getDirection(), 0.001);
    }

    @Test
    void testMovementAndSpeed() {
        // NOTA: Eseguire update() qui causerebbe un NPE se moveManager non fosse null-safe.
        // Assumiamo di testare solo il calcolo del movimento.
        
        enemy.update(DELTA_TIME); 
        
        final double expectedX = START_X + (1 * CLASSIC_SPEED * DELTA_TIME);
        assertEquals(expectedX, enemy.getX(), 0.001);
    }
    
    @Test
    void testAttackAlwaysEmpty() {
        Optional<Projectiles> result = enemy.attack();
        assertFalse(result.isPresent());
    }

    @Test
    void testDirectionReversal() {
        enemy.setDirection();
        assertEquals(-1, enemy.getDirection(), 0.001);
    }
    
    @Test
    void testPhysicsCollisionInvertsDirection() {
        // Collisione laterale (forza l'inversione di direzione)
        enemy.correctPhysicsCollision(0.1, new Vector2D(-1.0, 0.0));
        assertEquals(-1, enemy.getDirection(), 0.001);
    }
    
    @Test
    void testPhysicsCollisionCorrectsPosition() {
        final double penetration = 0.5;
        final Vector2D normal = new Vector2D(0.0, 1.0); 
        final double expectedY = START_Y + (0.8 * (penetration - 0.001));

        enemy.correctPhysicsCollision(penetration, normal);
        
        assertEquals(expectedY, enemy.getY(), 0.001);
        assertEquals(1, enemy.getDirection(), 0.001); // Non deve cambiare direzione verticale
    }

    @Test
    void testDie() {
        enemy.die();
        assertFalse(enemy.isAlive());
    }
}