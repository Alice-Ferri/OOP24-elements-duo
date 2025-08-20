package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.map.api.Tile;
import it.unibo.elementsduo.model.map.api.TileType;
import it.unibo.elementsduo.resources.Position;

public class SimpleTile implements Tile {

    private final TileType type;
    private final char symbol;
    private final Position pos;

    public SimpleTile(TileType tileType, char c, Position pos){
        this.symbol=c;
        this.type=tileType;
        this.pos=pos;
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
            case LAVA:
            //da modificare : controllare che player sia fuoco
                return true;
            case WALL:
                return false;
            case WATER:
            //da modificare : controllare che player sia acqua
                return true;
            case ACID:
                return false;
            default:
                return false;
        }
        
    }

    @Override
    public char getChar() {
        return this.symbol;
    }

    @Override
    public Position getPosition() {
        return this.pos;
    }
    
}
