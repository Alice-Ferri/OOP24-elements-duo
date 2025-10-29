package it.unibo.elementsduo.model.gamestate.impl;

import java.util.Objects;

import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.events.api.EventListener;
import it.unibo.elementsduo.model.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.map.level.api.Level;

public class GameStateImpl implements EventListener, GameState{

    private final Level level;
    private boolean gameOver = false;
    private boolean won = false;
    private int gemsCollected = 0;
    private int deadEnemies = 0;

    public GameStateImpl(final EventManager eventManager,final Level level) {
        Objects.requireNonNull(eventManager);
        Objects.requireNonNull(level);
        this.level=level;

        eventManager.subscribe(PlayerDiedEvent.class, this);
        eventManager.subscribe(GemCollectedEvent.class, this);
        eventManager.subscribe(EnemyDiedEvent.class, this);
    }

    @Override
    public void onEvent(Event event) {
        

        if (event instanceof PlayerDiedEvent e) {
            handlePlayerDied(e); 
        } else if (event instanceof GemCollectedEvent e) {
            handleGemCollected(e);
        } else if (event instanceof EnemyDiedEvent e) {
            handleEnemyDied(e);
        }

    }

    private void handleEnemyDied(EnemyDiedEvent e) {
        e.getEnemy().die();
        this.deadEnemies++;
    }

    private void handleGemCollected(GemCollectedEvent e) {
        this.gemsCollected++;
    }

    private void handlePlayerDied(PlayerDiedEvent e) {
        System.out.println("c");
        this.gameOver = true;
        this.won = false;
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
    
}
