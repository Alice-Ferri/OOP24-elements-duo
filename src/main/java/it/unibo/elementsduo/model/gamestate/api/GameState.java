package it.unibo.elementsduo.model.gamestate.api;

/**
 * Represents the current state of the game.
 * Tracks win/loss conditions and player statistics.
 */
public interface GameState {

    /**
     * Checks if the game has ended (either win or loss).
     *
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * Checks if the game was won.
     * This is typically only valid if isGameOver() is true.
     *
     * @return true if the game was won, false if lost or still playing.
     */
    boolean didWin();

    /**
     * Gets the total number of gems collected in the level.
     *
     * @return the number of gems collected.
     */
    int getGemsCollected();

    /**
     * Gets the total number of enemies defeated in the level.
     *
     * @return the number of enemies defeated.
     */
    int getEnemiesDefeated();

}
