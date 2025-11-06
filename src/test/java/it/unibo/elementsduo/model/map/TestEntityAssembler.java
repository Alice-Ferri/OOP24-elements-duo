package it.unibo.elementsduo.model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import it.unibo.elementsduo.model.gameentity.api.EntityAssembler;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.impl.EntityAssemblerImpl;
import it.unibo.elementsduo.resources.Position;
import java.util.Set;

/**
 * Integration test for the {@link EntityAssemblerImpl} class.
 * It verifies that all valid symbols create entities and invalid symbols return null.
 */
final class TestEntityAssembler {

    private EntityAssembler entityAssembler;

    /**
     * Initializes the entityAssembler by injecting the implementations
     * of others factories before each test.
     */
    @BeforeEach
    void setUp() {
        this.entityAssembler = new EntityAssemblerImpl();
    }

    /**
     * Tests that every single valid symbol
     * produces a non-null entity.
     */
    @Test
    void testAllValidSymbolsAreCreated() {
        final Set<Character> validSymbols = Set.of(
            'P', '#', 'A', 'F', 'Q', 'E', 'K',
            'B', 'W', 'C', 'S', 'L', 'H', 'M', 'R'
        );

        final Position pos = new Position(1, 1);

        for (final char symbol : validSymbols) {
            final GameEntity result = entityAssembler.createEntity(symbol, pos);

            assertNotNull(result, 
                "The entity created for the symbol '" + symbol + "' was null.");
        }
    }

    /**
     * Tests that a set of invalid symbols
     * returns null.
     */
    @Test
    void testInvalidSymbolsReturnNull() {
        final Set<Character> invalidSymbols = Set.of('X', 'Z', '?', '1', ' ');
        final Position pos = new Position(1, 1);

        for (final char symbol : invalidSymbols) {
            assertNull(
                entityAssembler.createEntity(symbol, pos),
                "The invalid symbol '" + symbol + "' did not return null."
            );
        }
    }
}
