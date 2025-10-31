package it.unibo.elementsduo.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.controller.inputController.impl.InputControllerImpl;
import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Unit tests for {@link InputControllerImpl}.
 * 
 * Tests the behavior of keyboard input simulation, including movement and jump detection.
 */
final class TestInputController {

    private InputControllerImpl controller;

    /**
     * Sets up a fresh input controller before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new InputControllerImpl();
        controller.setEnabled(true);
    }

    /**
     * Tests that pressing the "move left" key is correctly detected for FIREBOY.
     */
    @Test
    void testMoveLeftPressed() {
        KeyEvent leftPress = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_A, 'A');
        controller.dispatchKeyEvent(leftPress);

        assertTrue(controller.isMoveLeftPressed(PlayerType.FIREBOY));
        assertFalse(controller.isMoveRightPressed(PlayerType.FIREBOY));
    }

    /**
     * Tests that pressing the "move right" key is correctly detected for FIREBOY.
     */
    @Test
    void testMoveRightPressed() {
        KeyEvent rightPress = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_D, 'D');
        controller.dispatchKeyEvent(rightPress);

        assertTrue(controller.isMoveRightPressed(PlayerType.FIREBOY));
        assertFalse(controller.isMoveLeftPressed(PlayerType.FIREBOY));
    }

    /**
     * Tests that the jump key is detected only once per key press.
     */
    @Test
    void testJumpPressedOnce() {
        KeyEvent jumpPress = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_W, 'W');

        assertFalse(controller.isJumpPressed(PlayerType.FIREBOY));

        controller.dispatchKeyEvent(jumpPress);
        assertTrue(controller.isJumpPressed(PlayerType.FIREBOY));

        assertFalse(controller.isJumpPressed(PlayerType.FIREBOY));

        KeyEvent jumpRelease = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
                0, KeyEvent.VK_W, 'W');
        controller.dispatchKeyEvent(jumpRelease);

        controller.dispatchKeyEvent(jumpPress);
        assertTrue(controller.isJumpPressed(PlayerType.FIREBOY));
    }

    /**
     * Tests enabling and disabling the input controller.
     */
    @Test
    void testEnableDisable() {
        controller.setEnabled(false);
        KeyEvent leftPress = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_A, 'A');
        controller.dispatchKeyEvent(leftPress);

        // When disabled, key press is ignored
        assertFalse(controller.isMoveLeftPressed(PlayerType.FIREBOY));

        controller.setEnabled(true);
        controller.dispatchKeyEvent(leftPress);
        assertTrue(controller.isMoveLeftPressed(PlayerType.FIREBOY));
    }

    /**
     * Tests that key release updates the controller state correctly.
     */
    @Test
    void testKeyReleased() {
        KeyEvent leftPress = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_A, 'A');
        KeyEvent leftRelease = new KeyEvent(new java.awt.Label(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
                0, KeyEvent.VK_A, 'A');

        controller.dispatchKeyEvent(leftPress);
        assertTrue(controller.isMoveLeftPressed(PlayerType.FIREBOY));

        controller.dispatchKeyEvent(leftRelease);
        assertFalse(controller.isMoveLeftPressed(PlayerType.FIREBOY));
    }
}
