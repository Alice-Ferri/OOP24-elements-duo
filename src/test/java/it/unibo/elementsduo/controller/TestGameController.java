package it.unibo.elementsduo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.view.LevelPanel;

/**
 * Unit tests for {@link GameControllerImpl}.
 * 
 * Tests the behavior of game controller methods using mocks/fakes for dependencies.
 */
class GameControllerImplTest {

    private Level level;
    private LevelPanel view;
    private GameNavigation navigation;
    private ProgressionManagerImpl progressionManager;
    private GameControllerImpl controller;

    /**
     * Sets up mocks and the game controller before each test.
     */
    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        view = mock(LevelPanel.class);
        navigation = mock(GameNavigation.class);
        progressionManager = mock(ProgressionManagerImpl.class);

        JButton homeButton = new JButton();
        JButton levelSelectButton = new JButton();
        when(view.getHomeButton()).thenReturn(homeButton);
        when(view.getLevelSelectButton()).thenReturn(levelSelectButton);

        controller = new GameControllerImpl(level, navigation, view, progressionManager);
    }

    /**
     * Tests that getPanel() returns the correct panel.
     */
    @Test
    void testGetPanel() {
        assertEquals(view, controller.getPanel(), "getPanel should return the LevelPanel instance");
    }

    /**
     * Tests that activate installs input controller and attaches button listeners.
     */
    @Test
    void testActivateAddsListeners() {
        controller.activate();

        // Simulate button click → should call navigation methods
        view.getHomeButton().doClick();
        view.getLevelSelectButton().doClick();

        verify(navigation).goToMenu();
        verify(navigation).goToLevelSelection();
    }

    /**
     * Tests that deactivate stops game loop and removes listeners.
     */
    @Test
    void testDeactivateRemovesListeners() {
        controller.activate();
        controller.deactivate();

        // After deactivate, clicking buttons should not call navigation methods
        view.getHomeButton().doClick();
        view.getLevelSelectButton().doClick();

        verifyNoMoreInteractions(navigation);
    }

    /**
     * Tests that update does not throw exception even if level or entities are empty.
     */
    @Test
    void testUpdateWithEmptyLevel() {
        when(level.getAllPlayers()).thenReturn(java.util.Collections.emptyList());
        when(level.getAllProjectiles()).thenReturn(java.util.Collections.emptyList());
        when(level.getLivingEnemies()).thenReturn(java.util.Collections.emptyList());
        when(level.getAllInteractiveObstacles()).thenReturn(java.util.Collections.emptyList());
        when(level.getAllCollidables()).thenReturn(java.util.Collections.emptyList());

        assertDoesNotThrow(() -> controller.update(0.1), "update should run even if level is empty");
    }

    /**
     * Tests render just calls repaint on the panel.
     */
    @Test
    void testRender() {
        controller.render();
        verify(view).repaint();
    }
}
