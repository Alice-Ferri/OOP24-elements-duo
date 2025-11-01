package it.unibo.elementsduo.model.progression;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link ProgressionState} class, focusing on the record-keeping logic
 * for best completion time and maximum gems collected per level.
 */
final class TestProgressionState {

    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final double TIME_RECORD = 50.0;
    private static final double TIME_WORSE = 60.0;
    private static final double TIME_BETTER = 45.0;
    private static final int GEMS_MAX = 100;
    private static final int GEMS_LESS = 50;
    private static final int GEMS_MORE = 120;
    private static final double DELTA = 0.001;

    private ProgressionState progressionState;

    @BeforeEach
    void setUp() {
        this.progressionState = new ProgressionState();
    }

    /**
     * Tests that the default constructor initializes the maps correctly.
     */
    @Test
    void testDefaultConstructorAndInitialMaps() {
        assertNotNull(progressionState.getLevelCompletionTimes());
        assertTrue(progressionState.getLevelCompletionTimes().isEmpty());
        assertNotNull(progressionState.getLevelGemsCollected());
        assertTrue(progressionState.getLevelGemsCollected().isEmpty());
    }
    

    /**
     * Tests the functionality of setting and getting the current level.
     */
    @Test
    void testSetAndGetCurrentLevel() {
        final int newLevel = 5;
        progressionState.setCurrentLevel(newLevel);
        assertEquals(newLevel, progressionState.getCurrentLevel());
    }


    /**
     * Tests that the time is saved when completing a level for the first time.
     */
    @Test
    void testTime_FirstCompletionSavesRecord() {
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_RECORD, GEMS_MAX);
        
        final Map<Integer, Double> times = progressionState.getLevelCompletionTimes();
        
        assertTrue(times.containsKey(LEVEL_ONE));
        assertEquals(TIME_RECORD, times.get(LEVEL_ONE), DELTA);
    }

    /**
     * Tests that a better (lower) time successfully replaces the existing record.
     */
    @Test
    void testTime_BeatExistingRecord() {
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_RECORD, GEMS_MAX);
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_BETTER, GEMS_LESS);
        
        final Map<Integer, Double> times = progressionState.getLevelCompletionTimes();
        
        assertEquals(TIME_BETTER, times.get(LEVEL_ONE), DELTA);
    }

    /**
     * Tests that a worse (higher) time does NOT replace the existing record.
     */
    @Test
    void testTime_DoNotBeatExistingRecord() {
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_RECORD, GEMS_MAX);
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_WORSE, GEMS_LESS);
        
        final Map<Integer, Double> times = progressionState.getLevelCompletionTimes();
        
        assertEquals(TIME_RECORD, times.get(LEVEL_ONE), DELTA);
    }
    
    /**
     * Tests that the gem count is saved when collecting for the first time.
     */
    @Test
    void testGems_FirstCompletionSavesRecord() {
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_RECORD, GEMS_MAX);
        
        final Map<Integer, Integer> gems = progressionState.getLevelGemsCollected();
        
        assertTrue(gems.containsKey(LEVEL_TWO));
        assertEquals(GEMS_MAX, gems.get(LEVEL_TWO));
    }
    
    /**
     * Tests that a higher gem count successfully replaces the existing maximum.
     */
    @Test
    void testGems_BeatExistingMax() {
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_RECORD, GEMS_MAX);
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_WORSE, GEMS_MORE);
        
        final Map<Integer, Integer> gems = progressionState.getLevelGemsCollected();
        
        assertEquals(GEMS_MORE, gems.get(LEVEL_TWO));
    }
    
    /**
     * Tests that a lower gem count does NOT replace the existing maximum.
     */
    @Test
    void testGems_DoNotBeatExistingMax() {
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_RECORD, GEMS_MAX);
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_BETTER, GEMS_LESS);
        
        final Map<Integer, Integer> gems = progressionState.getLevelGemsCollected();
        
        assertEquals(GEMS_MAX, gems.get(LEVEL_TWO));
    }
    
    /**
     * Tests scenarios where time and gem updates are mixed, ensuring independent logic.
     */
    @Test
    void testCombinedScenarios() {

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_WORSE, GEMS_MAX); 

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_BETTER, GEMS_MORE); 
        
        assertEquals(TIME_BETTER, progressionState.getLevelCompletionTimes().get(LEVEL_ONE), DELTA);
        assertEquals(GEMS_MORE, progressionState.getLevelGemsCollected().get(LEVEL_ONE));

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_WORSE, GEMS_LESS); 
        
        assertEquals(TIME_BETTER, progressionState.getLevelCompletionTimes().get(LEVEL_ONE), DELTA);
        assertEquals(GEMS_MORE, progressionState.getLevelGemsCollected().get(LEVEL_ONE));
    }
}