package it.unibo.elementsduo.model.interactions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.interactions.commands.impl.PushBoxCommand;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.PlayerFactoryImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Tests for {@link PushBoxCommand}.
 */
final class TestPushBoxCommand {

    private static final double VELOCITY_TEST = -4.0;

    private PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        this.playerFactory = new PlayerFactoryImpl();
    }

    @Test
    void appliesHorizontalVelocityWhenPlayerPushes() {
        final Player player = playerFactory.createPlayer(PlayerType.FIREBOY, new Position(-1, 0));
        player.setVelocityX(2.0);
        final PushBox box = new PushBox(new Position(0, 0));

        final PushBoxCommand command = new PushBoxCommand(box, player, new Vector2D(-1, 0));
        command.execute();

        assertEquals(2.0, box.getVelocity().x());
        assertEquals(0.0, box.getVelocity().y());
    }

    @Test
    void doesNothingWhenPlayerAbove() {
        final Player player = playerFactory.createPlayer(PlayerType.WATERGIRL, new Position(0, 1));
        player.setVelocityX(3.0);
        final PushBox box = new PushBox(new Position(0, 0));

        final PushBoxCommand command = new PushBoxCommand(box, player, new Vector2D(0, -1));
        command.execute();

        assertEquals(0.0, box.getVelocity().x());
    }

    @Test
    void doesNothingWhenPlayerMovesOpposite() {
        final Player player = playerFactory.createPlayer(PlayerType.FIREBOY, new Position(-1, 0));
        player.setVelocityX(VELOCITY_TEST);
        final PushBox box = new PushBox(new Position(0, 0));

        final PushBoxCommand command = new PushBoxCommand(box, player, new Vector2D(-1, 0));
        command.execute();

        assertEquals(0.0, box.getVelocity().x());
    }
}
