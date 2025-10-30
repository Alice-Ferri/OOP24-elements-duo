package it.unibo.elementsduo.controller.maincontroller.impl;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;

import java.nio.file.Paths;
import java.util.Optional;

import it.unibo.elementsduo.controller.gamecontroller.impl.GameControllerImpl;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.LevelSelectionNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.MainController;
import it.unibo.elementsduo.controller.subcontroller.impl.HomeController;
import it.unibo.elementsduo.controller.subcontroller.impl.LevelSelectionController;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactoryImpl;
import it.unibo.elementsduo.view.GameFrame;
import it.unibo.elementsduo.view.LevelSelectionPanel;
import it.unibo.elementsduo.view.MenuPanel;
import it.unibo.elementsduo.datasave.SaveManager; 
import it.unibo.elementsduo.model.progression.ProgressionState; 
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;


public class MainControllerImpl implements GameNavigation,HomeNavigation,LevelSelectionNavigation,MainController{
    
    private final static int MAX_LEVELS=3;

    private final GameFrame mainFrame;
    private Controller currentController;
    private final MapLoader mapLoader;
    private int currentLevelNumber = -1;
    private final SaveManager saveManager;
    private ProgressionManagerImpl progressionManager;


    private static final String menuKey = "menu";
    private static final String gameKey = "game";
    private static final String levelKey = "level";
    private static final String SAVE_DIR = "save";

    public MainControllerImpl(){
        this.mainFrame = new GameFrame();
        mapLoader= new MapLoader(new obstacleFactoryImpl(), new EnemyFactoryImpl(),new InteractiveObstacleFactoryImpl());
        this.saveManager = new SaveManager(Paths.get(SAVE_DIR));
    }

    @Override
    public void startGame(int levelNumber) {
        currentLevelNumber = levelNumber;
        this.checkController();

        final Level level;
        level = this.mapLoader.loadLevel(levelNumber);
        final Controller gameController = new GameControllerImpl(level, this, this.progressionManager);
        gameController.activate();
        final String currentGameKey = gameKey + currentLevelNumber;
        mainFrame.addView(gameController.getPanel(), currentGameKey);
        this.progressionManager.getCurrentState().setCurrentLevel(levelNumber);
        mainFrame.showView(currentGameKey);
        currentController = gameController;
        
    }

   public void startNewGame() {

    final ProgressionState defaultState = new ProgressionState(1); 
    this.initializeProgressionAndStart(defaultState); 
    }
    

    public void loadSavedGame() {

    final ProgressionState defaultState = new ProgressionState(1);
    
    final ProgressionState state = saveManager.loadGame().orElseGet(() -> {
        System.err.println("Nessun salvataggio trovato o valido. Partita iniziata da capo.");
        return defaultState; 
    });

    this.initializeProgressionAndStart(state);
}
    

    @Override
    public void quitGame() {
        System.exit(0);
    }

    @Override
    public void goToMenu() {
        currentLevelNumber=-1;
        this.checkController();
        final MenuPanel view = new MenuPanel();
        final Controller controller = new HomeController(view, this, this::startNewGame, this::loadSavedGame);
        controller.activate();
        mainFrame.addView(view, menuKey);
        mainFrame.showView(menuKey);
        currentController = controller;

    }

    @Override
    public void goToLevelSelection() {
        currentLevelNumber=-1;
        this.checkController();
        final LevelSelectionPanel view = new LevelSelectionPanel();
        final Controller controller = new LevelSelectionController(view,this,this.progressionManager);
        controller.activate();
        mainFrame.addView(view, levelKey);
        mainFrame.showView(levelKey);
        currentController = controller;

    }

    @Override
    public void startApp() {
        this.goToMenu();
        this.mainFrame.setVisible(true);
    }

    private void checkController(){
        if(currentController!=null){
            currentController.deactivate();
            currentController=null;
        }
    }

    @Override
    public void restartCurrentLevel() {
        
        checkController();
        if (this.currentLevelNumber != -1) {
            this.startGame(this.currentLevelNumber);
        } else {
            this.goToMenu();
        }
    }


private void initializeProgressionAndStart(final ProgressionState state) {

    this.progressionManager = new ProgressionManagerImpl(saveManager, state);
    this.progressionManager.saveGame(); 
    final int nextLevelToPlay = state.getCurrentLevel();

    this.checkController();

    if (nextLevelToPlay > MAX_LEVELS) {
        this.goToLevelSelection();
    } else {
        this.startGame(nextLevelToPlay);
    }
}

    
}
