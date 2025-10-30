package it.unibo.elementsduo.model.gamestate.api;

public interface GameState {
    
    boolean isGameOver();

    boolean didWin();
    
    int getGemsCollected();

    int getEnemiesDefeated();

}
