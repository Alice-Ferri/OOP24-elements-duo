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
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.map.mapvalidator.impl.MapValidatorImpl;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactoryImpl;
import it.unibo.elementsduo.view.GameFrame;
import it.unibo.elementsduo.view.LevelPanel;
import it.unibo.elementsduo.view.LevelSelectionPanel;
import it.unibo.elementsduo.view.MenuPanel;
import it.unibo.elementsduo.datasave.SaveManager; 
import it.unibo.elementsduo.model.progression.ProgressionState; 
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;


public class MainControllerImpl implements GameNavigation,HomeNavigation,LevelSelectionNavigation,MainController{
    

    private final GameFrame mainFrame;
    private Controller currentController;
    private final MapLoader mapLoader;
    private final MapValidator mapValidator;

    private int currentLevelNumber = -1;
    private final SaveManager saveManager;
    private ProgressionManagerImpl progressionManager;


    private static final String MENU_KEY = "menu";
    private static final String GAME_KEY = "game";
    private static final String levelkey = "level";
    private static final String SAVE_DIR = "save";

    public MainControllerImpl(){
        this.mainFrame = new GameFrame();
        this.mapValidator = new MapValidatorImpl();
        mapLoader= new MapLoader(new obstacleFactoryImpl(), new EnemyFactoryImpl(),new InteractiveObstacleFactoryImpl());
        this.saveManager = new SaveManager(Paths.get(SAVE_DIR));
    }

    @Override
    public void startGame(final int levelNumber) {

        this.checkController();
        currentLevelNumber = levelNumber;
        
        final Level level = this.mapLoader.loadLevel(currentLevelNumber);
        try{
            mapValidator.validate(level);
        }catch (InvalidMapException e){
            handleException(e.getMessage());
            return ;
        }

        final LevelPanel panel = new LevelPanel(level);
        final Controller gameController = new GameControllerImpl(level, this,panel,progressionManager);
        gameController.activate();

        final String currentkey = GAME_KEY + currentLevelNumber;
        mainFrame.addView(gameController.getPanel(), currentkey);
        this.progressionManager.getCurrentState().setCurrentLevel(levelNumber);
        mainFrame.showView(currentkey);

        currentController = gameController;
        
    }

    @Override
    public void startNewGame() {
        
        initProgressionManager(new ProgressionState());
        this.goToLevelSelection();
    }

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
    public void quitGame() {
        System.exit(0);
    }

    @Override
    public void goToMenu() {
        this.checkController();
        currentLevelNumber=-1;
        
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
        currentLevelNumber=-1;
        
        final LevelSelectionPanel view = new LevelSelectionPanel();
        final Controller controller = new LevelSelectionController(view,this,this.progressionManager);
        controller.activate();

        mainFrame.addView(view, levelkey);
        mainFrame.showView(levelkey);

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
            if (this.progressionManager == null) {
                 this.initProgressionManager(new ProgressionState());
            }
            this.startGame(this.currentLevelNumber); 
        } else {
            this.goToMenu();
        }
    }

    private void handleException(final String error){
        JOptionPane.showMessageDialog(
                this.mainFrame,        
                error,                 
                "Errore Mappa",       
                JOptionPane.ERROR_MESSAGE 
        );
        this.goToLevelSelection();
    }

    private void initProgressionManager(final ProgressionState state) {
        this.progressionManager = new ProgressionManagerImpl(saveManager, state);
        this.progressionManager.saveGame();
    }
}