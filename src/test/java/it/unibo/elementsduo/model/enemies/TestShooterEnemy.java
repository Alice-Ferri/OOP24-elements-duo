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

/**
 * Test di unità per la classe ShooterEnemyImpl (massima semplificazione).
 */
final class ShooterEnemyTest {

    private static final double START_X = 10.0;
    private static final double START_Y = 5.0;
    private static final double SHOOTER_SPEED = 2.0;
    private static final double MAX_COOLDOWN = 3.0; 
    private static final double DELTA_TIME = 0.5;

    private ShooterEnemyImpl enemy;
    
    // Per far compilare il test senza ProjectilesImpl, il codice del test
    // usa la Reflection o un'implementazione anonima per creare l'oggetto.
    // **NOTA:** La classe originale ShooterEnemyImpl DEVE avere ProjectilesImpl nel suo classpath per compilare,
    // anche se qui non la definiamo, altrimenti il progetto fallirà.
    
    @BeforeEach
    void setUp() {
        this.enemy = new ShooterEnemyImpl(new Position(START_X, START_Y));
    }
    
    // --- Metodi Helper (Reflection) per manipolare 'shootCooldown' ---
    private void setShootCooldown(final double value) throws Exception {
        final Field cooldownField = ShooterEnemyImpl.class.getDeclaredField("shootCooldown");
        cooldownField.setAccessible(true);
        cooldownField.set(enemy, value);
    }
    
    private double getShootCooldown() throws Exception {
        final Field cooldownField = ShooterEnemyImpl.class.getDeclaredField("shootCooldown");
        cooldownField.setAccessible(true);
        return cooldownField.getDouble(enemy);
    }
    // -----------------------------------------------------------------

    @Test
    void testInitialStateAndMovement() {
        assertTrue(enemy.isAlive());
        
        // Testa il movimento e la velocità
        enemy.update(DELTA_TIME);
        final double expectedX = START_X + (1 * SHOOTER_SPEED * DELTA_TIME);
        assertEquals(expectedX, enemy.getX(), 0.001);
    }
    
    @Test
    void testAttackWhenCooldownIsZero() throws Exception {
        // Forza l'attacco
        setShootCooldown(0.0);
        Optional<Projectiles> result = enemy.attack();
        
        assertTrue(result.isPresent(), "Deve attaccare quando il cooldown è zero.");
        assertEquals(MAX_COOLDOWN, getShootCooldown(), 0.001, "Il cooldown deve essere resettato.");
    }
    
    @Test
    void testAttackWhenCooldownIsActive() throws Exception {
        // Non deve attaccare
        setShootCooldown(MAX_COOLDOWN / 2);
        Optional<Projectiles> result = enemy.attack();
        
        assertFalse(result.isPresent(), "Non deve attaccare quando il cooldown è attivo.");
        assertEquals(MAX_COOLDOWN / 2, getShootCooldown(), 0.001, "Il cooldown non deve cambiare.");
    }

    @Test
    void testUpdateManagesCooldownReset() throws Exception {
        // Esegue l'update quando il cooldown è scaduto per forzare l'attacco e il reset
        setShootCooldown(DELTA_TIME); // Impostiamo un valore che scadrà
        enemy.update(DELTA_TIME);
        
        // Risultato: reset a MAX_COOLDOWN (3.0) e decremento di DELTA_TIME (0.5)
        assertEquals(MAX_COOLDOWN - DELTA_TIME, getShootCooldown(), 0.001, 
                     "L'update deve resettare e decrementare il cooldown.");
    }
    
    @Test
    void testPhysicsCollisionInvertsDirection() {
        // Colpisce una parete laterale (normale con |x| > 0.5)
        enemy.correctPhysicsCollision(0.1, new Vector2D(-1.0, 0.0));
        assertEquals(-1, enemy.getDirection(), 0.001, "La direzione deve essere invertita dopo la collisione.");
    }

    @Test
    void testDie() {
        enemy.die();
        assertFalse(enemy.isAlive(), "Lo stato 'alive' deve essere false.");
    }
}