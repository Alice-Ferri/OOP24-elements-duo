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
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.level.impl.LevelImpl;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.map.mapvalidator.impl.MapValidatorImpl;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactoryImpl;
import it.unibo.elementsduo.view.GameFrame;

import it.unibo.elementsduo.view.LevelPanel;
import it.unibo.elementsduo.view.GuidePanel;
import it.unibo.elementsduo.view.LevelSelectionPanel;
import it.unibo.elementsduo.view.MenuPanel;
import it.unibo.elementsduo.datasave.SaveManager;
import it.unibo.elementsduo.model.progression.ProgressionState;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;

/**
 * The main controller for the application.
 * It manages the navigation between different sub-controllers (menu, level selection, game)
 * and holds the main game frame.
 */
public final class MainControllerImpl implements GameNavigation, HomeNavigation, LevelSelectionNavigation, MainController {

    private static final String MENU_KEY = "menu";
    private static final String GAME_KEY = "game";
    private static final String LEVEL_KEY = "level";
    private static final String SAVE_DIR = "save";

    private final GameFrame mainFrame;
    private Controller currentController;
    private final MapLoader mapLoader;
    private final MapValidator mapValidator;

    private int currentLevelNumber = -1;
    private final SaveManager saveManager;
    private ProgressionManagerImpl progressionManager;

    /**
     * Constructs the MainController, initializing the main frame and factories.
     */
    public MainControllerImpl() {
        this.mainFrame = new GameFrame();
        this.mapValidator = new MapValidatorImpl();
        this.mapLoader = new MapLoader(new obstacleFactoryImpl(), new EnemyFactoryImpl(), new InteractiveObstacleFactoryImpl());
        this.saveManager = new SaveManager(Paths.get(SAVE_DIR));
    }

    @Override
    public void startGame(final int levelNumber) {
        this.checkController();
        currentLevelNumber = levelNumber;

        final Level level = new LevelImpl(this.mapLoader.loadLevel(currentLevelNumber));
        try {
            mapValidator.validate(level);
        } catch (final InvalidMapException e) {
            handleException(e.getMessage());
            return;
        }

        final LevelPanel panel = new LevelPanel(level);
        final Controller gameController = new GameControllerImpl(level, this, panel, progressionManager);
        gameController.activate();

        final String currentkey = GAME_KEY + currentLevelNumber;
        mainFrame.addView(panel, currentkey);
        this.progressionManager.getCurrentState().setCurrentLevel(levelNumber);
        mainFrame.showView(currentkey);

        currentController = gameController;
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
        GuidePanel guidePanel = new GuidePanel(this::goToMenu);
        final String guideKey = "GUIDE";
        mainFrame.addView(guidePanel, guideKey);
        mainFrame.showView(guideKey);
    }

    @Override
    public void quitGame() {
        System.exit(0);
    }

    @Override
    public void goToMenu() {
        this.checkController();
        currentLevelNumber = -1;

        final MenuPanel view = new MenuPanel();
        final Controller controller = new HomeController(view, this);

        controller.activate();

        mainFrame.addView(view, MENU_KEY);
        mainFrame.showView(MENU_KEY);

        currentController = controller;
    }

    @Override
    public void goToLevelSelection() {
        this.checkController();
        currentLevelNumber = -1;

        final LevelSelectionPanel view = new LevelSelectionPanel();
        final Controller controller = new LevelSelectionController(view, this, this.progressionManager);
        controller.activate();

        mainFrame.addView(view, LEVEL_KEY);
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

    private void handleException(final String error) {
        JOptionPane.showMessageDialog(
                this.mainFrame,
                error,
                "Map Error",
                JOptionPane.ERROR_MESSAGE
        );
        this.goToLevelSelection();
    }

    private void initProgressionManager(final ProgressionState state) {
        this.progressionManager = new ProgressionManagerImpl(saveManager, state);
        this.progressionManager.saveGame();
    }
}
