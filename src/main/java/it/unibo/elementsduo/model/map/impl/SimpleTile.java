package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.map.api.Tile;
import it.unibo.elementsduo.model.map.api.TileType;

public class SimpleTile implements Tile {

    private final TileType type;
    private final char symbol;

    public SimpleTile(TileType tileType, char c){
        this.symbol=c;
        this.type=tileType;
    }

    @Override
    public TileType getType() {
        return this.type;
    }

    @Override
    public boolean isWalkable() {

        switch(this.type) {
            case FIRESPAWN,FLOOR,FIREEXIT,WATEREXIT,WATERSPAWN:
                return true;
            case WALL:
                return false;
            default:
                return false;
        }
        
    }

    @Override
    public char getChar() {
        return this.symbol;
    }
    
}
