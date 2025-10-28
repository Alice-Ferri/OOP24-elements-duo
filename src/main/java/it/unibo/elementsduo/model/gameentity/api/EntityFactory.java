package it.unibo.elementsduo.model.gameentity.api; 
import it.unibo.elementsduo.resources.Position;
import java.util.Set;

public interface EntityFactory {
    Set<GameEntity> createEntities(char symbol, Position pos);
}
