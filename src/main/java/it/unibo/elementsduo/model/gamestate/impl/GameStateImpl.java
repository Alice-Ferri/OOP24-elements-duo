package it.unibo.elementsduo.model.gamestate.impl;

import java.util.Objects;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.collisions.events.api.EventListener;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.FireExitEvent;
import it.unibo.elementsduo.model.collisions.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.WaterExitEvent; 
import it.unibo.elementsduo.model.gamestate.api.GameState;

public class GameStateImpl implements EventListener, GameState {

    private boolean gameOver;
    private boolean won;
    private int gemsCollected;
    private int deadEnemies;
    private boolean fireboyReachedExit; 
    private boolean watergirlReachedExit; 

    public GameStateImpl(final EventManager eventManager) {
        Objects.requireNonNull(eventManager);

        eventManager.subscribe(PlayerDiedEvent.class, this);
        eventManager.subscribe(GemCollectedEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
        eventManager.subscribe(FireExitEvent.class, this);
        eventManager.subscribe(WaterExitEvent.class, this);
    }

    @Override
    public void onEvent(final Event event) {
        if (gameOver) {
            return;
        }

        if (event instanceof PlayerDiedEvent) {
            handlePlayerDied();
        } else if (event instanceof GemCollectedEvent) {
            handleGemCollected();
        } else if (event instanceof EnemyDiedEvent) {
            handleEnemyDied();
        } else if (event instanceof FireExitEvent) { 
            handleFireReachedExit();
            checkGameWinCondition();
        } else if (event instanceof WaterExitEvent) {
            handleWaterReachedExit();
            checkGameWinCondition();
        }

    }


    private void handleEnemyDied() {
        this.deadEnemies++;
    }

    private void handleGemCollected() {
        this.gemsCollected++;
    }

    private void handlePlayerDied() {
        endGame(false);
    }

    private void handleFireReachedExit() {
        this.fireboyReachedExit = true;
    }

    private void handleWaterReachedExit() {
        this.watergirlReachedExit = true;
    }

    private void checkGameWinCondition() {
        if (!gameOver && this.fireboyReachedExit && this.watergirlReachedExit) {
            endGame(true);
        }
    }

    private void endGame(final boolean won) {
        if (!gameOver) {
            this.gameOver = true;
            this.won = won;
        }
    }

    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    @Override
    public boolean didWin() {
        return this.won;
    }

    @Override
    public int getGemsCollected() {
        return this.gemsCollected;
    }

    @Override
    public int getEnemiesDefeated() {
        return this.deadEnemies;
    }

    public boolean hasFireboyReachedExit() {
        return this.fireboyReachedExit;
    }

    public boolean hasWatergirlReachedExit() {
        return this.watergirlReachedExit;
    }
}