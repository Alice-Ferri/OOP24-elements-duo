package it.unibo.elementsduo.controller.maincontroller.impl;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

import it.unibo.elementsduo.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.LevelSelectionNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.MainController;
import it.unibo.elementsduo.controller.subcontroller.impl.HomeController;
import it.unibo.elementsduo.controller.subcontroller.impl.LevelSelectionController;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.impl.MapValidatorImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.ObstacleFactoryImpl;
import it.unibo.elementsduo.model.powerups.impl.PowerUpFactoryImpl;
import it.unibo.elementsduo.view.GameFrame;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;

import it.unibo.elementsduo.view.GuidePanel;
import it.unibo.elementsduo.datasave.SaveManager;
import it.unibo.elementsduo.model.progression.ProgressionState;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;

/**
 * The main controller for the application.
 * It manages the navigation between different sub-controllers (menu, level
 * selection, game)
 * and holds the main game frame.
 */
public final class MainControllerImpl
        implements GameNavigation, HomeNavigation, LevelSelectionNavigation, MainController {

    private static final String MENU_KEY = "menu";
    private static final String GAME_KEY = "game";
    private static final String LEVEL_KEY = "level";
    private static final String SAVE_DIR = "save";

    private final GameFrame mainFrame;
    private final SaveManager saveManager;

    private int currentLevelNumber = -1;
    private ProgressionManagerImpl progressionManager;
    private Controller currentController;

    /**
     * Constructs the MainController, initializing the main frame and factories.
     */
    public MainControllerImpl() {
        this.mainFrame = new GameFrame();
        this.saveManager = new SaveManager(Paths.get(SAVE_DIR));
    }

    @Override
    public void startGame(final int levelNumber) {
        this.checkController();
        currentLevelNumber = levelNumber;
        try {
            final Controller gameController = new GameControllerImpl(currentLevelNumber, this, progressionManager);
            gameController.activate();

            final String currentkey = GAME_KEY + currentLevelNumber;
            mainFrame.addView(gameController.getPanel(), currentkey);
            this.progressionManager.setCurrentLevel(levelNumber);
            mainFrame.showView(currentkey);

            currentController = gameController;
        } catch (InvalidMapException e) {
            this.handleException(e.getMessage());
        }

    }

    /**
     * Starts a new game by initializing a new progression state
     * and navigating to the level selection screen.
     */
    @Override
    public void startNewGame() {
        initProgressionManager(new ProgressionState());
        this.goToLevelSelection();
    }

    /**
     * Loads a saved game. If no save is found, a new game is started.
     * Navigates to the level selection screen.
     */
    @Override
    public void loadSavedGame() {
        final ProgressionState defaultState = new ProgressionState();

        final ProgressionState state = saveManager.loadGame().orElseGet(() -> {
            return defaultState;
        });

        initProgressionManager(state);
        this.goToLevelSelection();
    }

    @Override
    public void gameGuide() {
        this.checkController();
        final GuidePanel guidePanel = new GuidePanel(this::goToMenu);
        final String guideKey = "GUIDE";
        mainFrame.addView(guidePanel, guideKey);
        mainFrame.showView(guideKey);
    }

    @Override
    public void quitGame() {
        this.checkController();
        this.mainFrame.dispose();
    }

    @Override
    public void goToMenu() {
        this.checkController();
        currentLevelNumber = -1;

        final Controller controller = new HomeController(this);

        controller.activate();

        mainFrame.addView(controller.getPanel(), MENU_KEY);
        mainFrame.showView(MENU_KEY);

        currentController = controller;
    }

    @Override
    public void goToLevelSelection() {
        this.checkController();
        currentLevelNumber = -1;

        final Controller controller = new LevelSelectionController(this, this.progressionManager);
        controller.activate();

        mainFrame.addView(controller.getPanel(), LEVEL_KEY);
        mainFrame.showView(LEVEL_KEY);

        currentController = controller;
    }

    @Override
    public void startApp() {
        this.goToMenu();
        this.mainFrame.setVisible(true);
    }

    private void checkController() {
        if (currentController != null) {
            currentController.deactivate();
            currentController = null;
        }
    }

    @Override
    public void restartCurrentLevel() {
        checkController();

        if (this.currentLevelNumber != -1) {
            if (this.progressionManager == null) {
                this.initProgressionManager(new ProgressionState());
            }
            this.startGame(this.currentLevelNumber);
        } else {
            this.goToMenu();
        }
    }

    public void handleException(final String error) {
        this.goToLevelSelection();
        JOptionPane.showMessageDialog(
                this.mainFrame,
                error,
                "Map Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void initProgressionManager(final ProgressionState state) {
        this.progressionManager = new ProgressionManagerImpl(saveManager, state);
        this.progressionManager.saveGame();
    }
}
