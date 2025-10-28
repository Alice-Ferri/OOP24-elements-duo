package it.unibo.elementsduo.view;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;
import it.unibo.elementsduo.resources.Position;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Objects;

public class LevelPanel extends JPanel {

    private final int elementSize = 32;
    private final Level level;

    private final Map<Class<? extends StaticObstacle>, Color> staticObstacleColorMap = Map.of(
        Wall.class, Color.DARK_GRAY,
        Floor.class, Color.LIGHT_GRAY,
        fireSpawn.class, Color.ORANGE,
        waterSpawn.class, Color.BLUE,
        fireExit.class, Color.RED,
        waterExit.class, new Color(0, 191, 255)
    );

    private final Map<Class<? extends Enemy>, Color> enemyColorMap = Map.of(
        ClassicEnemiesImpl.class, new Color(139, 0, 0),
        ShooterEnemyImpl.class, new Color(75, 0, 130)
    );

    public LevelPanel(final Level level) {
        this.level = Objects.requireNonNull(level);
        this.setPreferredSize(calculateStaticLayoutSize());
        this.setBackground(Color.white);
    }

    private Dimension calculateStaticLayoutSize() {
        var staticObstacles = level.getAllObstacles().stream()
                                    .filter(StaticObstacle.class::isInstance)
                                    .toList();

        if (staticObstacles.isEmpty()) {
            return new Dimension(0, 0);
        }

        final int maxX = staticObstacles.stream()
                .mapToInt(obs -> (int) obs.getHitBox().getCenter().x())
                .max()
                .orElse(0);
        final int maxY = staticObstacles.stream()
                .mapToInt(obs -> (int) obs.getHitBox().getCenter().y())
                .max()
                .orElse(0);

        return new Dimension((maxX + 1) * elementSize, (maxY + 1) * elementSize);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Dimension staticLayoutSize = calculateStaticLayoutSize();
        final int offsetX = (getWidth() - staticLayoutSize.width) / 2;
        final int offsetY = (getHeight() - staticLayoutSize.height) / 2;

        drawStaticObstacles(g, offsetX, offsetY);
        drawEnemies(g, offsetX, offsetY);
    }

    private void drawStaticObstacles(final Graphics g, final int offsetX, final int offsetY) {
        this.level.getAllObstacles().stream()
            .filter(StaticObstacle.class::isInstance)
            .map(StaticObstacle.class::cast)
            .forEach(obs -> {
                final int gridX = (int) obs.getHitBox().getCenter().x();
                final int gridY = (int) obs.getHitBox().getCenter().y();

                final Color tileColor = this.staticObstacleColorMap.getOrDefault(obs.getClass(), Color.MAGENTA);
                g.setColor(tileColor);

                final int pixelX = gridX * elementSize + offsetX;
                final int pixelY = gridY * elementSize + offsetY;

                g.fillRect(pixelX, pixelY, elementSize, elementSize);

                g.setColor(Color.BLACK);
                g.drawRect(pixelX, pixelY, elementSize, elementSize);
            });
    }

    private void drawEnemies(final Graphics g, final int offsetX, final int offsetY) {
        this.level.getAllEnemies().stream().forEach(enemy -> {
            final Color enemyColor = this.enemyColorMap.getOrDefault(enemy.getClass(), Color.PINK);
            g.setColor(enemyColor);

            final int pixelX = (int) Math.round(enemy.getX() * elementSize) + offsetX;
            final int pixelY = (int) Math.round(enemy.getY() * elementSize) + offsetY;

            g.fillOval(pixelX, pixelY, elementSize, elementSize);

            if (enemy instanceof ShooterEnemyImpl) {
                g.setColor(Color.WHITE);
                final int detailSize = elementSize / 2;
                final int detailOffset = elementSize / 4;
                g.fillOval(pixelX + detailOffset, pixelY + detailOffset, detailSize, detailSize);
            }
        });
    }
}