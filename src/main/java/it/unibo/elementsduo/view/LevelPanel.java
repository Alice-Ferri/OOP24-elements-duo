package it.unibo.elementsduo.view;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Map;
import java.util.Objects;

public class LevelPanel extends JPanel {

    private final Level level;
    private final GameAreaPanel gameArea;
    private final JButton homeButton;
    private final JButton levelSelectButton;

    public LevelPanel(final Level level) {
        this.level = Objects.requireNonNull(level);
        this.setLayout(new BorderLayout());

        this.homeButton = new JButton("Menu Principale");
        this.levelSelectButton = new JButton("Selezione Livello");

        final JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(Color.DARK_GRAY);
        topBar.add(homeButton);
        topBar.add(levelSelectButton);

        this.gameArea = new GameAreaPanel();

        this.add(topBar, BorderLayout.NORTH);
        this.add(gameArea, BorderLayout.CENTER);
        this.setPreferredSize(gameArea.getPreferredSize());
    }

    public JButton getHomeButton() {
        return this.homeButton;
    }

    public JButton getLevelSelectButton() {
        return this.levelSelectButton;
    }

    private class GameAreaPanel extends JPanel {

        private final int elementSize = 32;

        private final Map<Class<? extends StaticObstacle>, Color> staticObstacleColorMap = Map.of(
                Wall.class, Color.DARK_GRAY,
                Floor.class, Color.LIGHT_GRAY,
                fireSpawn.class, Color.ORANGE,
                waterSpawn.class, Color.BLUE,
                fireExit.class, Color.RED,
                lavaPool.class, Color.ORANGE,
                waterPool.class, Color.CYAN,
                greenPool.class, Color.GREEN,
                waterExit.class, new Color(0, 191, 255));

        private final Map<Class<? extends InteractiveObstacle>, Color> interactiveColorMap = Map.of(Lever.class,
                Color.YELLOW,
                PlatformImpl.class, Color.CYAN,
                PushBox.class, Color.RED,
                button.class, Color.GREEN);

        private final Map<Class<? extends Enemy>, Color> enemyColorMap = Map.of(
                ClassicEnemiesImpl.class, new Color(139, 0, 0),
                ShooterEnemyImpl.class, new Color(75, 0, 130));

        GameAreaPanel() {
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
            drawInteractiveObstacles(g, offsetX, offsetY);
            drawEnemies(g, offsetX, offsetY);
            drawProjectiles(g, offsetX, offsetY);
            drawPlayers(g, offsetX, offsetY);
        }

        private void drawStaticObstacles(final Graphics g, final int offsetX, final int offsetY) {
            level.getAllObstacles().stream()
                    .filter(StaticObstacle.class::isInstance)
                    .map(StaticObstacle.class::cast)
                    .forEach(obs -> {
                        final HitBox hb = obs.getHitBox();
                        final double cx = hb.getCenter().x();
                        final double cy = hb.getCenter().y();
                        final double hw = hb.getHalfWidth();
                        final double hh = hb.getHalfHeight();

                        final int x = toPx(cx - hw) + offsetX;
                        final int y = toPx(cy - hh) + offsetY;
                        final int w = toPx(hw * 2.0);
                        final int h = toPx(hh * 2.0);

                        final Color tileColor = this.staticObstacleColorMap.getOrDefault(obs.getClass(), Color.MAGENTA);
                        g.setColor(tileColor);

                        g.fillRect(x, y, w, h);

                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, w, h);
                    });
        }

        private void drawInteractiveObstacles(final Graphics g, final int offsetX, final int offsetY) {
            level.getEntitiesByClass(InteractiveObstacle.class).forEach(obj -> {
                final HitBox hb = obj.getHitBox();
                final double cx = hb.getCenter().x();
                final double cy = hb.getCenter().y();

                final double hw = hb.getHalfWidth();
                final double hh = hb.getHalfHeight();

                final int x = toPx(cx - hw) + offsetX;
                final int y = toPx(cy - hh) + offsetY;
                final int w = toPx(hw * 2.0);
                final int h = toPx(hh * 2.0);

                Color base = interactiveColorMap.getOrDefault(obj.getClass(), Color.PINK);
                g.setColor(base);

                if (obj instanceof Triggerable triggerable) {
                    if (triggerable.isActive()) {
                        g.setColor(base.brighter());
                    } else {
                        g.setColor(base.darker());
                    }
                }

                g.fillRect(x, y, w, h);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, w, h);

            });
        }

        private void drawEnemies(final Graphics g, final int offsetX, final int offsetY) {
            final double enemyHalfWidth = 0.5;
            final double enemyHalfHeight = 0.5;

            level.getLivingEnemies().stream().forEach(enemy -> {
                final Color enemyColor = this.enemyColorMap.getOrDefault(enemy.getClass(), Color.PINK);
                g.setColor(enemyColor);

                final double cx = enemy.getX();
                final double cy = enemy.getY();
                final double tlx = cx - enemyHalfWidth;
                final double tly = cy - enemyHalfHeight;

                final int pixelX = toPx(tlx) + offsetX;
                final int pixelY = toPx(tly) + offsetY;
                final int w = toPx(enemyHalfWidth * 2.0);
                final int h = toPx(enemyHalfHeight * 2.0);

                g.fillOval(pixelX, pixelY, w, h);

                if (enemy instanceof ShooterEnemyImpl) {
                    g.setColor(Color.WHITE);
                    final int detailSize = w / 2;
                    final int detailOffset = w / 4;
                    g.fillOval(pixelX + detailOffset, pixelY + detailOffset, detailSize, detailSize);
                }
            });
        }

        private int toPx(final double worldCoord) {
            return (int) Math.round(worldCoord * this.elementSize);
        }

        private void drawProjectiles(final Graphics g, final int offsetX, final int offsetY) {

            final double projectileWidth = 0.25;
            final double projectileHeight = 0.25;
            final double projHalfWidth = projectileWidth / 2.0;
            final double projHalfHeight = projectileHeight / 2.0;

            level.getAllProjectiles().stream().forEach(projectile -> {
                g.setColor(Color.BLACK);

                final double cx = projectile.getX();
                final double cy = projectile.getY();
                final double tlx = cx - projHalfWidth;
                final double tly = cy - projHalfHeight;

                final int pixelX = toPx(tlx) + offsetX;
                final int pixelY = toPx(tly) + offsetY;
                final int w = toPx(projectileWidth);
                final int h = toPx(projectileHeight);

                g.fillOval(pixelX, pixelY, w, h);

            });
        }

        private void drawPlayers(final Graphics g, final int offsetX, final int offsetY) {

            level.getAllPlayers().stream().forEach(player -> {

                final HitBox hb = player.getHitBox();
                final double cx = hb.getCenter().x();
                final double cy = hb.getCenter().y();
                final double hw = hb.getHalfWidth();
                final double hh = hb.getHalfHeight();

                final int x = toPx(cx - hw) + offsetX;
                final int y = toPx(cy - hh) + offsetY;
                final int w = toPx(hw * 2.0);
                final int h = toPx(hh * 2.0);

                if (player instanceof Fireboy) {
                    g.setColor(Color.RED);
                } else if (player instanceof Watergirl) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.RED);
                }

                g.fillOval(x, y, w, h);

                g.setColor(Color.BLACK);
            });
        }
    }
}