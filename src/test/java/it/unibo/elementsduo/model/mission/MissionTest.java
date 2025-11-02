package it.unibo.elementsduo.model.mission;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.mission.impl.EnemyObjective;
import it.unibo.elementsduo.model.mission.impl.GemObjective;
import it.unibo.elementsduo.model.mission.impl.Mission;
import it.unibo.elementsduo.model.mission.impl.TimeLimitObjective;

/**
 * Test for the Mission class.
 * Uses a fake gamestate to control the outcomes.
 */
final class TestMission {

    /**
     * A fake gamestate that can be controlled from the outside.
     * It only implements the methods strictly necessary for this test.
     */
    private static class FakeGameState implements GameState {
        private int gemsCollected = 0;
        private int enemiesDefeated = 0;

        public void setGemsCollected(final int count) { this.gemsCollected = count; }
        public void setEnemiesDefeated(final int count) { this.enemiesDefeated = count; }
        @Override public int getGemsCollected() { return this.gemsCollected; }
        @Override public int getEnemiesDefeated() { return this.enemiesDefeated; }
        @Override public boolean isGameOver() { throw new UnsupportedOperationException(); }
        @Override
        public boolean didWin() {return true;}
    }

    private FakeGameState stubGameState;
    private Mission mission;

    private static final int TOTAL_GEMS = 10;
    private static final int TOTAL_ENEMIES = 5;
    private static final double TIME_LIMIT = 60.0;

    @BeforeEach
    void setUp() {
        this.stubGameState = new FakeGameState();

        this.mission = new Mission("Test Mission");
        this.mission.add(new GemObjective(TOTAL_GEMS));
        this.mission.add(new EnemyObjective(TOTAL_ENEMIES));
        this.mission.add(new TimeLimitObjective(TIME_LIMIT));
    }

    /**
     * Tests the success: all objectives are completed.
     */
    @Test
    void testMissionSuccessWhenAllObjectivesPass() {
        stubGameState.setGemsCollected(TOTAL_GEMS);
        stubGameState.setEnemiesDefeated(TOTAL_ENEMIES);
        final double finalTime = TIME_LIMIT - 5.0;
        mission.checkCompletion(stubGameState, finalTime);
        assertTrue(mission.isComplete(), "Mission should be complete if all children pass");
    }

    /**
     * Tests the failure: not all games are collected.
     */
    @Test
    void testMissionFailsWhenGemsAreMissing() {
        stubGameState.setGemsCollected(TOTAL_GEMS - 1);
        stubGameState.setEnemiesDefeated(TOTAL_ENEMIES);
        final double finalTime = TIME_LIMIT - 5.0;
        mission.checkCompletion(stubGameState, finalTime);
        assertFalse(mission.isComplete(), "Mission should fail if gems are missing");
    }

    /**
     * Tests the failure: not all enemies are dead.
     */
    @Test
    void testMissionFailsWhenEnemiesRemain() {
        stubGameState.setGemsCollected(TOTAL_GEMS);
        stubGameState.setEnemiesDefeated(TOTAL_ENEMIES - 1);
        final double finalTime = TIME_LIMIT - 5.0;
        mission.checkCompletion(stubGameState, finalTime);
        assertFalse(mission.isComplete(), "Mission should fail if enemies remain");
    }

    /**
     * Tests the timer exceed.
     */
    @Test
    void testMissionFailsWhenTimeIsOver() {
        stubGameState.setGemsCollected(TOTAL_GEMS);
        stubGameState.setEnemiesDefeated(TOTAL_ENEMIES);
        final double finalTime = TIME_LIMIT + 5.0;
        mission.checkCompletion(stubGameState, finalTime);
        assertFalse(mission.isComplete(), "Mission should fail if the time limit is exceeded");
    }
}