package it.unibo.elementsduo.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.controller.gamecontroller.impl.GameTimer;

/**
 * Unit tests for {@link GameTimer}.
 */
final class GameTimerTest {
    private static final double DELTA = 0.05; // 50ms tolerance

    /**
     * Tests that the timer counts elapsed time correctly.
     */
    @Test
    void testStartAndElapsedTime() throws InterruptedException {
        GameTimer timer = new GameTimer();

        timer.start();
        Thread.sleep(200); // sleep 200ms
        timer.stop();

        double elapsed = timer.getElapsedSeconds();

        assertTrue(elapsed >= 0.2, "Elapsed time should be at least 0.2 seconds");
        assertTrue(elapsed < 0.3, "Elapsed time should be less than 0.3 seconds");
    }

    /**
     * Tests that stopping the timer prevents further increments.
     */
    @Test
    void testStopPreventsFurtherCounting() throws InterruptedException {
        GameTimer timer = new GameTimer();

        timer.start();
        Thread.sleep(100);
        timer.stop();
        double firstElapsed = timer.getElapsedSeconds();

        Thread.sleep(100);
        double secondElapsed = timer.getElapsedSeconds();

        assertEquals(firstElapsed, secondElapsed, DELTA, "Elapsed time should not increase after stop");
    }

    /**
     * Tests that getElapsedSeconds returns zero before starting.
     */
    @Test
    void testInitialElapsedTime() {
        GameTimer timer = new GameTimer();
        assertEquals(0.0, timer.getElapsedSeconds(), DELTA, "Initial elapsed time should be zero");
    }
}
