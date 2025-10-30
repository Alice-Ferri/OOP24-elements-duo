package it.unibo.elementsduo.controller.maincontroller.impl;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;

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

public class MainControllerImpl implements GameNavigation,HomeNavigation,LevelSelectionNavigation,MainController{

    private final GameFrame mainFrame;
    private Controller currentController;
    private final MapLoader mapLoader;
    private final MapValidator mapValidator;

    private int currentLevelNumber = -1;


    private static final String menuKey = "menu";
    private static final String gameKey = "game";
    private static final String levelKey = "level";

    public MainControllerImpl(){
        this.mainFrame = new GameFrame();
        this.mapValidator = new MapValidatorImpl();
        mapLoader= new MapLoader(new obstacleFactoryImpl(), new EnemyFactoryImpl(),new InteractiveObstacleFactoryImpl());
    }

    @Override
    public void startGame(int levelNumber) {
        
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
        final Controller gameController = new GameControllerImpl(level, this,panel);
        gameController.activate();

        final String currentGameKey = gameKey + currentLevelNumber;
        mainFrame.addView(gameController.getPanel(), currentGameKey);
        mainFrame.showView(currentGameKey);

        currentController = gameController;
        
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
        final Controller controller = new HomeController(view,this);
        controller.activate();

        mainFrame.addView(view, menuKey);
        mainFrame.showView(menuKey);

        currentController = controller;

    }

    @Override
    public void goToLevelSelection() {
        this.checkController();
        currentLevelNumber=-1;
        
        final LevelSelectionPanel view = new LevelSelectionPanel();
        final Controller controller = new LevelSelectionController(view,this);
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

    private void handleException(String error){
        JOptionPane.showMessageDialog(
                this.mainFrame,        
                error,                 
                "Errore Mappa",       
                JOptionPane.ERROR_MESSAGE 
        );
        this.goToLevelSelection();
    }
    
}
