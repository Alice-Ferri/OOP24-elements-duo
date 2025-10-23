package it.unibo.elementsduo.controller.api;

public interface MainController {
    
    void startApp();

    void showInitialMenu();

    void showLevelMenu();

    void startGame(final int levelNumber);
}
