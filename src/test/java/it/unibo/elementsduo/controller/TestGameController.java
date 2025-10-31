package it.unibo.elementsduo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.view.LevelPanel;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.testDoubles.DoubleInputController;
import it.unibo.elementsduo.testDoubles.TestDoubleLevel;
import it.unibo.elementsduo.testDoubles.TestDoubleLevelPanel;
import it.unibo.elementsduo.testDoubles.TestDoubleNavigation;
import it.unibo.elementsduo.testDoubles.TestDoubleProgressionManager;

/**
 * Unit tests for {@link GameControllerImpl}.
 */
class GameControllerImplTest
{
    private Level level;
    private LevelPanel view;
    private GameNavigation navigation;
    private ProgressionManagerImpl progressionManager;
    private GameControllerImpl controller;
    private InputController inputController;

    /**
     * Sets up test doubles and the game controller before each test.
     */
    @BeforeEach
    void setUp()
    {
        level = new DoubleLevel();
        view = new DoubleLevelPanel();
        navigation = new DoubleNavigation();
        progressionManager = new DoubleProgressionManager();
        inputController = new DoubleInputController();

        controller = new GameControllerImpl(level, navigation, view, progressionManager);
    }

    /**
     * Tests that getPanel() returns the correct panel.
     */
    @Test
    void testGetPanel()
    {
        assertEquals(view, controller.getPanel(), "getPanel should return the LevelPanel instance");
    }

    /**
     * Tests that activate installs input controller and attaches button listeners.
     */
    @Test
    void testActivateAddsListeners()
    {
        controller.activate();

        view.getHomeButton().doClick();
        view.getLevelSelectButton().doClick();

        DoubleNavigation nav = (DoubleNavigation) navigation;
        assertEquals(true, nav.goToMenuCalled, "goToMenu should be called on home button click");
        assertEquals(true, nav.goToLevelSelectionCalled, "goToLevelSelection should be called on level select button click");
    }

    /**
     * Tests that deactivate stops game loop and removes listeners.
     */
    @Test
    void testDeactivateRemovesListeners()
    {
        controller.activate();
        controller.deactivate();

        view.getHomeButton().doClick();
        view.getLevelSelectButton().doClick();

        DoubleNavigation nav = (DoubleNavigation) navigation;
        assertEquals(false, nav.goToMenuCalled, "goToMenu should not be called after deactivate");
        assertEquals(false, nav.goToLevelSelectionCalled, "goToLevelSelection should not be called after deactivate");
    }

    /**
     * Tests update does not throw exception even if level or entities are empty.
     */
    @Test
    void testUpdateWithEmptyLevel()
    {
        assertDoesNotThrow(() -> controller.update(0.1), "update should run even if level is empty");
    }

    /**
     * Tests render just calls repaint on the panel.
     */
    @Test
    void testRender()
    {
        controller.render();
        DoubleLevelPanel panel = (DoubleLevelPanel) view;
        assertEquals(true, panel.repaintCalled, "repaint should be called");
    }
}
