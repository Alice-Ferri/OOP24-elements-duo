package it.unibo.elementsduo.controller.maincontroller.impl;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.controller.maincontroller.api.GameNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.LevelSelectionNavigation;
import it.unibo.elementsduo.controller.maincontroller.api.MainController;
import it.unibo.elementsduo.controller.subcontroller.impl.HomeController;
import it.unibo.elementsduo.view.GameFrame;
import it.unibo.elementsduo.view.LevelPanel;
import it.unibo.elementsduo.view.LevelSelectionPanel;
import it.unibo.elementsduo.view.MenuPanel;

public class MainControllerImpl implements GameNavigation,HomeNavigation,LevelSelectionNavigation,MainController{

    private final GameFrame mainFrame;
    private Controller currentController;


    private static final String menuKey = "menu";
    private static final String gameKey = "game";
    private static final String levelKey = "level";

    public MainControllerImpl(){
        this.mainFrame = new GameFrame();
    }

    @Override
    public void startGame(int levelNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public void quitGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quitGame'");
    }

    @Override
    public void goToMenu() {
        //controlla se il gioco è in esecuzione e controlla

        final MenuPanel view = new MenuPanel();
        final Controller controller = new HomeController(view,this);
        controller.activate();
        mainFrame.addView(view, menuKey);
        mainFrame.showView("menu");
        currentController = controller;

    }

    @Override
    public void goToLevelSelection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'goToLevelSelection'");
    }

    @Override
    public void startApp() {
        this.goToMenu();
        this.mainFrame.setVisible(true);
    }

    private void checkController(){
        if(currentController!=null){
            currentController.deactivate();
        }
    }
    
}
