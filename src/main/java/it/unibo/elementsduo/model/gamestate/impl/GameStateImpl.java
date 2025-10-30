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

    private boolean gameOver = false;
    private boolean won = false;
    private int gemsCollected = 0;
    private int deadEnemies = 0;
    private boolean fireboyReachedExit = false; 
    private boolean watergirlReachedExit = false; 

    public GameStateImpl(final EventManager eventManager) {
        Objects.requireNonNull(eventManager);

        eventManager.subscribe(PlayerDiedEvent.class, this);
        eventManager.subscribe(GemCollectedEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
        eventManager.subscribe(FireExitEvent.class, this);
        eventManager.subscribe(WaterExitEvent.class, this);
    }

    @Override
    public void onEvent(Event event) {
        if (gameOver) {
            return;
        }

        if (event instanceof PlayerDiedEvent e) {
            handlePlayerDied(e);
        } else if (event instanceof GemCollectedEvent e) {
            handleGemCollected(e);
        } else if (event instanceof EnemyDiedEvent e) {
            handleEnemyDied(e);
        } else if (event instanceof FireExitEvent e) { 
            handleFireReachedExit(e);
            checkGameWinCondition();
        } else if (event instanceof WaterExitEvent e) {
            handleWaterReachedExit(e);
            checkGameWinCondition();
        }

    }


    private void handleEnemyDied(EnemyDiedEvent e) {
        this.deadEnemies++;
    }

    private void handleGemCollected(GemCollectedEvent e) {
        this.gemsCollected++;
    }

    private void handlePlayerDied(PlayerDiedEvent e) {
        endGame(false);
    }

    private void handleFireReachedExit(FireExitEvent e) {
        this.fireboyReachedExit = true;
    }

    private void handleWaterReachedExit(WaterExitEvent e) {
        this.watergirlReachedExit = true;
    }

    private void checkGameWinCondition() {
        if (!gameOver && this.fireboyReachedExit && this.watergirlReachedExit) {
            endGame(true);
        }
    }

    private void endGame(boolean won) {
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