package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.map.api.Tile;
import it.unibo.elementsduo.model.map.api.TileFactory;
import it.unibo.elementsduo.model.map.api.TileType;
import it.unibo.elementsduo.resources.Position;

public class BasicTileFactory implements TileFactory{

    @Override
    public Tile createTile(char symbol, Position pos) {
        switch(symbol){
            case '-' : return new SimpleTile(TileType.LAVA,'-',pos);
            case 'A' : return new SimpleTile(TileType.WATEREXIT,'A',pos);
            case 'F' : return new SimpleTile(TileType.FIREEXIT,'F',pos);
            case 'P' : return new SimpleTile(TileType.FLOOR,'P',pos);
            case '#' : return new SimpleTile(TileType.WALL,'#',pos);
            case ';' : return new SimpleTile(TileType.WATER,';',pos);
            case 'L' : return new SimpleTile(TileType.ACID,'L',pos);
            default: return null;
        }
    }
    
}
