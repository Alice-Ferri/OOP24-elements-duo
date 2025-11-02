package it.unibo.elementsduo.model.progressionstate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.elementsduo.model.progression.ProgressionState;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link ProgressionState} class, focusing on the record-keeping logic
 * for best completion time and mission completion status per level.
 */
final class TestProgressionState {

    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final double TIME_RECORD = 50.0;
    private static final double TIME_WORSE = 60.0;
    private static final double TIME_BETTER = 45.0;

    // Le costanti GEMS sono state sostituite da costanti MISSION
    private static final boolean MISSION_COMPLETE = true;
    private static final boolean MISSION_FAILED = false;
    private static final String MISSION_COMPLETE_STRING = "* Sfida Completata!";
    // RIMOSSO: private static final String MISSION_DEFAULT_STRING = "X Sfida non superata!";


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
        assertNotNull(progressionState.getLevelMissionCompleted());
        assertTrue(progressionState.getLevelMissionCompleted().isEmpty());
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
    void testTimeFirstCompletionSavesRecord() { // NOME CORRETTO

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_RECORD, MISSION_COMPLETE);
        
        final Map<Integer, Double> times = progressionState.getLevelCompletionTimes();
        
        assertTrue(times.containsKey(LEVEL_ONE));
        assertEquals(TIME_RECORD, times.get(LEVEL_ONE), DELTA);
    }

    /**
     * Tests that a better (lower) time successfully replaces the existing record.
     */
    @Test
    void testTimeBeatExistingRecord() { // NOME CORRETTO
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_RECORD, MISSION_COMPLETE);
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_BETTER, MISSION_FAILED);
        
        final Map<Integer, Double> times = progressionState.getLevelCompletionTimes();
        
        assertEquals(TIME_BETTER, times.get(LEVEL_ONE), DELTA);
    }

    /**
     * Tests that a worse (higher) time does NOT replace the existing record.
     */
    @Test
    void testTimeDoNotBeatExistingRecord() { // NOME CORRETTO
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_RECORD, MISSION_COMPLETE);
        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_WORSE, MISSION_FAILED);
        
        final Map<Integer, Double> times = progressionState.getLevelCompletionTimes();
        
        assertEquals(TIME_RECORD, times.get(LEVEL_ONE), DELTA);
    }
    
    /**
     * Tests that the mission status is saved when completing for the first time with success.
     */
    @Test
    void testMissionCompletedSavesRecord() { // NOME CORRETTO
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_RECORD, MISSION_COMPLETE);
        
        final Map<Integer, String> missions = progressionState.getLevelMissionCompleted();
        
        assertTrue(missions.containsKey(LEVEL_TWO));
        assertEquals(MISSION_COMPLETE_STRING, missions.get(LEVEL_TWO));
    }
    
    /**
     * Tests that a failed mission does NOT overwrite a previously completed mission.
     */
    @Test
    void testMissionFailedDoesNotOverwriteCompleted() { // NOME CORRETTO

        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_RECORD, MISSION_COMPLETE);
        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_WORSE, MISSION_FAILED);
        
        final Map<Integer, String> missions = progressionState.getLevelMissionCompleted();
        
        assertEquals(MISSION_COMPLETE_STRING, missions.get(LEVEL_TWO));
    }
    
    /**
     * Tests that a completed mission successfully overwrites a failed (default) state.
     */
    @Test
    void testMissionCompletedOverwritesFailed() { // NOME CORRETTO

        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_RECORD, MISSION_FAILED);
        
        final Map<Integer, String> missions = progressionState.getLevelMissionCompleted();
        assertFalse(missions.containsKey(LEVEL_TWO)); 

        progressionState.addLevelCompletionTime(LEVEL_TWO, TIME_BETTER, MISSION_COMPLETE);

        assertTrue(missions.containsKey(LEVEL_TWO));
        assertEquals(MISSION_COMPLETE_STRING, missions.get(LEVEL_TWO));
    }
    
    /**
     * Tests scenarios where time and mission updates are mixed, ensuring independent logic.
     */
    @Test
    void testCombinedScenarios() {

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_WORSE, MISSION_FAILED); 
        assertEquals(TIME_WORSE, progressionState.getLevelCompletionTimes().get(LEVEL_ONE), DELTA);
        assertFalse(progressionState.getLevelMissionCompleted().containsKey(LEVEL_ONE));

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_BETTER, MISSION_COMPLETE); 
        assertEquals(TIME_BETTER, progressionState.getLevelCompletionTimes().get(LEVEL_ONE), DELTA); 
        assertEquals(MISSION_COMPLETE_STRING, progressionState.getLevelMissionCompleted().get(LEVEL_ONE));

        progressionState.addLevelCompletionTime(LEVEL_ONE, TIME_WORSE, MISSION_FAILED); 
        assertEquals(TIME_BETTER, progressionState.getLevelCompletionTimes().get(LEVEL_ONE), DELTA); 
        assertEquals(MISSION_COMPLETE_STRING, progressionState.getLevelMissionCompleted().get(LEVEL_ONE)); 
    }
}