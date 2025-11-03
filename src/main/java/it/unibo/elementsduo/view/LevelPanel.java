package it.unibo.elementsduo.view;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.level.api.LevelData;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.GreenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.LavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.WaterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

/**
 * Main panel for the game level, combining the game area and a top control bar.
 * This panel holds the {@link GameAreaPanel} (where the game is rendered)
 * and navigation buttons.
 */
public final class LevelPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final transient LevelData level;
    private final GameAreaPanel gameArea;
    private final JButton homeButton;
    private final JButton levelSelectButton;

    /**
     * Constructs a new LevelPanel for the given level.
     *
     * @param level The game level model to be rendered.
     */
    public LevelPanel(final LevelData level) {
        this.level = Objects.requireNonNull(level);
        this.setLayout(new BorderLayout());

        this.homeButton = new JButton("Menu Principale");
        this.levelSelectButton = new JButton("Selezione Livello");

        final JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topBar.setBackground(Color.DARK_GRAY);
        topBar.add(homeButton);
        topBar.add(levelSelectButton);

        this.gameArea = new GameAreaPanel();

        this.add(topBar, BorderLayout.NORTH);
        this.add(gameArea, BorderLayout.CENTER);
    }

    public void addButtonsListeners(ActionListener start, ActionListener levelSelection){
        this.homeButton.addActionListener(start);
        this.levelSelectButton.addActionListener(levelSelection);
    }

    public void removeButtonsListeners(ActionListener start, ActionListener levelSelection){
        this.homeButton.removeActionListener(start);
        this.levelSelectButton.removeActionListener(levelSelection);
    }

    /**
     * Inner class representing the canvas where the game state is actively
     * rendered.
     * It handles all custom painting of game entities.
     */
    private final class GameAreaPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private final Dimension gridDimensions;

        private final Map<Class<? extends AbstractStaticObstacle>, Color> staticObstacleColorMap = Map.of(
                Wall.class, Color.DARK_GRAY,
                Floor.class, Color.LIGHT_GRAY,
                FireExit.class, Color.RED,
                LavaPool.class, Color.ORANGE,
                WaterPool.class, Color.CYAN,
                GreenPool.class, Color.GREEN,
                WaterExit.class, Color.BLUE);

        private final Map<Class<? extends AbstractInteractiveObstacle>, Color> interactiveColorMap = Map.of(Lever.class,
                Color.YELLOW,
                PlatformImpl.class, Color.CYAN,
                PushBox.class, Color.RED,
                Button.class, Color.GREEN);

        private final Map<Class<? extends Enemy>, Color> enemyColorMap = Map.of(
                ClassicEnemiesImpl.class, new Color(139, 0, 0),
                ShooterEnemyImpl.class, new Color(75, 0, 130));

        GameAreaPanel() {
            this.gridDimensions = calculateGridDimensions();
            this.setBackground(Color.white);
        }

        private Dimension calculateGridDimensions() {
            final var staticObstacles = level.getAllObstacles().stream()
                    .filter(AbstractStaticObstacle.class::isInstance)
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

            return new Dimension(maxX + 1, maxY + 1);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            if (gridDimensions.width == 0 || gridDimensions.height == 0) {
                return;
            }
            final int panelWidth = getWidth();
            final int panelHeight = getHeight();

            final int sizeBasedOnWidth = panelWidth / gridDimensions.width;
            final int sizeBasedOnHeight = panelHeight / gridDimensions.height;
            final int elementSize = Math.min(sizeBasedOnWidth, sizeBasedOnHeight);

            final int renderedWidth = elementSize * gridDimensions.width;
            final int renderedHeight = elementSize * gridDimensions.height;
            final int offsetX = (panelWidth - renderedWidth) / 2;
            final int offsetY = (panelHeight - renderedHeight) / 2;

            drawStaticObstacles(g, offsetX, offsetY, elementSize);
            drawInteractiveObstacles(g, offsetX, offsetY, elementSize);
            drawEnemies(g, offsetX, offsetY, elementSize);
            drawProjectiles(g, offsetX, offsetY, elementSize);
            drawPlayers(g, offsetX, offsetY, elementSize);
        }

        private void drawStaticObstacles(final Graphics g, final int offsetX, final int offsetY,
                final int elementSize) {
            level.getAllObstacles().stream()
                    .filter(AbstractStaticObstacle.class::isInstance)
                    .map(AbstractStaticObstacle.class::cast)
                    .forEach(obs -> {
                        final HitBox hb = obs.getHitBox();
                        final double cx = hb.getCenter().x();
                        final double cy = hb.getCenter().y();
                        final double hw = hb.getHalfWidth();
                        final double hh = hb.getHalfHeight();

                        final int x = toPx(cx - hw, elementSize) + offsetX;
                        final int y = toPx(cy - hh, elementSize) + offsetY;
                        final int w = toPx(hw * 2.0, elementSize);
                        final int h = toPx(hh * 2.0, elementSize);

                        final Color tileColor = this.staticObstacleColorMap.getOrDefault(obs.getClass(), Color.MAGENTA);
                        g.setColor(tileColor);

                        g.fillRect(x, y, w, h);

                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, w, h);
                    });
        }

        private void drawInteractiveObstacles(final Graphics g, final int offsetX, final int offsetY,
                final int elementSize) {

            level.getEntitiesByClass(AbstractInteractiveObstacle.class).forEach(obj -> {

                final HitBox hb = obj.getHitBox();
                final double cx = hb.getCenter().x();
                final double cy = hb.getCenter().y();

                final double hw = hb.getHalfWidth();
                final double hh = hb.getHalfHeight();

                final int x = toPx(cx - hw, elementSize) + offsetX;
                final int y = toPx(cy - hh, elementSize) + offsetY;
                final int w = toPx(hw * 2.0, elementSize);
                final int h = toPx(hh * 2.0, elementSize);

                final Color base = interactiveColorMap.getOrDefault(obj.getClass(), Color.PINK);
                g.setColor(base);

                if (obj instanceof TriggerSource source) {
                    if (source.isActive()) {
                        g.setColor(base.brighter());
                    } else {
                        g.setColor(base.darker());
                    }
                } else if (obj instanceof PlatformImpl platform) {
                    if (platform.isActive()) {
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

        private void drawEnemies(final Graphics g, final int offsetX, final int offsetY, final int elementSize) {
            final double enemyHalfWidth = 0.5;
            final double enemyHalfHeight = 0.5;

            level.getLivingEnemies().stream().forEach(enemy -> {
                final Color enemyColor = this.enemyColorMap.getOrDefault(enemy.getClass(), Color.PINK);
                g.setColor(enemyColor);

                final double cx = enemy.getX();
                final double cy = enemy.getY();
                final double tlx = cx - enemyHalfWidth;
                final double tly = cy - enemyHalfHeight;

                final int pixelX = toPx(tlx, elementSize) + offsetX;
                final int pixelY = toPx(tly, elementSize) + offsetY;
                final int w = toPx(enemyHalfWidth * 2.0, elementSize);
                final int h = toPx(enemyHalfHeight * 2.0, elementSize);

                g.fillOval(pixelX, pixelY, w, h);

                if (enemy instanceof ShooterEnemyImpl) {
                    g.setColor(Color.WHITE);
                    final int detailSize = w / 2;
                    final int detailOffset = w / 4;
                    g.fillOval(pixelX + detailOffset, pixelY + detailOffset, detailSize, detailSize);
                }
            });
        }

        private int toPx(final double worldCoord, final int elementSize) {
            return (int) Math.round(worldCoord * elementSize);
        }

        private void drawProjectiles(final Graphics g, final int offsetX, final int offsetY, final int elementSize) {

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

                final int pixelX = toPx(tlx, elementSize) + offsetX;
                final int pixelY = toPx(tly, elementSize) + offsetY;
                final int w = toPx(projectileWidth, elementSize);
                final int h = toPx(projectileHeight, elementSize);

                g.fillOval(pixelX, pixelY, w, h);

            });
        }

        private void drawPlayers(final Graphics g, final int offsetX, final int offsetY, final int elementSize) {

            level.getAllPlayers().stream().forEach(player -> {

                final HitBox hb = player.getHitBox();
                final double cx = hb.getCenter().x();
                final double cy = hb.getCenter().y();
                final double hw = hb.getHalfWidth();
                final double hh = hb.getHalfHeight();

                final int x = toPx(cx - hw, elementSize) + offsetX;
                final int y = toPx(cy - hh, elementSize) + offsetY;
                final int w = toPx(hw * 2.0, elementSize);
                final int h = toPx(hh * 2.0, elementSize);

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
