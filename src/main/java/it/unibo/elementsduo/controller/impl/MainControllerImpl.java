package it.unibo.elementsduo.controller.impl;

import it.unibo.elementsduo.controller.api.MainController;
import it.unibo.elementsduo.view.MainMenuPanel;
import it.unibo.elementsduo.view.MenuFrame;

public class MainControllerImpl implements MainController{

    private static final String MENU_VIEW = "menu";
    private final MenuFrame menuFrame;

    public MainControllerImpl(){
        menuFrame = new MenuFrame();
    }

    @Override
    public void startApp() {
        this.showInitialMenu();
        this.menuFrame.setVisible(true);
    }


    private void showInitialMenu() {

        final MainMenuPanel menuView = new MainMenuPanel();
        
        new MenuController(
            menuView,
            () -> this.showLevelMenu()
        );

        this.menuFrame.addView(menuView,MENU_VIEW);
        this.menuFrame.showView(MENU_VIEW);
    }

    private void showLevelMenu() {
        System.out.println("Prova");
    }

    private void startGame(int levelNumber) {
        
    }
    
}
