package it.unibo.elementsduo.model.map.api;

//Classe utilizzata per determinare il ruolo del tile (ovvero di un elemento statico del gioco
//usato per pavimenti spawn,lava,etc 
//utilizzo un enum

public enum TileType {
    WALL,
    FLOOR,
    FIRESPAWN,
    WATERSPAWN,
    FIREEXIT,
    WATEREXIT
}
