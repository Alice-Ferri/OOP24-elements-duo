package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.map.api.Tile;
import it.unibo.elementsduo.model.map.api.TileFactory;
import it.unibo.elementsduo.model.map.api.TileType;

public class BasicTileFactory implements TileFactory{

    @Override
    public Tile createTile(char symbol) {
        switch(symbol){
            case 'A' : return new SimpleTile(TileType.WATEREXIT,'A');
            case 'F' : return new SimpleTile(TileType.FIREEXIT,'F');
            case 'P' : return new SimpleTile(TileType.FLOOR,'P');
            case '#' : return new SimpleTile(TileType.WALL,'#');
            case 'W' : return new SimpleTile(TileType.WATERSPAWN,';');
            case 'B' : return new SimpleTile(TileType.FIRESPAWN,'L');
            default: return null;

        }
    }
    
}
